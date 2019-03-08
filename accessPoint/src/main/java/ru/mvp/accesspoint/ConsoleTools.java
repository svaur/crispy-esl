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
import java.util.stream.Collectors;
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
            sendDataToCOMMport(eslsNumsArray[Integer.valueOf(eslStr)], esl.getNextImage());
//            FileOutputStream fos = new FileOutputStream(eslStr+".exe");
//            fos.write(data, 0, data.length);
//            fos.flush();
//            fos.close();
            //костыль дозаписи информации о ценники. Нельзя доделать пока ценники ничего не возвращают
            esl.setBatteryLevel("100");
            esl.setConnectivity("connected");
            esl.setLastUpdate(new Timestamp(new Date().getTime()));
            esl.setStatus("online");
            eslsRepository.saveAndFlush(esl);
            loggerDBTools.log(new Timestamp(new Date().getTime()),"task", "run", "успешно обновлен ценник <br>" + esl.getCode() + "<br> ручное обновление <br>" , "integration");
        }catch (Exception e){
            System.out.println("обработать ошибку"+e);
            esl.setBatteryLevel("?");
            esl.setConnectivity("disconnected");
            esl.setLastUpdate(new Timestamp(new Date().getTime()));
            esl.setStatus("offline");
            eslsRepository.saveAndFlush(esl);
            loggerDBTools.log(new Timestamp(new Date().getTime()),"task", "run", "ошибка обновления <br>" + esl.getCode() + "<br>" + e.getLocalizedMessage(), "integration");
        }
    }
    //дикий говнокод из двух шаблонов. Показ завтра, поэтому выбирать не приходистя
    public void updateTask(String taskId){
        Tasks tasks = tasksRepository.findById(Integer.valueOf(taskId));
        String[] barcodes = tasks.getBarcodes().split(",");
        for (String barcode : barcodes) {
            byte[] outByte = new byte[2];
            Collection<Esls> eslsById = itemsRepository.findByCode(barcode).getEslsById();
            if (eslsById.size()!=0) {
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
                try {
                    sendDataToCOMMport(outByte, o.getNextImage());
                }catch (Exception e){
                    for (Esls esls : eslsById) {
                        //костыль дозаписи информации о ценники. Нельзя доделать пока ценники ничего не возвращают
                        esls.setBatteryLevel("?");
                        esls.setConnectivity("disconnected");
                        esls.setStatus("offline");
                        eslsRepository.save(esls);
                    }
                    eslsRepository.flush();
                }
            }
        }

    }
    private void sendDataToCOMMport(byte[] eslNum, byte[] fileBytes) throws Exception{
        byte[] okArr = new byte[2];
        okArr[0]=(byte)0xF0;
        okArr[1]=(byte)0x0F;
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


    public void sendFinePic() throws Exception{
String test = "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,07,FF,FC,00,00,00,00,00,00,00,00,00,00,01," +
        "FF,FC,00,00,07,FF,FF,FF,FC,00,00,03,FF,F0,00,00," +
        "00,00,03,FF,FE,00,00,1F,FF,FF,FF,FF,80,00,0F,FF," +
        "F8,00,00,00,00,07,FF,FF,80,00,FF,FE,00,3F,FF,E0," +
        "00,1F,FF,FE,00,00,00,00,1F,FF,FF,80,03,FF,C0,00," +
        "00,7F,F8,00,7F,FF,FF,00,00,00,00,3F,FF,FF,F8,FF," +
        "80,00,00,00,00,3F,C3,FF,FF,FF,E0,00,00,00,7F,FF," +
        "FF,FC,FE,00,00,00,00,00,1F,F3,FF,FF,FF,E0,00,00," +
        "01,FF,FF,FF,FF,F0,00,00,00,00,00,01,FF,FF,FF,FF," +
        "E0,00,00,03,FF,FF,FF,FF,80,00,00,00,00,00,00,FF," +
        "FF,FF,FF,F8,00,00,07,FF,FF,FF,FF,00,00,00,00,00," +
        "00,00,1F,FF,FF,FF,F8,00,00,07,FF,FF,FF,FF,00,00," +
        "00,00,00,00,00,0F,FF,FF,FF,FE,00,00,0F,FF,FF,FF," +
        "FC,00,00,00,00,00,00,00,03,FF,FF,FF,FE,00,00,0F," +
        "FF,FF,FF,E0,00,00,00,00,00,00,00,00,FF,FF,FF,FF," +
        "00,00,1F,FF,FF,FF,80,00,00,00,00,00,00,00,00,FF," +
        "FF,FF,FF,00,00,3F,FF,FF,FF,80,00,00,00,00,00,00," +
        "00,00,1F,FF,FF,FF,80,00,3F,FF,FF,FE,00,00,00,00," +
        "00,00,00,00,00,0F,FF,FF,FF,80,00,3F,FF,FF,FE,00," +
        "00,00,00,00,00,00,00,00,07,FF,FF,FF,80,00,3F,FF," +
        "FF,FC,00,00,00,00,00,00,00,00,00,03,FF,FF,FF,80," +
        "00,3F,FF,FF,F0,00,00,00,00,00,00,00,00,00,00,FF," +
        "FF,FF,80,00,3F,FF,FF,E0,00,00,00,00,00,00,00,00," +
        "00,00,7F,FF,FF,80,00,3F,FF,FF,C0,00,00,00,00,00," +
        "00,00,00,00,00,3F,FF,FF,80,00,3F,FF,FF,80,00,00," +
        "00,00,00,00,00,00,00,00,1F,FF,FF,80,00,3F,FF,FF," +
        "80,00,00,00,00,00,00,00,00,00,00,0F,FF,FF,80,00," +
        "3F,FF,FF,00,00,00,00,00,00,00,00,00,00,00,0F,FF," +
        "FF,80,00,3F,FF,FC,00,00,00,00,00,00,00,00,00,00," +
        "00,07,FF,FF,80,00,3F,FF,FC,00,00,00,00,00,00,00," +
        "00,00,00,00,03,FF,FF,80,00,3F,FF,FC,00,00,00,00," +
        "00,00,00,00,00,00,00,01,FF,FF,00,00,1F,FF,F8,00," +
        "00,00,00,00,00,00,00,00,00,00,01,FF,FF,00,00,0F," +
        "FF,F0,00,00,00,00,00,00,00,00,00,00,00,01,FF,FF," +
        "00,00,0F,FF,F0,00,00,00,00,00,00,00,00,00,00,00," +
        "01,FF,FF,00,00,07,FF,C0,00,00,00,00,00,00,00,00," +
        "00,00,00,00,7F,FE,00,00,07,FF,C0,00,00,04,00,00," +
        "00,00,00,06,00,00,00,7F,FC,00,00,03,FF,80,00,00," +
        "FF,80,00,00,00,00,1F,C0,00,00,7F,F8,00,00,03,FF," +
        "80,00,00,FF,C0,00,00,00,00,7F,F0,00,00,7F,F8,00," +
        "00,01,FF,80,00,03,FF,F0,00,00,00,00,FF,F8,00,00," +
        "3F,E0,00,00,00,7F,80,00,03,FF,F0,00,00,00,01,FF," +
        "FC,00,00,3F,E0,00,00,00,3F,00,00,07,FF,F8,00,00," +
        "00,03,FF,FE,00,00,1F,E0,00,00,00,0F,00,00,3F,FF," +
        "FC,00,00,00,07,FF,FF,00,00,0F,00,00,00,00,07,00," +
        "00,7F,FF,FC,00,00,00,07,FF,FF,80,00,0E,00,00,00," +
        "00,07,00,00,7F,FF,FC,00,00,00,07,FF,FF,C0,00,0E," +
        "00,00,00,00,06,00,00,FF,FF,FC,00,00,00,07,FF,FF," +
        "F0,00,0E,00,00,00,00,06,00,01,FF,FF,FC,00,00,00," +
        "07,FD,FF,F0,00,0E,00,00,00,00,06,00,01,FF,F3,FC," +
        "00,00,00,07,FC,FF,F0,00,0E,00,00,00,00,06,00,07," +
        "FF,C1,FC,00,00,00,07,F0,FF,FC,00,0E,00,00,00,00," +
        "06,00,07,FF,C1,FC,00,00,00,07,F0,FF,FC,00,0E,00," +
        "00,00,00,06,00,0F,FF,E1,FC,00,00,00,07,F0,FF,FE," +
        "00,0E,00,00,00,00,06,00,0F,FF,F3,FC,00,00,00,07," +
        "F8,FF,FE,00,0E,00,00,00,00,06,00,1F,FF,FF,F8,00," +
        "00,00,07,FD,FF,FF,00,0E,00,00,00,00,06,00,1F,FF," +
        "FF,F8,00,00,00,07,FF,FF,FF,00,0E,00,00,00,00,06," +
        "00,3F,FF,FF,F8,00,00,00,03,FF,FF,FF,80,0E,00,00," +
        "00,00,06,00,3F,FF,FF,F0,00,00,00,01,FF,FF,FF,80," +
        "0E,00,00,00,00,06,00,3F,FF,FF,F0,00,00,00,01,FF," +
        "FF,FF,80,0E,00,00,00,00,06,00,3F,FF,FF,F0,00,00," +
        "00,01,FF,FF,FF,80,0E,00,00,00,00,06,00,3F,FF,FF," +
        "F0,00,00,00,00,FF,FF,FF,80,0E,00,00,00,00,07,00," +
        "3F,FF,FF,E0,00,00,00,00,FF,FF,FF,80,0E,00,00,00," +
        "00,07,00,3F,FF,FF,C0,00,1F,80,00,FF,FF,FF,80,0E," +
        "00,00,00,00,07,00,3F,FF,FF,C0,03,FF,FC,00,7F,FF," +
        "FF,80,1C,00,00,00,00,07,00,3F,FF,FF,80,03,FF,FC," +
        "00,7F,FF,FF,80,1C,00,00,00,00,03,00,3F,FF,FF,80," +
        "03,FF,FC,00,3F,FF,FF,80,1C,00,00,00,00,03,00,3F," +
        "FF,FF,80,03,FF,FC,00,1F,FF,FF,80,38,00,00,00,00," +
        "03,80,1F,FF,FE,00,03,FF,FC,00,1F,FF,FF,00,78,00," +
        "00,00,00,03,80,0F,FF,FE,00,03,FF,F0,00,0F,FF,FF," +
        "00,78,00,00,00,00,03,80,0F,FF,FC,00,00,FF,E0,00," +
        "07,FF,FC,00,70,00,00,00,00,01,C0,07,FF,F8,00,00," +
        "1F,80,00,01,FF,FC,00,F0,00,00,00,00,01,E0,01,FF," +
        "F0,00,00,00,00,00,01,FF,F0,00,F0,00,00,00,00,00," +
        "F0,01,FF,C0,00,00,00,00,00,00,FF,F0,01,F0,00,00," +
        "00,00,00,F0,00,7F,00,00,00,00,00,00,00,7F,C0,01," +
        "E0,00,00,00,00,00,F0,00,00,00,00,00,00,00,00,00," +
        "00,00,01,80,00,00,00,00,00,78,00,00,00,00,00,00," +
        "00,00,00,00,00,03,80,00,00,00,00,00,7C,00,00,00," +
        "00,00,00,00,00,00,00,00,07,00,00,00,00,00,00,1C," +
        "00,00,00,00,00,00,00,00,00,00,00,0E,00,00,00,00," +
        "00,00,0E,00,00,00,00,00,00,00,00,00,00,00,0E,00," +
        "00,00,00,00,00,0E,00,00,00,00,00,00,00,00,00,00," +
        "00,1E,00,00,00,00,00,00,0F,80,00,00,00,00,00,00," +
        "00,00,00,00,1C,00,00,00,00,00,00,03,C0,00,00,00," +
        "00,00,00,00,00,00,00,70,00,00,00,00,00,00,03,C0," +
        "00,00,00,00,00,00,00,00,00,00,F0,00,00,00,00,00," +
        "00,00,F0,00,00,00,00,00,00,00,00,00,03,E0,00,00," +
        "00,00,00,00,00,78,00,00,00,00,00,00,00,00,00,07," +
        "C0,00,00,00,00,00,00,00,7C,00,00,00,00,00,00,00," +
        "00,00,0F,80,00,00,00,00,00,00,00,3E,00,00,00,00," +
        "00,00,00,00,00,0F,00,00,00,00,00,00,00,00,0F,80," +
        "00,00,00,00,00,00,00,00,FC,00,00,00,00,00,00,00," +
        "00,03,F8,00,00,00,00,00,00,00,03,F0,00,00,00,00," +
        "00,00,00,00,01,FC,00,00,00,00,00,00,00,0F,E0,00," +
        "00,00,00,00,00,00,00,00,7F,00,00,00,00,00,00,00," +
        "3F,00,00,00,00,00,00,00,00,00,00,1F,C0,00,00,00," +
        "00,00,00,FF,00,00,00,00,00,00,00,00,00,00,07,F8," +
        "00,00,00,00,00,03,FE,00,00,00,00,00,00,00,00,00," +
        "00,07,FE,00,00,00,00,00,1F,FE,00,00,00,00,00,00," +
        "00,00,00,00,07,FF,FF,FF,00,1F,FF,FF,FE,00,00,00," +
        "00,00,00,00,00,00,00,07,FF,FF,FF,FF,FF,FF,FF,FE," +
        "00,00,00,00,00,00,00,00,00,00,07,FF,FF,FF,FF,FF," +
        "FF,FF,FE,00,00,00,00,00,00,00,00,00,00,07,FF,FE," +
        "03,FF,80,07,FF,FE,00,00,00,00,00,00,00,00,00,00," +
        "07,FF,FE,00,00,00,0F,FF,FE,00,00,00,00,00,00,00," +
        "00,00,00,07,FF,FF,00,00,00,0F,FF,FE,00,00,00,00," +
        "00,00,00,00,00,00,07,FF,FF,00,00,00,0F,FF,FE,00," +
        "00,00,00,00,00,00,00,00,00,07,FF,FF,00,00,00,0F," +
        "FF,FE,00,00,00,00,00,00,00,00,00,00,07,FF,FF,00," +
        "00,00,0F,FF,FE,00,00,00,00,00,00,00,00,00,00,07," +
        "FF,FF,00,00,00,0F,FF,FE,00,00,00,00,00,00,00,00," +
        "00,00,06,FF,FF,00,00,00,0F,FF,FE,00,00,00,00,00," +
        "00,00,00,00,00,06,FF,FF,00,00,00,0F,FF,EE,00,00," +
        "00,00,00,00,00,00,00,00,06,07,F8,00,00,00,03,FE," +
        "0E,00,00,00,00,00,00,00,00,00,00,06,00,00,00,00," +
        "00,00,00,0E,00,00,00,00,00,00,00,00,00,00,07,00," +
        "00,00,00,00,00,00,0E,00,00,00,00,00,00,00,00,00," +
        "00,07,00,00,00,00,00,00,00,0C,00,00,00,00,00,00," +
        "00,00,00,00,03,00,00,00,00,00,00,00,0C,00,00,00," +
        "00,00,00,00,00,00,00,03,00,00,00,00,00,00,00,0C," +
        "00,00,00,00,00,00,00,00,00,00,03,00,00,00,00,00," +
        "00,00,0C,00,00,00,00,00,00,00,00,00,00,03,00,00," +
        "00,00,00,00,00,38,00,00,00,00,00,00,00,00,00,00," +
        "03,00,00,00,00,00,00,00,38,00,00,00,00,00,00,00," +
        "00,00,00,01,80,00,00,00,00,00,00,70,00,00,00,00," +
        "00,00,00,00,00,00,01,E0,00,00,00,00,00,00,F0,00," +
        "00,00,00,00,00,00,00,00,00,01,F0,00,00,00,00,00," +
        "00,F0,00,00,00,00,00,00,00,00,00,00,01,F8,00,00," +
        "00,00,00,01,F0,00,00,00,00,00,00,00,00,00,00,00," +
        "FC,00,00,00,00,00,03,F0,00,00,00,00,00,00,00,00," +
        "00,00,00,FE,00,00,00,00,00,0F,F0,00,00,00,00,00," +
        "00,00,00,00,00,00,FF,80,00,00,00,00,1F,F0,00,00," +
        "00,00,00,00,00,00,00,00,00,FF,C0,00,00,00,00,7F," +
        "F0,00,00,00,00,00,00,00,00,00,00,00,FF,F8,00,00," +
        "00,01,FF,E0,00,00,00,00,00,00,00,00,00,00,00,FF," +
        "FF,00,00,00,0F,FF,E0,00,00,00,00,00,00,00,00,00," +
        "00,00,FF,FF,FF,FF,FF,FF,FF,C0,00,00,00,00,00,00," +
        "00,00,00,00,00,7F,FF,FF,FF,FF,FF,FF,80,00,00,00," +
        "00,00,00,00,00,00,00,00,3F,FF,FF,FF,FF,FF,FF,80," +
        "00,00,00,00,00,00,00,00,00,00,00,3F,FF,E0,00,00," +
        "FF,FF,00,00,00,00,00,00,00,00,00,00,00,00,3F,FF," +
        "C0,00,00,7F,FF,00,00,00,00,00,00,00,00,00,00,00," +
        "00,0F,FF,C0,00,00,7F,FF,00,00,00,00,00,00,00,00," +
        "00,00,00,00,0F,FF,C0,00,00,3F,FF,00,00,00,00,00," +
        "00,00,00,00,00,00,00,07,FF,80,00,00,3F,FC,00,00," +
        "00,00,00,00,00,00,00,00,00,00,03,FF,00,00,00,0F," +
        "FC,00,00,00,00,00,00,00,00,00,00,00,00,03,FC,00," +
        "00,00,07,F0,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,F0,00,00,00,00,E0,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00," +
        "00,00,00,00,00,00,00,00";
        Byte[] list = Stream.of(test.split(",")).map(this::hexToByte).toArray(Byte[]::new);
        byte[] bytes = new byte[list.length];
        byte[] outByte = new byte[2];
        outByte[0]=(byte)0xb7;
        outByte[1]=(byte)0xFF;
        int j = 0;
        for(Byte b: list)
            bytes[j++] = b.byteValue();
        sendDataToCOMMport(outByte, bytes);
    }
    byte hexToByte(String hexString) {
        int firstDigit = Character.digit(hexString.charAt(0), 16);
        int secondDigit = Character.digit(hexString.charAt(1), 16);
        return (byte) ((firstDigit << 4) + secondDigit);
    }
}
