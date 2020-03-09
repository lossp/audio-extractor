package main.com.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class File {
    @PostMapping(value = "/upload" , headers = "content-type=multipart/form-data")
    @ApiOperation(value = "File upload", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public boolean upload(@ApiParam(value = "file", required = true) MultipartFile file) {
        return true;
    }
}
