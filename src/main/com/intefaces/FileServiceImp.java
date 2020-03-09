package main.com.intefaces;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface FileServiceImp {

    /**
     * upload a file
     * @param inputStream the file stream needs to upload
     * @return true for upload successfully, false for failed to upload
     * @Exception IOException
     */
    public boolean upload(InputStream inputStream) throws IOException;


    /**
     * download a file
     * @param file the file needs to download
     * @return true for download the file successfully, false for failed to download the file
     * @Exception IOException
     */
    public boolean download(File file) throws IOException;

    /**
     * convert a file into specific form
     * @param file the file needs to convert
     * @return true for convert successfully, false for failed to convert the file
     */
    public boolean convert(File file, String form);

}
