package main.com.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import main.com.domain.FileDownloader;
import main.com.domain.FileUploader;
import main.com.entity.DownloadEntity;
import main.com.entity.FtpEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;


/**
 * the controller defines the upload, download and convert
 */
@RestController
public class FileController {
    final Logger log = Logger.getLogger(FileController.class);
    @Autowired
    FtpEntity ftpEntity;
    @Autowired
    DownloadEntity downloadEntity;

    /**
     * here is the controller of uploading
     * @param file the file needs to upload, read in as Multipart file
     * @return boolean
     */
    @PostMapping(value = "/upload" , headers = "content-type=multipart/form-data")
    @ApiOperation(value = "File upload", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public boolean upload(@ApiParam(value = "file", required = true) MultipartFile file) {
        log.info("FileController.upload method Entry, file is ready to upload");
        System.out.println(file.getOriginalFilename());
        return FileUploader.upload(file, ftpEntity);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/download")

    @ApiOperation(value = "File download", produces="application/octet-stream")
    public String download(@RequestParam(name = "fileName") String fileName, HttpServletResponse response) {

        log.info("FileController.download method Entry, file is ready to download");
        return FileDownloader.download(fileName, response, downloadEntity);
    }

}

