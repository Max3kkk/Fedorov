package com.example.gg;

import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("description")
    private String description;

    @SerializedName("gifURL")
    private String gifURL;

    @SerializedName("previewURL")
    private String previewURL;

    public String getDescription() {
        return description;
    }

    public String getGifURL() {
        return gifURL;
    }

    public String getPreviewURL() {
        return previewURL;
    }
}
