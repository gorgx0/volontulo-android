package com.stxnext.volontulo.api;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserProfile {

    private String url;
    private int id;
    private User user;
    private List<Organization> organizations;
    @SerializedName("is_administrator")
    private boolean isAdministrator;
    @SerializedName("phone_no")
    private String phoneNo;
    private List<Image> images;

    @Override
    public String toString() {
        return "User " + id + " [" + url + "]: " + phoneNo + (isAdministrator ? " ADMIN" : "");
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

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
     * The user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     * The organizations
     */
    public List<Organization> getOrganizations() {
        return organizations;
    }

    /**
     *
     * @param organizations
     * The organizations
     */
    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    /**
     *
     * @return
     * The isAdministrator
     */
    public boolean getIsAdministrator() {
        return isAdministrator;
    }

    /**
     *
     * @param isAdministrator
     * The is_administrator
     */
    public void setIsAdministrator(boolean isAdministrator) {
        this.isAdministrator = isAdministrator;
    }

    /**
     *
     * @return
     * The phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     *
     * @param phoneNo
     * The phone_no
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     *
     * @return
     * The images
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     *
     * @param images
     * The images
     */
    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String resolveName() {
        String name;
        boolean hasFirstName = !TextUtils.isEmpty(user.getFirstName());
        boolean hasLastName = !TextUtils.isEmpty(user.getLastName());
        if (hasFirstName || hasLastName) {
            StringBuilder sb = new StringBuilder();
            if (hasFirstName) {
                sb.append(user.getFirstName());
            }
            if (hasFirstName && hasLastName) {
                sb.append(" ");
            }
            if (hasLastName) {
                sb.append(user.getLastName());
            }
            name = sb.toString();
        } else {
            name = user.getUsername();
        }
        return name;
    }

    public String getEmail() {
        return user.getEmail();
    }

    public boolean hasImage() {
        return images.size() > 0;
    }

    public String getImage() {
        String image = null;
        if (hasImage()) {
            image = images.get(0).getImage();
        }
        return image;
    }
}