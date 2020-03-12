package main.com.domain;

import main.com.entity.DownloadEntity;
import main.com.services.FileService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;

public class FileDownloader {
    private final static Logger log = Logger.getLogger(FileDownloader.class);

    public static String download(String fileName, HttpServletResponse response, DownloadEntity downloadEntity) {
        log.info("FileDownloader.download entry");
        FileService fileService = new FileService(fileName, downloadEntity.getDirectory());
        return fileService.download(fileName, response);
    }
}
