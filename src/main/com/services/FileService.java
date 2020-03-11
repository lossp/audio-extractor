package main.com.services;

import main.com.intefaces.FileServiceImp;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import java.io.*;

public class FileService implements FileServiceImp {
    final Logger log = Logger.getLogger(FileService.class);

    /**
     * The essential params for FileService, it basically defines the specific actions of upload
     * first opening a ftp client, then checking the directory, saving the file at the end. Throw Exceptions if any of it occurs
     * the host, port, username, password, basePath, filePath is passed in by the entity ftpEntity.
     */
    private final String protocal;
    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String basePath;
    private final String filePath;
    private final String fileName;
    public FileService(String protocal, String host, int port, String username, String password, String basePath, String filePath, String fileName) {
        this.protocal = protocal;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.basePath = basePath;
        this.filePath = filePath;
        this.fileName = fileName;
    }

    /**
     * the achievement of uploading.
     * @param inputStream the file stream needs to upload
     * @return boolean
     */
    public boolean upload(InputStream inputStream) {
        // TODO this part can be done better
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


    public boolean download(String savingPath) {
        log.info("FileService.download Entering");
        FTPClient ftpClient = new FTPClient();
        boolean result = false;
        try {
            ftpClient.connect(host, port);
            ftpClient.login(username, password);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory("/Desktop");
            ftpClient.enterLocalPassiveMode();

            FTPFile[] files = ftpClient.listFiles();
            for (FTPFile file:files) {
                System.out.println("+++++ " + new String(file.getName().getBytes("UTF-8"), FTP.DEFAULT_CONTROL_ENCODING));
                if (file.getName().equals(fileName)) {
                    File downFile = new File(savingPath + File.separator + file.getName());
                    System.out.println("===" + savingPath + File.separator + file.getName());
                    OutputStream outputStream = new FileOutputStream(downFile);
                    result = ftpClient.retrieveFile(new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"), outputStream);
                    outputStream.flush();
                    outputStream.close();
                }
            }
            System.out.println("fileName" + fileName);
            System.out.println("savingPath" + savingPath);
            return result;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean convert(File file, String form) {
        return true;
    }
}
