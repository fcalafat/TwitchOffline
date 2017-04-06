package sheol.twitchoffline.twitch;

import io.mikael.urlbuilder.UrlBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sheol.twitchoffline.twitch.pojo.LatestVideos;

/**
 * Created by Fab on 2016-10-26.
 */
public class TwitchApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchApi.class);

    @Autowired
    private RestTemplate restTemplate;

    public TwitchApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LatestVideos getLatestVideos(String channel) {
        LOGGER.info("Getting videos for channel [{}]", channel);
        UrlBuilder url = UrlBuilder.empty()
                .withScheme("https")
                .withHost("api.twitch.tv")
                .withPath("/kraken/channels/" + channel + "/videos")
                .addParameter("client_id", "ry6l4jazsamhf63ilxpspglh3r9pwpu")
                .addParameter("limit", "100")
                .addParameter("broadcasts", "true");

        return restTemplate.getForObject(url.toUri(), LatestVideos.class);
    }
}
