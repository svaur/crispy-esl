package ru.mvp.rsreu.integration.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.StreamSupport;

/**
 * Created by Art on 14.11.2018.
 */
@Component
@EnableScheduling
public class FileMonitor {

    private final static Logger LOGGER = LoggerFactory.getLogger(FileMonitor.class);

    private String directory = "src\\main\\resources\\tempDir";
    private long checkInterval = 5000;
    private Map<String, FileInfo> fileInfoMap = new HashMap<>();

    @Scheduled(fixedDelay = 5000)
    public void task() throws InterruptedException {
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(Paths.get(directory))) {
            StreamSupport.stream(ds.spliterator(), false)
                    .filter(e -> checkChangeFile(e))
                    .forEach(e -> System.out.println(e));
        } catch (IOException e) {
            LOGGER.error("Catch error: ", e);
        }
    }

    private boolean checkChangeFile(Path path) {
        File file = path.toFile();
        String fileName = file.getName();
        if (!fileInfoMap.containsKey(fileName)) {
            fileInfoMap.put(fileName, new FileInfo(file.length(), file.lastModified()));
            return false;
        }
        FileInfo fileInfo = fileInfoMap.get(fileName);
        long currentTime = new Date().getTime();
        boolean firstFlag = fileInfo.getLastSize() == file.length() &&
                fileInfo.getLastUpdate() == file.lastModified() &&
                currentTime - fileInfo.lastUpdate > checkInterval;
        if (firstFlag && fileInfo.isDone()) {
            return false;
        } else if (firstFlag && !fileInfo.isDone()) {
            fileInfo.setDone(true);
            fileInfoMap.put(fileName, fileInfo);
            return true;
        } else {
            fileInfo.setLastSize(file.length());
            fileInfo.setLastUpdate(file.lastModified());
            fileInfo.setDone(false);
            fileInfoMap.put(fileName, fileInfo);
            return false;
        }
    }

    private class FileInfo {
        private long lastSize;
        private long lastUpdate;
        private boolean done;

        public FileInfo(long lastSize, long lastUpdate) {
            this.lastSize = lastSize;
            this.lastUpdate = lastUpdate;

        }

        public long getLastSize() {
            return lastSize;
        }

        public void setLastSize(long lastSize) {
            this.lastSize = lastSize;
        }

        public long getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(long lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }
    }
}
