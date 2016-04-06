package com.stxnext.volontulo.api;

import com.google.gson.annotations.SerializedName;

public class Image {

    private int id;
    private String image;
    @SerializedName("is_avatar")
    private boolean isAvatar;

    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The isAvatar
     */
    public boolean getIsAvatar() {
        return isAvatar;
    }

    /**
     *
     * @param isAvatar
     * The is_avatar
     */
    public void setIsAvatar(boolean isAvatar) {
        this.isAvatar = isAvatar;
    }

}