package com.stxnext.volontulo.api;

/**
 * Class for interpreting response of create or update an @see Offer
 */
public class SaveResponse {

    private String url;
    private Integer id;
    private Integer organization;
    private String requirements;
    private String timePeriod;
    private String statusOld;
    private String offerStatus;
    private String recruitmentStatus;
    private String actionStatus;

    /**
     * Returns url of a REST request for an offer data.
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url of a REST request for an offer data.
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Returns id in database.
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id in database.
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns id of an organization which an offer belongs to.
     *
     * @return
     * The organization
     */
    public Integer getOrganization() {
        return organization;
    }

    /**
     * Sets id of an organization which an offer belongs to.
     *
     * @param organization
     * The organization
     */
    public void setOrganization(Integer organization) {
        this.organization = organization;
    }

    /**
     * Returns requirements for a volunteer.
     *
     * @return
     * The requirements
     */
    public String getRequirements() {
        return requirements;
    }

    /**
     * Sets requirements for a volunteer.
     *
     * @param requirements
     * The requirements
     */
    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    /**
     * Returns time period.
     *
     * @return
     * The timePeriod
     */
    public String getTimePeriod() {
        return timePeriod;
    }

    /**
     * Sets time period.
     *
     * @param timePeriod
     * The time_period
     */
    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    /**
     * Returns status old.
     *
     * @return
     * The statusOld
     */
    public String getStatusOld() {
        return statusOld;
    }

    /**
     * Sets status old.
     *
     * @param statusOld
     * The status_old
     */
    public void setStatusOld(String statusOld) {
        this.statusOld = statusOld;
    }

    /**
     * Returns offer status.
     *
     * @return
     * The offerStatus
     */
    public String getOfferStatus() {
        return offerStatus;
    }

    /**
     * Sets offer status.
     *
     * @param offerStatus
     * The offer_status
     */
    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }

    /**
     * Returns recruitment status.
     *
     * @return
     * The recruitmentStatus
     */
    public String getRecruitmentStatus() {
        return recruitmentStatus;
    }

    /**
     * Sets recruitment status.
     *
     * @param recruitmentStatus
     * The recruitment_status
     */
    public void setRecruitmentStatus(String recruitmentStatus) {
        this.recruitmentStatus = recruitmentStatus;
    }

    /**
     * Returns action status.
     *
     * @return
     * The actionStatus
     */
    public String getActionStatus() {
        return actionStatus;
    }

    /**
     * Sets action status.
     *
     * @param actionStatus
     * The action_status
     */
    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }

}