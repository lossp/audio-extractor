package main.com.services;

import main.com.intefaces.FileServiceImp;
import org.apache.log4j.Logger;

import java.io.File;

public class FileService implements FileServiceImp {
    final Logger log = Logger.getLogger(FileService.class);
    private final File file;
    public FileService(File file) {
        this.file = file;
    }

    public boolean upload(File file) {
        return true;
    }


    public boolean download(File file) {
        return true;
    }


    public boolean convert(File file, String form) {
        return true;
    }
}
