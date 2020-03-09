package main.com.domain;

import main.com.intefaces.FileServiceImp;
import org.apache.log4j.Logger;

import java.io.File;

public class FileUploader {
    private final Logger log = Logger.getLogger(FileUploader.class);
    private final File file;
    public FileUploader(File file) {
        this.file = file;
    }

    public boolean upload(FileServiceImp fsImp) {
        return fsImp.upload(file);
    }
}
