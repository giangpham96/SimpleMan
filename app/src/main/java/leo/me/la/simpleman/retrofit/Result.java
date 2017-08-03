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

        public Original getOriginal() {
            return original;
        }

        public void setOriginal(Original original) {
            this.original = original;
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
        @SerializedName("size")
        @Expose
        private String size;
        @SerializedName("frames")
        @Expose
        private String frames;
        @SerializedName("mp4")
        @Expose
        private String mp4;
        @SerializedName("mp4_size")
        @Expose
        private String mp4Size;
        @SerializedName("webp")
        @Expose
        private String webp;
        @SerializedName("webp_size")
        @Expose
        private String webpSize;

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

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getFrames() {
            return frames;
        }

        public void setFrames(String frames) {
            this.frames = frames;
        }

        public String getMp4() {
            return mp4;
        }

        public void setMp4(String mp4) {
            this.mp4 = mp4;
        }

        public String getMp4Size() {
            return mp4Size;
        }

        public void setMp4Size(String mp4Size) {
            this.mp4Size = mp4Size;
        }

        public String getWebp() {
            return webp;
        }

        public void setWebp(String webp) {
            this.webp = webp;
        }

        public String getWebpSize() {
            return webpSize;
        }

        public void setWebpSize(String webpSize) {
            this.webpSize = webpSize;
        }

    }
}