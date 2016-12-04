package sheol.twichoffline.grabber;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import sheol.twichoffline.grabber.pojo.ProgressIndicator;
import sheol.twichoffline.grabber.pojo.VODCutter;

import java.io.File;
import java.util.Arrays;

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

    public void downloadVideo(String videoId, String channel, String fileName) {
        String url = createUrl(videoId);
        String response = restTemplate.getForObject(url, String.class);
        VODCutter vodCutter = extractPieces(response);
        vodCutter.setVideoId(videoId);
        downloadParts(vodCutter, channel, fileName);
    }

    private void downloadParts(VODCutter vodCutter, String channel, String fileName) {
        try {
            File outputFile = new File(this.outputFolder + "/" + channel + "/" + fileName);
            if(outputFile.exists()) {
                LOGGER.info("Already downloaded");
                return;
            }
            ProgressIndicator progressIndicator = new ProgressIndicator(vodCutter.getPieces().size());
            LOGGER.info(progressIndicator.getProgress());
            // Eventually create the folder
            outputFile.getParentFile().mkdirs();
            vodCutter.getPieces().forEach(p -> downloadFile(p, outputFile, progressIndicator));
            LOGGER.info("DONE");
        } catch (Exception e) {
            LOGGER.error("Cannot download video pieces", e);
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

    private VODCutter extractPieces(String response) {
        try {
            String pieces = "{" +
                    StringUtils.substring(response, StringUtils.indexOf(response, "\"pieces"), response.length() - 1);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(pieces, VODCutter.class);
        } catch (Exception e) {
            LOGGER.error("Cannot get download link for this video");
            return new VODCutter();
        }
    }

    private String createUrl(String videoId) {
        return StringUtils.replaceAll(this.downloadURL, "\\{\\}", videoId);
    }
}
