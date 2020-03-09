package main.com.services;

import java.io.File;

public interface FileService {

    /**
     * upload a file
     * @param file the file needs to upload
     * @return true for upload successfully, false for failed to upload
     */
    public boolean upload(File file);


    /**
     * download a file
     * @param file the file needs to download
     * @return true for download the file successfully, false for failed to download the file
     */
    public boolean download(File file);

    /**
     * convert a file into specific form
     * @param file the file needs to convert
     * @return true for convert successfully, false for failed to convert the file
     */
    public boolean convert(File file, String form);

}
