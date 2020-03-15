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
     * @param fileName file name
     * @return true for convert successfully, false for failed to convert the file
     */
    public String convert(String fileName, String form);


    /**
     * pick a clip from the video and convert
     * @param fileName file`name
     * @param start the time when the pick begins, format requires HH:MM:SS
     * @param end the time when the pick ends, format requires HH:MM:SS
     * @param form the form converts to
     * @return boolean
     */
    public boolean pickAndConvert(String fileName, String start, String end, String form);

}
