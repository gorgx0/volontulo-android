package com.stxnext.volontulo.api;

import java.util.ArrayList;
import java.util.List;

public class CreateError {

    private String detail;
    private List<String> timeCommitment = new ArrayList<>();
    private List<String> title = new ArrayList<>();
    private List<String> location = new ArrayList<>();
    private List<String> description = new ArrayList<>();
    private List<String> organization = new ArrayList<>();
    private List<String> benefits = new ArrayList<>();

    /**
     *
     * @return
     * The detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     *
     * @param detail
     * The detail
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     *
     * @return
     * The timeCommitment
     */
    public List<String> getTimeCommitment() {
        return timeCommitment;
    }

    /**
     *
     * @param timeCommitment
     * The time_commitment
     */
    public void setTimeCommitment(List<String> timeCommitment) {
        this.timeCommitment = timeCommitment;
    }

    /**
     *
     * @return
     * The title
     */
    public List<String> getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(List<String> title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The location
     */
    public List<String> getLocation() {
        return location;
    }

    /**
     *
     * @param location
     * The location
     */
    public void setLocation(List<String> location) {
        this.location = location;
    }

    /**
     *
     * @return
     * The description
     */
    public List<String> getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(List<String> description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The organization
     */
    public List<String> getOrganization() {
        return organization;
    }

    /**
     *
     * @param organization
     * The organization
     */
    public void setOrganization(List<String> organization) {
        this.organization = organization;
    }

    /**
     *
     * @return
     * The benefits
     */
    public List<String> getBenefits() {
        return benefits;
    }

    /**
     *
     * @param benefits
     * The benefits
     */
    public void setBenefits(List<String> benefits) {
        this.benefits = benefits;
    }

}