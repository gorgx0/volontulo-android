package com.stxnext.volontulo.api;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.stxnext.volontulo.utils.realm.ImageParcelConverter;
import com.stxnext.volontulo.utils.realm.OrganizationParcelConverter;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.UserProfileRealmProxy;
import io.realm.annotations.PrimaryKey;

/**
 * Representation of a user profile entity in local environment.
 *
 * Object can be pass between activities or fragments.
 */
@Parcel(implementations = {UserProfileRealmProxy.class},
        value = Parcel.Serialization.BEAN,
        analyze = {UserProfile.class})
public class UserProfile extends RealmObject {

    public static final String USER_PROFILE_ID = "USER-PROFILE-ID";
    public static final String USER_PROFILE_OBJECT = "USER-PROFILE-OBJECT";
    public static final String FIELD_ID= "id";
    public static final String FIELD_USER = "user";

    private String url;
    @PrimaryKey
    private int id;
    private User user;
    private RealmList<Organization> organizations;
    @SerializedName("is_administrator")
    private boolean isAdministrator;
    @SerializedName("phone_no")
    private String phoneNo;
    private RealmList<Image> images;

    /**
     * Returns url of a REST request for a user profile data.
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url of a REST request for a user profile data.
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Returns identifier key.
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets identifier key.
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns user.
     *
     * @return
     * The user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user
     * The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns list of organizations which user belongs to.
     *
     * @return
     * The organizations
     */
    public RealmList<Organization> getOrganizations() {
        return organizations;
    }

    /**
     * Sets list of organizations which user belongs to.
     *
     * @param organizations
     * The organizations
     */
    @ParcelPropertyConverter(OrganizationParcelConverter.class)
    public void setOrganizations(RealmList<Organization> organizations) {
        this.organizations = organizations;
    }

    /**
     * Returns is administrator.
     *
     * @return
     * The isAdministrator
     */
    public boolean getIsAdministrator() {
        return isAdministrator;
    }

    /**
     * Sets is administrator.
     *
     * @param isAdministrator
     * The is_administrator
     */
    public void setIsAdministrator(boolean isAdministrator) {
        this.isAdministrator = isAdministrator;
    }

    /**
     * Returns phone number.
     *
     * @return
     * The phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * Sets phone number.
     *
     * @param phoneNo
     * The phone_no
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * Returns list of organizations.
     *
     * @return
     * The images
     */
    public RealmList<Image> getImages() {
        return images;
    }

    /**
     * Sets list of organizations.
     *
     * @param images
     * The images
     */
    @ParcelPropertyConverter(ImageParcelConverter.class)
    public void setImages(RealmList<Image> images) {
        this.images = images;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public String toString() {
        return "User " + id + " [" + url + "]: " + phoneNo + (isAdministrator ? " ADMIN" : "");
    }

    /**
     * Returns embedded user's name.
     *
     * @return
     */
    public String retrieveName() {
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

    /**
     * Returns embedded user's email.
     *
     * @return
     */
    public String getEmail() {
        return user.getEmail();
    }

    /**
     * Returns is there any profile image .
     *
     * @return
     */
    public boolean hasImage() {
        return images.size() > 0;
    }

    /**
     * Returns a profile image.
     *
     * It's first element of list (with 0 index)
     *
     * @return
     */
    public String getImage() {
        String image = null;
        if (hasImage()) {
            image = images.get(0).getImage();
        }
        return image;
    }

    /**
     * Copy object
     *
     * @param profile
     * @return
     */
    public static UserProfile copyObject(final UserProfile profile) {
        if (profile != null) {
            final UserProfile newCopy = new UserProfile();
            newCopy.setId(profile.getId());
            newCopy.setIsAdministrator(profile.getIsAdministrator());
            newCopy.setImages(profile.getImages());
            newCopy.setUrl(profile.getUrl());
            newCopy.setUser(profile.getUser());
            newCopy.setOrganizations(profile.getOrganizations());
            newCopy.setPhoneNo(profile.getPhoneNo());
            return newCopy;
        }
        return UserProfile.createEmpty();
    }

    /**
     * Create empty UserProfile
     *
     * @return
     */
    public static UserProfile createEmpty() {
        final UserProfile profile = new UserProfile();
        profile.setUser(User.createEmpty());
        profile.setUrl("");
        profile.setOrganizations(new RealmList<Organization>());
        profile.setImages(new RealmList<Image>());
        profile.setPhoneNo("");
        return profile;
    }
}