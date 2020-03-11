package main.com.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import main.com.domain.FileDownloader;
import main.com.domain.FileUploader;
import main.com.entity.FtpEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * the controller defines the upload, download and convert
 */
@RestController
public class FileController {
    final Logger log = Logger.getLogger(FileController.class);
    @Autowired
    FtpEntity ftpEntity;

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

    @PostMapping(value = "/download")
    @ApiOperation(value = "File download")
    public boolean download(@RequestParam("fileName") String fileName,
                            @RequestParam("savingPath") String savingPath) {

        log.info("FileController.download method Entry, file is ready to download");
        return FileDownloader.download(fileName, savingPath, ftpEntity);
    }

}
