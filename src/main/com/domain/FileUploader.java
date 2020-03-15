package main.com.domain;

import main.com.entity.FtpEntity;
import main.com.services.FileService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * The class defines the action of uploading
 * it call the upload service to do the uploading, the base path and filename is done in this part
 */
public class FileUploader {
    private final static Logger log = Logger.getLogger(FileUploader.class);

    /**
     * A static method, it initializes a new file service every-time when it`s called. So there`s a one to one relation between fileService and uploading action
     * @param multipartFile the file uploaded
     * @param ftpEntity the configuration entity
     * @return boolean
     */
    public static boolean upload(MultipartFile multipartFile, FtpEntity ftpEntity) {
        log.info("FileUploader.upload entry");
        try {
            String fileName = multipartFile.getOriginalFilename();
            String filePath = new DateTime().toString("yyyy/MM/dd");
            System.out.println(ftpEntity.toString());
            FileService fileService = new FileService("ftp", ftpEntity.getHost(), ftpEntity.getPort(), ftpEntity.getUsername(), ftpEntity.getPassword(),
                    ftpEntity.getBaseUrl(), filePath, fileName, "");
            return fileService.upload(multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
