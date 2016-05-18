package com.stxnext.volontulo.api;

import org.parceler.Parcel;

import io.realm.OrganizationRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Representation of an organization entity in local environment.
 *
 * Object can be pass between activities or fragments.
 */
@Parcel(implementations = {OrganizationRealmProxy.class},
        value = Parcel.Serialization.BEAN,
        analyze = {Organization.class})
public class Organization extends RealmObject {

    private String url;
    @PrimaryKey
    private int id;
    private String name;
    private String address;
    private String description;

    /**
     * Returns url of a REST request for an organization data.
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url of a REST request for an organization data.
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
     * Returns name.
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns address.
     *
     * @return
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns description.
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

}