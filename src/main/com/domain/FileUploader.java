package main.com.domain;

import main.com.entity.FtpEntity;
import main.com.services.FileService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public class FileUploader {
    private final static Logger log = Logger.getLogger(FileUploader.class);

    public static boolean upload(MultipartFile multipartFile, FtpEntity ftpEntity) {
        log.info("FileUploader.upload entry");
        try {
            String fileName = multipartFile.getOriginalFilename();
            String filePath = new DateTime().toString("yyyy/MM/dd");
            System.out.println(ftpEntity.toString());
            FileService fileService = new FileService(ftpEntity.getHost(), ftpEntity.getPort(), ftpEntity.getUsername(), ftpEntity.getPassword(),
                    ftpEntity.getBaseUrl(), filePath, fileName);
            return fileService.upload(multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
