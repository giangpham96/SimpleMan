package leo.me.la.simpleman.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @SerializedName("images")
        @Expose
        private Images images;

        public Images getImages() {
            return images;
        }

        public void setImages(Images images) {
            this.images = images;
        }

    }

    public static class Images {

        @SerializedName("original")
        @Expose
        private Original original;
        @SerializedName("fixed_width")
        @Expose
        private FixedWidth fixedWidth;
        @SerializedName("fixed_height")
        @Expose
        private FixedHeight fixedHeight;

        public Original getOriginal() {
            return original;
        }

        public void setOriginal(Original original) {
            this.original = original;
        }

        public FixedWidth getFixedWidth() {
            return fixedWidth;
        }

        public void setFixedWidth(FixedWidth fixedWidth) {
            this.fixedWidth = fixedWidth;
        }

        public FixedHeight getFixedHeight() {
            return fixedHeight;
        }

        public void setFixedHeight(FixedHeight fixedHeight) {
            this.fixedHeight = fixedHeight;
        }
    }

    public static class Original {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("width")
        @Expose
        private String width;
        @SerializedName("height")
        @Expose
        private String height;
        @SerializedName("mp4")
        @Expose
        private String mp4;
        @SerializedName("webp")
        @Expose
        private String webp;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getMp4() {
            return mp4;
        }

        public void setMp4(String mp4) {
            this.mp4 = mp4;
        }

        public String getWebp() {
            return webp;
        }

        public void setWebp(String webp) {
            this.webp = webp;
        }
    }

    public class FixedHeight {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("height")
        @Expose
        private String height;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }
    }

    public class FixedWidth {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("width")
        @Expose
        private String width;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }
    }
}