package ru.mvp.accesspoint;

import org.springframework.stereotype.Component;
import ru.mvp.database.LoggerDBTools;
import ru.mvp.database.entities.Esls;
import ru.mvp.database.entities.Items;
import ru.mvp.database.entities.Tasks;
import ru.mvp.database.repositories.EslsRepository;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Stream;

import jssc.SerialPort;
import jssc.SerialPortException;
import ru.mvp.database.repositories.ItemsRepository;
import ru.mvp.database.repositories.TasksRepository;

import static com.sun.javafx.font.PrismFontFactory.isWindows;

@Component
public class ConsoleTools {
    private EslsRepository eslsRepository;
    private ItemsRepository itemsRepository;
    private TasksRepository tasksRepository;
    private LoggerDBTools loggerDBTools;
    private byte[][] eslsNumsArray=new byte[11][2];
    public ConsoleTools(EslsRepository eslsRepository, LoggerDBTools loggerDBTools,TasksRepository tasksRepository,ItemsRepository itemsRepository) {
        this.eslsRepository = eslsRepository;
        this.itemsRepository = itemsRepository;
        this.tasksRepository = tasksRepository;
        this.loggerDBTools = loggerDBTools;
        eslsNumsArray[0][0]=(byte)0xb4;
        eslsNumsArray[0][1]=(byte)0x00;
        eslsNumsArray[1][0]=(byte)0xb4;
        eslsNumsArray[1][1]=(byte)0x01;
        eslsNumsArray[2][0]=(byte)0xb4;
        eslsNumsArray[2][1]=(byte)0x02;
        eslsNumsArray[3][0]=(byte)0xb4;
        eslsNumsArray[3][1]=(byte)0x04;
        eslsNumsArray[4][0]=(byte)0xb4;
        eslsNumsArray[4][1]=(byte)0x08;
        eslsNumsArray[5][0]=(byte)0xb4;
        eslsNumsArray[5][1]=(byte)0x10;
        eslsNumsArray[6][0]=(byte)0xb4;
        eslsNumsArray[6][1]=(byte)0x20;
        eslsNumsArray[7][0]=(byte)0xb4;
        eslsNumsArray[7][1]=(byte)0x40;
        eslsNumsArray[8][0]=(byte)0xb4;
        eslsNumsArray[8][1]=(byte)0x80;
        eslsNumsArray[9][0]=(byte)0xb5;
        eslsNumsArray[9][1]=(byte)0x00;
        eslsNumsArray[10][0]=(byte)0xb6;
        eslsNumsArray[10][1]=(byte)0x00;
    }

    public String runConsoleCommand(String command) throws IOException {
        ProcessBuilder builder = new ProcessBuilder();
        if (isWindows) {
            builder.command("cmd.exe", "/c", command);
        } else {
            builder.command("sh", "-c", command);
        }
        builder.directory(new File(System.getProperty("user.home")));
        Process process = builder.start();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        StringBuilder output = new StringBuilder();
        while ((line = reader.readLine())!= null) {
            output.append(line + "<br>");
        }
        return output.toString();
    }
    public void getByteImage(String eslStr){
        //todo тупо влепим поле с айдишниками ценников для обновления в сущность таски. потом доработаем

        Esls esl = eslsRepository.findByCode(eslStr);
        try {
            //а насрать. Это временно
            //костыль дозаписи информации о ценники. Нельзя доделать пока ценники ничего не возвращают
            esl.setBatteryLevel("100");
            esl.setConnectivity("connected");
            esl.setLastUpdate(new Timestamp(new Date().getTime()));
            esl.setStatus("online");
            eslsRepository.saveAndFlush(esl);
            sendDataToCOMMport(eslsNumsArray[Integer.valueOf(eslStr)-1], esl.getNextImage());
//            FileOutputStream fos = new FileOutputStream(eslStr+".exe");
//            fos.write(data, 0, data.length);
//            fos.flush();
//            fos.close();
            loggerDBTools.log(new Timestamp(new Date().getTime()),"task", "run", "успешно обновлен ценник <br>" + esl.toString() + "<br> ручное обновление <br>" , "integration");
        }catch (Exception e){
            System.out.println("обработать ошибку"+e);
            loggerDBTools.log(new Timestamp(new Date().getTime()),"task", "run", "ошибка обновления <br>" + esl.toString() + "<br>" + e.getLocalizedMessage(), "integration");
        }
    }
    //дикий говнокод из двух шаблонов. Показ завтра, поэтому выбирать не приходистя
    public void updateTask(String taskId){
        Tasks tasks = tasksRepository.findById(Integer.valueOf(taskId));
        String[] barcodes = tasks.getBarcodes().split(",");
        for (String barcode : barcodes) {
            byte[] outByte = new byte[2];
            Collection<Esls> eslsById = itemsRepository.findByCode(barcode).getEslsById();
            if (eslsById!=null) {
                Esls o = (Esls) eslsById.toArray()[0];
                for (Esls esls : eslsById) {
                    System.out.println("ценники " + esls.getCode());
                    outByte[0] = (byte) (eslsNumsArray[Integer.valueOf(esls.getCode())][0] | outByte[0]);
                    outByte[1] = (byte) (eslsNumsArray[Integer.valueOf(esls.getCode())][1] | outByte[1]);
                    //костыль дозаписи информации о ценники. Нельзя доделать пока ценники ничего не возвращают
                    esls.setBatteryLevel("100");
                    esls.setConnectivity("connected");
                    esls.setLastUpdate(new Timestamp(new Date().getTime()));
                    esls.setStatus("online");
                    eslsRepository.save(esls);
                }
                eslsRepository.flush();
                System.out.println("групповое обновление " + bytesToHex(outByte) + "  " + barcode);
                sendDataToCOMMport(outByte, o.getNextImage());
            }
        }

    }
    private void sendDataToCOMMport(byte[] eslNum, byte[] fileBytes){
        byte[] okArr = new byte[2];
        okArr[0]=(byte)0xF0;
        okArr[1]=(byte)0x0F;
        try {
            SerialPort serialPort = new SerialPort("COM6");
            serialPort.openPort();
            serialPort.setParams(SerialPort.BAUDRATE_115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            byte [] outData = new byte[157];
            outData[0]=(byte)0x9C;
            outData[1]=eslNum[0];
            outData[2]=eslNum[1];
            byte[] b = new byte[1];
            new Random().nextBytes(b);
            outData[3]=b[0];
            int index = 0;
            int errCount = 5;
            for (int k = 1;k<20;k++) {
                outData[4] = (byte) k;
                for (int i = 0; i < 152; i++) {
                    outData[i + 5] = fileBytes[i+index];
                }
                serialPort.writeBytes(outData);
                byte[] buffer = serialPort.readBytes(2);
                if(Arrays.equals(buffer,okArr)){
                    System.out.println("ОТПРАВЛЕНО "+k);
                    index+=152;
                    errCount=5;
                }else {
                    System.out.println(bytesToHex(outData));
                    System.out.println(bytesToHex(buffer));
                    System.out.println("ERROR");
                    k--;
                    errCount--;
                    if (errCount<0){
                        throw new IOException();
                    }
                }
                Thread.sleep(50);
            }
            serialPort.closePort();
        }
        catch (SerialPortException|IOException|InterruptedException ex) {
            System.out.println(ex);
        }
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
