package sheol.twichoffline;

import org.apache.commons.io.FileUtils;
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
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import sheol.twichoffline.grabber.VODCutterGrabber;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fab on 2016-10-21.
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("sheol.twitchoffline")
@Configuration
@PropertySource("classpath:app.properties")
public class TwitchOffline implements CommandLineRunner {

    private static final String VIDEOS_TXT = "videos.txt";
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchOffline.class);
    @Autowired
    private VODCutterGrabber vodCutterGrabber;

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

    @Override
    public void run(String... strings) throws Exception {
        List<String> videos = new ArrayList<>();
        if(strings.length == 0) {
            LOGGER.info("Reading video IDs from {} file", VIDEOS_TXT);
            // read from txt file
            File videosFile = new File(VIDEOS_TXT);
            if(videosFile.exists()) {
                videos.addAll(FileUtils.readLines(videosFile, "UTF-8"));
            }
        } else {
            videos.addAll(CollectionUtils.<String>arrayToList(strings));
        }

        videos.forEach(v -> {
            LOGGER.info("Handling video with id [{}]", v);
            vodCutterGrabber.downloadVideo(v);
            //FfmpegConvert.convert(downloadfile);
        });
    }
}
