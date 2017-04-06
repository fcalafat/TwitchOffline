package sheol.twitchoffline.grabber;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import sheol.twitchoffline.grabber.pojo.ProgressIndicator;
import sheol.twitchoffline.grabber.pojo.VODCutter;

import java.io.File;
import java.util.Collections;

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
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));

        httpEntity = new HttpEntity<>(headers);
    }

    /**
     * Download a video from Twitch using the videoId
     * @param videoId
     * @param channel
     * @param fileName
     */
    public void downloadVideo(String videoId, String channel, String fileName) {
        String url = createUrl(videoId);
        String response = restTemplate.getForObject(url, String.class);
        VODCutter vodCutter = extractPieces(response);
        vodCutter.setVideoId(videoId);
        LOGGER.info("VODCutter feedback {}", vodCutter.toString());
        downloadParts(vodCutter, channel, fileName);
    }

    /**
     * Using the details from VODCutter, create a folder and file and start downloading
     * @param vodCutter
     * @param channel
     * @param fileName
     */
    private void downloadParts(VODCutter vodCutter, String channel, String fileName) {
        try {
            File outputFile = new File(this.outputFolder + "/" + channel + "/" + fileName);
            if (outputFile.exists()) {
                LOGGER.info("Already downloaded");
                return;
            }
            ProgressIndicator progressIndicator = new ProgressIndicator(vodCutter.getPieces().size());
            // Eventually create the folder
            if (outputFile.getParentFile().mkdirs()) {
                vodCutter.getPieces().forEach(p -> downloadFile(p, outputFile, progressIndicator));
                LOGGER.info("DONE");
            }
        } catch (Exception e) {
            LOGGER.error("Cannot download video pieces", e);
        }
    }

    /**
     * Download a piece of video
     * @param pieceUrl
     * @param outputFile
     * @param progressIndicator
     */
    private void downloadFile(String pieceUrl, File outputFile, ProgressIndicator progressIndicator) {
        try {
            ResponseEntity<byte[]> response = restTemplate.exchange(
                    pieceUrl, HttpMethod.GET, httpEntity, byte[].class);

            if(response.getStatusCode() != HttpStatus.OK) {
                throw new HttpServerErrorException(response.getStatusCode(), response.toString());
            }

            FileUtils.writeByteArrayToFile(outputFile, response.getBody(), true);

            progressIndicator.increment();
            if (progressIndicator.loggable()) {
                LOGGER.info(progressIndicator.getProgress());
            }
        } catch (Exception e) {
            LOGGER.error("Cannot download video pieces", e);
        }
    }

    /**
     * Parse VODCutter response to get the pieces links
     * @param response
     * @return
     */
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

    /**
     * Use the URL pattern to create the VODCutter link
     * @param videoId
     * @return
     */
    private String createUrl(String videoId) {
        return StringUtils.replaceAll(this.downloadURL, "\\{\\}", videoId);
    }
}
