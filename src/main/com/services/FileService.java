package main.com.services;

import main.com.entity.FtpEntity;
import main.com.intefaces.FileServiceImp;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileService implements FileServiceImp {
    final Logger log = Logger.getLogger(FileService.class);

    /**
     * The essential params for FileService
     */
    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String basePath;
    private final String filePath;
    private final String fileName;
    public FileService(String host, int port, String username, String password, String basePath, String filePath, String fileName) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.basePath = basePath;
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public boolean upload(InputStream inputStream) {
        log.info("FileService.upload entry");
        String temp = "";
        // create ftp client every time when it comes to uploading
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(host, port);
            ftpClient.login(username, password);
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                return false;
            }


            // checking the directory exists or not
            if (!ftpClient.changeWorkingDirectory(basePath + filePath)) {
                String[] dirs = filePath.split("/");
                temp = basePath;
                for (String dir:dirs) {
                    if (dir == null || dir.equals("")) { continue; }
                    temp += "/" + dir;
                    if (ftpClient.changeWorkingDirectory(temp)) {
                        if (!ftpClient.makeDirectory(temp)) return false;
                        else ftpClient.changeWorkingDirectory(temp);
                    }
                }
            }


            // now set the file into binary part
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            if (!ftpClient.storeFile(fileName, inputStream)) return false;
            inputStream.close();
            System.out.println("------------------->>>>>");
            System.out.println(temp);
            ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException(e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return true;
        }
    }


    public boolean download(File file) {
        return true;
    }


    public boolean convert(File file, String form) {
        return true;
    }
}
