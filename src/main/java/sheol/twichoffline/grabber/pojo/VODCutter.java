package sheol.twichoffline.grabber.pojo;

import java.util.Collections;
import java.util.List;

/**
 * Created by PC on 21/10/2016.
 */
public class VODCutter {

    private String videoId;
    private List<String> pieces;

    public VODCutter() {
        pieces = Collections.emptyList();
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public List<String> getPieces() {
        return pieces;
    }

    public void setPieces(List<String> pieces) {
        this.pieces = pieces;
    }

    @Override
    public String toString() {
        return "VODCutter{" +
                "pieces=" + pieces +
                '}';
    }
}
