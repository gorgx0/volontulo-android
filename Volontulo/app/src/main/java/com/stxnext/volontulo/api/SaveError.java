package com.stxnext.volontulo.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an error of processing create or update an @see Offer
 */
public class SaveError {

    private String detail;
    private List<String> timeCommitment = new ArrayList<>();
    private List<String> title = new ArrayList<>();
    private List<String> location = new ArrayList<>();
    private List<String> description = new ArrayList<>();
    private List<String> organization = new ArrayList<>();
    private List<String> benefits = new ArrayList<>();

    /**
     * Returns error message.
     *
     * @return
     * The detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Sets error message.
     *
     * @param detail
     * The detail
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * Returns list of errors connected with time commitment.
     *
     * It's used when there is no time commitment filled in request.
     *
     * @return
     * The timeCommitment
     */
    public List<String> getTimeCommitment() {
        return timeCommitment;
    }

    /**
     * Sets list of errors connected with time commitment.
     *
     * It's used when there is no time_commitment filled in request.
     *
     * @param timeCommitment
     * The time_commitment
     */
    public void setTimeCommitment(List<String> timeCommitment) {
        this.timeCommitment = timeCommitment;
    }

    /**
     * Returns list of errors connected with title.
     *
     * It's used when there is no title filled in request.
     *
     * @return
     * The title
     */
    public List<String> getTitle() {
        return title;
    }

    /**
     * Sets list of errors connected with title.
     *
     * It's used when there is no title filled in request.
     *
     * @param title
     * The title
     */
    public void setTitle(List<String> title) {
        this.title = title;
    }

    /**
     * Returns list of errors connected with location.
     *
     * It's used when there is no location filled in request.
     *
     * @return
     * The location
     */
    public List<String> getLocation() {
        return location;
    }

    /**
     * Sets list of errors connected with location.
     *
     * It's used when there is no location filled in request.
     *
     * @param location
     * The location
     */
    public void setLocation(List<String> location) {
        this.location = location;
    }

    /**
     * Returns list of errors connected with description.
     *
     * It's used when there is no description filled in request.
     *
     * @return
     * The description
     */
    public List<String> getDescription() {
        return description;
    }

    /**
     * Sets list of errors connected with description.
     *
     * It's used when there is no description filled in request.
     *
     * @param description
     * The description
     */
    public void setDescription(List<String> description) {
        this.description = description;
    }

    /**
     * Returns list of errors connected with organization.
     *
     * It's used when there is no organization filled in request.
     *
     * @return
     * The organization
     */
    public List<String> getOrganization() {
        return organization;
    }

    /**
     * Sets list of errors connected with organization.
     *
     * It's used when there is no organization filled in request.
     *
     * @param organization
     * The organization
     */
    public void setOrganization(List<String> organization) {
        this.organization = organization;
    }

    /**
     * Returns list of errors connected with benefits.
     *
     * It's used when there is no benefits filled in request.
     *
     * @return
     * The benefits
     */
    public List<String> getBenefits() {
        return benefits;
    }

    /**
     * Sets list of errors connected with benefits.
     *
     * It's used when there is no benefits filled in request.
     *
     * @param benefits
     * The benefits
     */
    public void setBenefits(List<String> benefits) {
        this.benefits = benefits;
    }

}