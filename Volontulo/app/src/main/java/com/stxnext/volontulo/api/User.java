package com.stxnext.volontulo.api;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.UserRealmProxy;
import io.realm.annotations.PrimaryKey;

/**
 * Representation of a user entity in local environment.
 *
 * Object can be pass between activities or fragments.
 */
@Parcel(implementations = {UserRealmProxy.class},
        value = Parcel.Serialization.BEAN,
        analyze = {User.class})
public class User extends RealmObject {

    public static final String USER_ID = "USER-ID";
    public static final String FIELD_ID = "id";
    public static final String FIELD_EMAIL = "email";

    @PrimaryKey
    private int id;
    private String username;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private String email;

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
     * Returns user name.
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets user name.
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns first name.
     *
     * @return
     * The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName
     * The first_name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Return last name.
     *
     * @return
     * The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName
     * The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Returns email.
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Create empty user
     *
     * @return
     */
    public static User createEmpty() {
        final User user = new User();
        user.setUsername("");
        user.setEmail("");
        user.setFirstName("");
        user.setLastName("");
        return user;
    }

}