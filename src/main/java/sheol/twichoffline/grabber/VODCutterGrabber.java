package sheol.twichoffline.grabber;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import sheol.twichoffline.grabber.pojo.ProgressIndicator;
import sheol.twichoffline.grabber.pojo.VODCutter;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Fab on 2016-10-21.
 */
public class VODCutterGrabber {
    private static final Logger LOGGER = LoggerFactory.getLogger(VODCutterGrabber.class);
    private String downloadURL;
    private String outputFolder;
    private RestTemplate restTemplate;
    private HttpEntity<String> httpEntity;

    public VODCutterGrabber(RestTemplate restTemplate, String downloadURL, String outputFolder) {
        this.restTemplate = restTemplate;
        this.downloadURL = downloadURL;
        this.outputFolder = outputFolder;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

        httpEntity = new HttpEntity<>(headers);
    }

    public String downloadVideo(String videoId) {
        String url = createUrl(videoId);
        String response = restTemplate.getForObject(url, String.class);
        VODCutter vodCutter = extractPieces(response);
        vodCutter.setVideoId(videoId);
        return downloadParts(vodCutter);
    }

    private String downloadParts(VODCutter vodCutter) {
        try {
            ProgressIndicator progressIndicator = new ProgressIndicator(vodCutter.getPieces().size());
            LOGGER.info(progressIndicator.getProgress());
            String filename = extractFileName(vodCutter.getVideoId());
            File outputFile = new File(this.outputFolder + "/" + filename);
            // Eventually create the folder
            outputFile.getParentFile().mkdirs();
            vodCutter.getPieces().forEach(p -> downloadFile(p, outputFile, progressIndicator));
            LOGGER.info("DONE");
            return outputFile.getPath();
        } catch (Exception e) {
            LOGGER.error("Cannot download video pieces", e);
            return "";
        }
    }

    private void downloadFile(String p, File outputFile, ProgressIndicator progressIndicator) {
        try {
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    p, HttpMethod.GET, httpEntity, byte[].class);

            FileUtils.writeByteArrayToFile(outputFile, response.getBody(), true);
            progressIndicator.increment();
            LOGGER.info(progressIndicator.getProgress());
        } catch (Exception e) {
            LOGGER.error("Cannot download video pieces", e);
        }
    }

    private String extractFileName(String p) throws ParseException {
        return p + ".ts";
    }

    private VODCutter extractPieces(String response) {
        try {
            String pieces = "{" +
                    StringUtils.substring(response, StringUtils.indexOf(response, "\"pieces"), response.length() - 1);
            Gson gson = new Gson();
            return gson.fromJson(pieces, VODCutter.class);
        } catch (Exception e) {
            LOGGER.error("Cannot get download link for this video", e);
            return new VODCutter();
        }
    }

    private String createUrl(String videoId) {
        return StringUtils.replaceAll(this.downloadURL, "\\{\\}", videoId);
    }
}
