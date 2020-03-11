package main.com.domain;

import main.com.entity.FtpEntity;
import main.com.services.FileService;
import org.apache.log4j.Logger;

public class FileDownloader {
    private final static Logger log = Logger.getLogger(FileDownloader.class);

    public static boolean download(String fileName, String savingPath, FtpEntity ftpEntity) {
        log.info("FileDownloader.download entry");
        FileService fileService = new FileService("ftp",ftpEntity.getHost(), ftpEntity.getPort(), ftpEntity.getUsername(), ftpEntity.getPassword(),
                ftpEntity.getBaseUrl(), ftpEntity.getBaseUrl() + fileName, fileName);
        return fileService.download(savingPath);
    }
}
