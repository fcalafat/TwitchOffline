package sheol.twichoffline.grabber;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Fab on 2016-10-21.
 */
public class VODCutterGrabber {
    private String downloadURL;
    private String outputFolder;

    public VODCutterGrabber(String downloadURL, String outputFolder) {
        this.downloadURL = downloadURL;
        this.outputFolder = outputFolder;
        System.out.println(this.outputFolder);
    }

    public void getLinksForVideo(String videoId){
        String url = createUrl(videoId);
        System.out.println(url);
    }

    private String createUrl(String videoId) {
        return StringUtils.replaceAll(videoId, "\\{\\}", videoId);
    }
}
