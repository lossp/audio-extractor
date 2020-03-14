package main.com.constants;


import java.util.ArrayList;
import java.util.List;

/**
 * The class defines the common Video types, I chosen a regular class over the Interface
 */
public class VideoForm {
    private static final List<String> types = new ArrayList<>();
    // video types definitions
    private static final String MKV = "mkv";
    private static final String AVI = "avi";
    private static final String MP4 = "mp4";
    private static final String FLV = "flv";
    private static final String MPEG = "mpeg";
    private static final String TGP = "3gp";
    private static final String MFV = "m4v";
    private static final String MOV = "mov";
    private static final String WEBM = "webm";


    // initialize the types
    static {
        types.add(MKV);
        types.add(AVI);
        types.add(MP4);
        types.add(FLV);
        types.add(MPEG);
        types.add(TGP);
        types.add(MFV);
        types.add(MOV);
        types.add(WEBM);
    }

    /**
     * See if the type was contained in the types
     * @param type the video type about to check
     * @return boolean
     */
    public static boolean contains(String type) {
        return types.contains(type);
    }
}
