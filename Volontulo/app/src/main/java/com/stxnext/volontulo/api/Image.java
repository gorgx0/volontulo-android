package com.stxnext.volontulo.api;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import io.realm.ImageRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Representation of an image entity in local environment.
 *
 * Object can be pass between activities or fragments.
 */
@Parcel(implementations = {ImageRealmProxy.class},
        value = Parcel.Serialization.BEAN,
        analyze = {Image.class})
public class Image extends RealmObject {

    @PrimaryKey
    private int id;
    private String image;
    private String path;
    @SerializedName("is_avatar")
    private boolean isAvatar;
    @SerializedName("is_main")
    private boolean isMain;

    /**
     * Returns identifier key
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets identifier key
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns url for image.
     *
     * Used for @see UserProfile
     *
     * @return image
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets url for image.
     *
     * Used for @see UserProfile
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Returns is avatar
     *
     * Used for @see UserProfile
     *
     * @return
     * The isAvatar
     */
    public boolean getIsAvatar() {
        return isAvatar;
    }

    /**
     * Sets is avatar
     *
     * Used for @see UserProfile
     *
     * @param isAvatar
     * The is_avatar
     */
    public void setIsAvatar(boolean isAvatar) {
        this.isAvatar = isAvatar;
    }

    /**
     * Returns address for image
     *
     * Used for @see Offer
     *
     * @return
     * The path
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets address for image
     *
     * Used for @see Offer
     *
     * @param path
     * The path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Returns is main
     *
     * Used for @see Offer
     *
     * @return
     * The isMain
     */
    public boolean isIsMain() {
        return isMain;
    }

    /**
     * Sets is main
     *
     * Used for @see Offer
     *
     * @param isMain
     * The is_main
     */
    public void setIsMain(boolean isMain) {
        this.isMain = isMain;
    }

}