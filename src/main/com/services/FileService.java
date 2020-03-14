package main.com.services;

import main.com.intefaces.FileServiceImp;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileService implements FileServiceImp {
    final Logger log = Logger.getLogger(FileService.class);

    /**
     * The essential params for FileService, it basically defines the specific actions of upload
     * first opening a ftp client, then checking the directory, saving the file at the end. Throw Exceptions if any of it occurs
     * the host, port, username, password, basePath, filePath is passed in by the entity ftpEntity.
     */
    private final String protocol;
    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String basePath;
    private final String filePath;
    private final String fileName;
    public FileService(String protocol, String host, int port, String username, String password, String basePath, String filePath, String fileName) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.basePath = basePath;
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public FileService(String fileName, String basePath) {
        this("http", "", 0, "", "", basePath, "", fileName);
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


    public String download(String fileName, HttpServletResponse response) {
        log.info("FileService.download Entering");
        File file = new File(basePath, fileName);
        System.out.println("base path = " + basePath);
        System.out.println("fileName = " + fileName);
        System.out.println("file is a file " + file.getPath());
        String copiedFileName = fileName;
        String[] params = copiedFileName.split("\\.");
        System.out.println(params.toString());
        System.out.println("=============[0] " + params[0]);
        System.out.println("=============[1] " + params[1]);
        if (file.exists()) {
            byte[] buffer = new byte[1024 * 8];
            FileInputStream fileInputStream = null;
            BufferedInputStream bufferedInputStream = null;
            try {
                response.setContentType("application/octet-stream;charset=UTF-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + params[0] + "." + params[1]);
                fileInputStream = new FileInputStream(file);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                OutputStream outputStream = response.getOutputStream();
                int index = 0;
                while ((index = bufferedInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, index);
                }
                outputStream.flush();
                System.out.println("download success");
            } catch (Exception exception) {
                exception.printStackTrace();
            } finally {
                try {
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    if (bufferedInputStream !=null) {
                        bufferedInputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("没有匹配成功");
        }
        return null;
    }


    public boolean convert(File file, String form) {
        return true;
    }
}
