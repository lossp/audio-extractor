package main.com.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import main.com.domain.FileUploader;
import main.com.entity.FtpEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {
    final Logger log = Logger.getLogger(FileController.class);
    @Autowired
    FtpEntity ftpEntity;

    @PostMapping(value = "/upload" , headers = "content-type=multipart/form-data")
    @ApiOperation(value = "File upload", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public boolean upload(@ApiParam(value = "file", required = true) MultipartFile file) {
        System.out.println("------------->>>>>");
        System.out.println(file.getOriginalFilename());
        return FileUploader.upload(file, ftpEntity);
    }

    @GetMapping(value = "/fuckyou")
    @ApiOperation(value = "test me")
    public String getFtpentity() {
        return ftpEntity.toString();
    }
}
