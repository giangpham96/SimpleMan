package leo.me.la.simpleman.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    private class Meta {

        @SerializedName("status")
        @Expose
        private int status;
        @SerializedName("msg")
        @Expose
        private String msg;
        @SerializedName("response_id")
        @Expose
        private String responseId;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getResponseId() {
            return responseId;
        }

        public void setResponseId(String responseId) {
            this.responseId = responseId;
        }

    }

    public class Data {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("image_original_url")
        @Expose
        private String imageOriginalUrl;
        @SerializedName("image_url")
        @Expose
        private String imageUrl;
        @SerializedName("image_mp4_url")
        @Expose
        private String imageMp4Url;
        @SerializedName("image_frames")
        @Expose
        private String imageFrames;
        @SerializedName("image_width")
        @Expose
        private String imageWidth;
        @SerializedName("image_height")
        @Expose
        private String imageHeight;
        @SerializedName("fixed_height_downsampled_url")
        @Expose
        private String fixedHeightDownsampledUrl;
        @SerializedName("fixed_height_downsampled_width")
        @Expose
        private String fixedHeightDownsampledWidth;
        @SerializedName("fixed_height_downsampled_height")
        @Expose
        private String fixedHeightDownsampledHeight;
        @SerializedName("fixed_width_downsampled_url")
        @Expose
        private String fixedWidthDownsampledUrl;
        @SerializedName("fixed_width_downsampled_width")
        @Expose
        private String fixedWidthDownsampledWidth;
        @SerializedName("fixed_width_downsampled_height")
        @Expose
        private String fixedWidthDownsampledHeight;
        @SerializedName("fixed_height_small_url")
        @Expose
        private String fixedHeightSmallUrl;
        @SerializedName("fixed_height_small_still_url")
        @Expose
        private String fixedHeightSmallStillUrl;
        @SerializedName("fixed_height_small_width")
        @Expose
        private String fixedHeightSmallWidth;
        @SerializedName("fixed_height_small_height")
        @Expose
        private String fixedHeightSmallHeight;
        @SerializedName("fixed_width_small_url")
        @Expose
        private String fixedWidthSmallUrl;
        @SerializedName("fixed_width_small_still_url")
        @Expose
        private String fixedWidthSmallStillUrl;
        @SerializedName("fixed_width_small_width")
        @Expose
        private String fixedWidthSmallWidth;
        @SerializedName("fixed_width_small_height")
        @Expose
        private String fixedWidthSmallHeight;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("caption")
        @Expose
        private String caption;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImageOriginalUrl() {
            return imageOriginalUrl;
        }

        public void setImageOriginalUrl(String imageOriginalUrl) {
            this.imageOriginalUrl = imageOriginalUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getImageMp4Url() {
            return imageMp4Url;
        }

        public void setImageMp4Url(String imageMp4Url) {
            this.imageMp4Url = imageMp4Url;
        }

        public String getImageFrames() {
            return imageFrames;
        }

        public void setImageFrames(String imageFrames) {
            this.imageFrames = imageFrames;
        }

        public String getImageWidth() {
            return imageWidth;
        }

        public void setImageWidth(String imageWidth) {
            this.imageWidth = imageWidth;
        }

        public String getImageHeight() {
            return imageHeight;
        }

        public void setImageHeight(String imageHeight) {
            this.imageHeight = imageHeight;
        }

        public String getFixedHeightDownsampledUrl() {
            return fixedHeightDownsampledUrl;
        }

        public void setFixedHeightDownsampledUrl(String fixedHeightDownsampledUrl) {
            this.fixedHeightDownsampledUrl = fixedHeightDownsampledUrl;
        }

        public String getFixedHeightDownsampledWidth() {
            return fixedHeightDownsampledWidth;
        }

        public void setFixedHeightDownsampledWidth(String fixedHeightDownsampledWidth) {
            this.fixedHeightDownsampledWidth = fixedHeightDownsampledWidth;
        }

        public String getFixedHeightDownsampledHeight() {
            return fixedHeightDownsampledHeight;
        }

        public void setFixedHeightDownsampledHeight(String fixedHeightDownsampledHeight) {
            this.fixedHeightDownsampledHeight = fixedHeightDownsampledHeight;
        }

        public String getFixedWidthDownsampledUrl() {
            return fixedWidthDownsampledUrl;
        }

        public void setFixedWidthDownsampledUrl(String fixedWidthDownsampledUrl) {
            this.fixedWidthDownsampledUrl = fixedWidthDownsampledUrl;
        }

        public String getFixedWidthDownsampledWidth() {
            return fixedWidthDownsampledWidth;
        }

        public void setFixedWidthDownsampledWidth(String fixedWidthDownsampledWidth) {
            this.fixedWidthDownsampledWidth = fixedWidthDownsampledWidth;
        }

        public String getFixedWidthDownsampledHeight() {
            return fixedWidthDownsampledHeight;
        }

        public void setFixedWidthDownsampledHeight(String fixedWidthDownsampledHeight) {
            this.fixedWidthDownsampledHeight = fixedWidthDownsampledHeight;
        }

        public String getFixedHeightSmallUrl() {
            return fixedHeightSmallUrl;
        }

        public void setFixedHeightSmallUrl(String fixedHeightSmallUrl) {
            this.fixedHeightSmallUrl = fixedHeightSmallUrl;
        }

        public String getFixedHeightSmallStillUrl() {
            return fixedHeightSmallStillUrl;
        }

        public void setFixedHeightSmallStillUrl(String fixedHeightSmallStillUrl) {
            this.fixedHeightSmallStillUrl = fixedHeightSmallStillUrl;
        }

        public String getFixedHeightSmallWidth() {
            return fixedHeightSmallWidth;
        }

        public void setFixedHeightSmallWidth(String fixedHeightSmallWidth) {
            this.fixedHeightSmallWidth = fixedHeightSmallWidth;
        }

        public String getFixedHeightSmallHeight() {
            return fixedHeightSmallHeight;
        }

        public void setFixedHeightSmallHeight(String fixedHeightSmallHeight) {
            this.fixedHeightSmallHeight = fixedHeightSmallHeight;
        }

        public String getFixedWidthSmallUrl() {
            return fixedWidthSmallUrl;
        }

        public void setFixedWidthSmallUrl(String fixedWidthSmallUrl) {
            this.fixedWidthSmallUrl = fixedWidthSmallUrl;
        }

        public String getFixedWidthSmallStillUrl() {
            return fixedWidthSmallStillUrl;
        }

        public void setFixedWidthSmallStillUrl(String fixedWidthSmallStillUrl) {
            this.fixedWidthSmallStillUrl = fixedWidthSmallStillUrl;
        }

        public String getFixedWidthSmallWidth() {
            return fixedWidthSmallWidth;
        }

        public void setFixedWidthSmallWidth(String fixedWidthSmallWidth) {
            this.fixedWidthSmallWidth = fixedWidthSmallWidth;
        }

        public String getFixedWidthSmallHeight() {
            return fixedWidthSmallHeight;
        }

        public void setFixedWidthSmallHeight(String fixedWidthSmallHeight) {
            this.fixedWidthSmallHeight = fixedWidthSmallHeight;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

    }
}
