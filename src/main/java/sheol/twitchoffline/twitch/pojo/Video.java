
package sheol.twitchoffline.twitch.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import sheol.twitchoffline.twitch.deserializer.DateDeserializer;

import java.util.*;

public class Video {

    private String title;
    private String description;
    private Integer broadcastId;
    private String broadcastType;
    private String status;
    private String language;
    private String tagList;
    private Integer views;
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonProperty("created_at")
    private Date createdAt;
    private String url;
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonProperty("published_at")
    private Date publishedAt;
    @JsonProperty("_id")
    private String id;
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonProperty("recorded_at")
    private Date recordedAt;
    private String game;
    private Integer length;
    private String preview;
    private String animatedPreview;
    private List<Thumbnail> thumbnails = new ArrayList<Thumbnail>();
    private Fps fps;
    private Resolutions resolutions;
    private Links_ links;
    private Channel channel;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The broadcastId
     */
    public Integer getBroadcastId() {
        return broadcastId;
    }

    /**
     * 
     * @param broadcastId
     *     The broadcast_id
     */
    public void setBroadcastId(Integer broadcastId) {
        this.broadcastId = broadcastId;
    }

    /**
     * 
     * @return
     *     The broadcastType
     */
    public String getBroadcastType() {
        return broadcastType;
    }

    /**
     * 
     * @param broadcastType
     *     The broadcast_type
     */
    public void setBroadcastType(String broadcastType) {
        this.broadcastType = broadcastType;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 
     * @param language
     *     The language
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * 
     * @return
     *     The tagList
     */
    public String getTagList() {
        return tagList;
    }

    /**
     * 
     * @param tagList
     *     The tag_list
     */
    public void setTagList(String tagList) {
        this.tagList = tagList;
    }

    /**
     * 
     * @return
     *     The views
     */
    public Integer getViews() {
        return views;
    }

    /**
     * 
     * @param views
     *     The views
     */
    public void setViews(Integer views) {
        this.views = views;
    }

    /**
     * 
     * @return
     *     The createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     *     The created_at
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The publishedAt
     */
    public Date getPublishedAt() {
        return publishedAt;
    }

    /**
     *
     * @param publishedAt
     *     The published_at
     */
    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The _id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The recordedAt
     */
    public Date getRecordedAt() {
        return recordedAt;
    }

    /**
     *
     * @param recordedAt
     *     The recorded_at
     */
    public void setRecordedAt(Date recordedAt) {
        this.recordedAt = recordedAt;
    }

    /**
     * 
     * @return
     *     The game
     */
    public String getGame() {
        return game;
    }

    /**
     * 
     * @param game
     *     The game
     */
    public void setGame(String game) {
        this.game = game;
    }

    /**
     * 
     * @return
     *     The length
     */
    public Integer getLength() {
        return length;
    }

    /**
     * 
     * @param length
     *     The length
     */
    public void setLength(Integer length) {
        this.length = length;
    }

    /**
     * 
     * @return
     *     The preview
     */
    public String getPreview() {
        return preview;
    }

    /**
     * 
     * @param preview
     *     The preview
     */
    public void setPreview(String preview) {
        this.preview = preview;
    }

    /**
     * 
     * @return
     *     The animatedPreview
     */
    public String getAnimatedPreview() {
        return animatedPreview;
    }

    /**
     * 
     * @param animatedPreview
     *     The animated_preview
     */
    public void setAnimatedPreview(String animatedPreview) {
        this.animatedPreview = animatedPreview;
    }

    /**
     * 
     * @return
     *     The thumbnails
     */
    public List<Thumbnail> getThumbnails() {
        return thumbnails;
    }

    /**
     * 
     * @param thumbnails
     *     The thumbnails
     */
    public void setThumbnails(List<Thumbnail> thumbnails) {
        this.thumbnails = thumbnails;
    }

    /**
     * 
     * @return
     *     The fps
     */
    public Fps getFps() {
        return fps;
    }

    /**
     * 
     * @param fps
     *     The fps
     */
    public void setFps(Fps fps) {
        this.fps = fps;
    }

    /**
     * 
     * @return
     *     The resolutions
     */
    public Resolutions getResolutions() {
        return resolutions;
    }

    /**
     * 
     * @param resolutions
     *     The resolutions
     */
    public void setResolutions(Resolutions resolutions) {
        this.resolutions = resolutions;
    }

    /**
     * 
     * @return
     *     The links
     */
    public Links_ getLinks() {
        return links;
    }

    /**
     * 
     * @param links
     *     The _links
     */
    public void setLinks(Links_ links) {
        this.links = links;
    }

    /**
     * 
     * @return
     *     The channel
     */
    public Channel getChannel() {
        return channel;
    }

    /**
     * 
     * @param channel
     *     The channel
     */
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
