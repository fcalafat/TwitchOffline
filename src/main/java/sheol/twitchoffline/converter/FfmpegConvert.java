package sheol.twitchoffline.converter;

import org.apache.commons.io.FilenameUtils;

/**
 * Created by PC on 22/10/2016.
 */
public class FfmpegConvert {

    public static void convert(String input) {
        String output = input.replace(FilenameUtils.getExtension(input), "mp4");
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("external\\ffmpeg\\bin\\ffmpeg.exe -i " + input + " " + output);
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can't convert file");
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }
}
