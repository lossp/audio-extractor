package main.com.domain;

import main.com.entity.ConvertEntity;
import main.com.services.FileService;
import org.apache.log4j.Logger;

public class FileConvertor {
    private final static Logger log = Logger.getLogger(FileDownloader.class);
    public static boolean convert(String fileName, String format, ConvertEntity convertEntity) {
        log.info("FileConverter.convert ent");
        FileService fileService = new FileService(fileName, convertEntity.getFilelocation(), convertEntity.getLocation());
        String result = fileService.convert(fileName, format);
        return true;
    }
}
