package sheol.twichoffline.twitch;

import io.mikael.urlbuilder.UrlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import sheol.twichoffline.twitch.pojo.LatestVideos;

/**
 * Created by Fab on 2016-10-26.
 */
public class TwitchApi {

    @Autowired
    private RestTemplate restTemplate;

    public TwitchApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public LatestVideos getLatestVideos(String channel) {
        UrlBuilder url = UrlBuilder.empty()
                .withScheme("https")
                .withHost("api.twitch.tv")
                .withPath("/kraken/channels/" + channel + "/videos")
                .addParameter("client_id", "ry6l4jazsamhf63ilxpspglh3r9pwpu")
                .addParameter("limit", "100")
                .addParameter("broadcasts", "true");

        LatestVideos latestVideos = restTemplate.getForObject(url.toUri(), LatestVideos.class);

        return latestVideos;
    }
}
