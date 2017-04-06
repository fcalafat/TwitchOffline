
package sheol.twitchoffline.twitch.pojo;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Fps {

    private Integer audioOnly;
    private Double chunked;
    private Double high;
    private Double low;
    private Double medium;
    private Double mobile;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The audioOnly
     */
    public Integer getAudioOnly() {
        return audioOnly;
    }

    /**
     * 
     * @param audioOnly
     *     The audio_only
     */
    public void setAudioOnly(Integer audioOnly) {
        this.audioOnly = audioOnly;
    }

    /**
     * 
     * @return
     *     The chunked
     */
    public Double getChunked() {
        return chunked;
    }

    /**
     * 
     * @param chunked
     *     The chunked
     */
    public void setChunked(Double chunked) {
        this.chunked = chunked;
    }

    /**
     * 
     * @return
     *     The high
     */
    public Double getHigh() {
        return high;
    }

    /**
     * 
     * @param high
     *     The high
     */
    public void setHigh(Double high) {
        this.high = high;
    }

    /**
     * 
     * @return
     *     The low
     */
    public Double getLow() {
        return low;
    }

    /**
     * 
     * @param low
     *     The low
     */
    public void setLow(Double low) {
        this.low = low;
    }

    /**
     * 
     * @return
     *     The medium
     */
    public Double getMedium() {
        return medium;
    }

    /**
     * 
     * @param medium
     *     The medium
     */
    public void setMedium(Double medium) {
        this.medium = medium;
    }

    /**
     * 
     * @return
     *     The mobile
     */
    public Double getMobile() {
        return mobile;
    }

    /**
     * 
     * @param mobile
     *     The mobile
     */
    public void setMobile(Double mobile) {
        this.mobile = mobile;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
