package sheol.twichoffline.converter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionContext;
import org.w3c.dom.ProcessingInstruction;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;

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
