package sheol.twitchoffline;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import sheol.twitchoffline.grabber.VODCutterGrabber;
import sheol.twitchoffline.twitch.TwitchApi;
import sheol.twitchoffline.twitch.pojo.Video;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Fab on 2016-10-21.
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("sheol.twitchoffline")
@Configuration
public class TwitchOffline implements CommandLineRunner {

    private static final String CHANNELS_TXT = "channels.txt";
    private static final String DOWNLOADED_TXT = "downloaded.txt";
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchOffline.class);
    @Autowired
    private VODCutterGrabber vodCutterGrabber;

    @Autowired
    private TwitchApi twitchApi;

    public static void main(String[] args) {
        LOGGER.info("Started TwitchOffline");
        SpringApplication app = new SpringApplication(TwitchOffline.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.setLogStartupInfo(false);
        app.run(args);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());

        return restTemplate;
    }

    @Bean
    public VODCutterGrabber vodCutterGrabber(@Autowired RestTemplate restTemplate,
                                             @Value("${vodcutter.downloadLocation}") String downloadLocation,
                                             @Value("${vodcutter.downloadURL}") String downloadUrl) {
        return new VODCutterGrabber(restTemplate, downloadUrl, downloadLocation);
    }

    @Bean
    public TwitchApi twitchApi(@Autowired RestTemplate restTemplate) {
        return new TwitchApi(restTemplate);
    }

    @Override
    public void run(String... strings) throws Exception {
        LOGGER.info("Let's do this");
        List<String> videos = new ArrayList<>();

        File videosFile = new File(CHANNELS_TXT);
        File downloadedFile = new File(DOWNLOADED_TXT);

        if (strings.length == 0) {
            LOGGER.info("Reading channels from {} file", CHANNELS_TXT);
            // read from txt file
            if (videosFile.exists()) {
                videos.addAll(FileUtils.readLines(videosFile, "UTF-8"));
            }
        } else {
            videos.addAll(Stream.of(strings).collect(Collectors.toList()));
        }

        videos.stream().map(twitchApi::getLatestVideos)
                .forEach(latestVideo -> latestVideo.getVideos()
                        .stream()
                        .filter(v -> toGrab(downloadedFile, v))
                        .forEach(video -> {
                            LOGGER.info("Handling video: Channel [{}] - Name: [{}] - Published: [{}]",
                                    video.getChannel().getDisplayName(),
                                    video.getTitle(),
                                    DateFormatUtils.format(video.getPublishedAt(), "yyyy-MM-dd HH:mm:ss"));
                            vodCutterGrabber.downloadVideo(video.getId().substring(1),
                                    video.getChannel().getDisplayName(),
                                    extractFileName(video));
                            try {
                                FileUtils.writeStringToFile(downloadedFile, extractFileName(video) + "\n", Charset.forName("UTF-8"), true);
                            } catch (IOException e) {
                                LOGGER.info("Cannot log downloaded file!!!!");
                            }
                        }));

        LOGGER.info("Chini!!!!");
    }

    private String extractFileName(Video video) {
        return video.getChannel().getDisplayName() +
                "-" +
                DateFormatUtils.format(video.getPublishedAt(), "yyyy-MM-dd") +
                "-" +
                video.getTitle() +
                ".ts";
    }


    private boolean toGrab(File downloadedFile, Video video) {
        Date yesterday = DateUtils.addDays(new Date(), -1);
        return video.getPublishedAt().after(yesterday) && notDownloadedYet(downloadedFile, extractFileName(video));
    }

    private boolean notDownloadedYet(File downloadedFile, String s) {
        try {
            if (!downloadedFile.exists() && downloadedFile.createNewFile()) {
                List<String> contents = FileUtils.readLines(downloadedFile, Charset.forName("UTF-8"));
                return !CollectionUtils.contains(contents.iterator(), s);
            }
        } catch (IOException e) {
            return true;
        }
        return true;
    }
}
