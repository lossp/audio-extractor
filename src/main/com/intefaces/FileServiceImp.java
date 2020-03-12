package main.com.intefaces;

import javax.servlet.http.HttpServletResponse;
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
     * @param fileName the file needs to be download
     * @param response http response
     * @return return a string
     * @Exception IOException
     */
    public String download(String fileName, HttpServletResponse response) throws IOException;

    /**
     * convert a file into specific form
     * @param file the file needs to convert
     * @return true for convert successfully, false for failed to convert the file
     */
    public boolean convert(File file, String form);

}
