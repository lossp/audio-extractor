package main.com.uitls;


public class FileNameTool {
    /**
     * swap the file name with the new format, example, out.mp4 -> out.mp3
     * @param fileName the original file name, contains a original name and a format, divided by "."
     * @param format the format it needs to be converted into
     * @return String represents a new file name
     */
    public static String swapFileNameFormat(String fileName, String format) {
        String[] params = fileName.split("\\.");
        String fileOriginalName = params[0];
        return fileOriginalName + "." + format;
    }
}
