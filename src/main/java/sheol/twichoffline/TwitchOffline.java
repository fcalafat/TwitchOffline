package sheol.twichoffline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import sheol.twichoffline.grabber.VODCutterGrabber;

import java.util.Arrays;

/**
 * Created by Fab on 2016-10-21.
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("sheol.twitchoffline")
@Configuration
@PropertySource("classpath:app.properties")
public class TwitchOffline {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(TwitchOffline.class, args);
    }

    @Bean
    public VODCutterGrabber vodCutterGrabber(@Value("${vodcutter.downloadLocation}") String downloadLocation,
                                             @Value("${vodcutter.downloadURL}") String downloadUrl) {
        return new VODCutterGrabber(downloadUrl, downloadLocation);
    }
}
