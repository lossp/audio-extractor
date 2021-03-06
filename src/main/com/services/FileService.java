package main.com.services;

import main.com.constants.VideoForm;
import main.com.entity.ConvertEntity;
import main.com.intefaces.FileServiceImp;
import main.com.uitls.FileNameTool;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService implements FileServiceImp {
    final Logger log = Logger.getLogger(FileService.class);
    private static final String VIDEO_FILE_NAME_PATTERN = "([^\\s]+(\\.(?i)(mkv|avi|3gp|mp4|mpeg|flv|mov|webm|rmvb|rm))$)";
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
    private final String ffmpegPath;
    public FileService(String protocol, String host, int port, String username, String password, String basePath, String filePath, String fileName, String ffmpegPath) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.basePath = basePath;
        this.filePath = filePath;
        this.fileName = fileName;
        this.ffmpegPath = ffmpegPath;
    }

    public FileService(String fileName, String basePath) {
        this("http", "", 0, "", "", basePath, "", fileName, "");
    }
    public FileService(String fileName, String basePath, String ffmpegPath) {
        this("http", "", 0, "", "", basePath, "", fileName, ffmpegPath);
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


    /**
     * the achievement of downloading
     * @param fileName the file`s name, the file`s format is included
     * @param response http response
     * @return
     */
    public String download(String fileName, HttpServletResponse response) {
        log.info("FileService.download Entering");
        File file = new File(basePath, fileName);
        System.out.println("base path = " + basePath);
        System.out.println("fileName = " + fileName);
        System.out.println("file is a file " + file.getPath());
        String copiedFileName = fileName;
        String[] params = copiedFileName.split("\\.");
        System.out.println(params.toString());
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


    /**
     * convert the file into specific form, the main idea of this is that use ffmpeg to convert the video
     * @param fileName the file needs to convert
     * @param format the file new format
     * @return
     */
    public String convert(String fileName, String format) {
        log.info("FileService.convert method entry");
        // TODO first of, check the file is video or not

        // check the file name`s correctness;
        if (!fileName.matches(VIDEO_FILE_NAME_PATTERN)) throw new IllegalArgumentException("file name is incorrect, please check the file name contains a name and a format");

        // check the file format is allowed
        String copiedFileName = fileName;
        String[] params = copiedFileName.split("\\.");
        String fileFormat = params[1];
        if (!VideoForm.contains(fileFormat)) throw new IllegalArgumentException("file format is not supported, sorry");
        String outputFileName = FileNameTool.swapFileNameFormat(fileName, format);
        final List<String> command = new ArrayList<>();
        command.add(ffmpegPath);
        command.add("-i");
        command.add(basePath + "/" + fileName);
        command.add(basePath + "/" + outputFileName);
        for (String co:command) {
            System.out.println(co);
        }
        ProcessBuilder builder = new ProcessBuilder(command);
        try {
            Process process = builder.start();
            InputStream errorStream = process.getErrorStream();
            InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            errorStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean pickAndConvert(String fileName, String start, String end, String form) {
        return true;
    }
}
