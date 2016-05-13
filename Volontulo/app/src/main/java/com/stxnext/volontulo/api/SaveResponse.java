package com.stxnext.volontulo.api;

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
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The organization
     */
    public Integer getOrganization() {
        return organization;
    }

    /**
     *
     * @param organization
     * The organization
     */
    public void setOrganization(Integer organization) {
        this.organization = organization;
    }

    /**
     *
     * @return
     * The requirements
     */
    public String getRequirements() {
        return requirements;
    }

    /**
     *
     * @param requirements
     * The requirements
     */
    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    /**
     *
     * @return
     * The timePeriod
     */
    public String getTimePeriod() {
        return timePeriod;
    }

    /**
     *
     * @param timePeriod
     * The time_period
     */
    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    /**
     *
     * @return
     * The statusOld
     */
    public String getStatusOld() {
        return statusOld;
    }

    /**
     *
     * @param statusOld
     * The status_old
     */
    public void setStatusOld(String statusOld) {
        this.statusOld = statusOld;
    }

    /**
     *
     * @return
     * The offerStatus
     */
    public String getOfferStatus() {
        return offerStatus;
    }

    /**
     *
     * @param offerStatus
     * The offer_status
     */
    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }

    /**
     *
     * @return
     * The recruitmentStatus
     */
    public String getRecruitmentStatus() {
        return recruitmentStatus;
    }

    /**
     *
     * @param recruitmentStatus
     * The recruitment_status
     */
    public void setRecruitmentStatus(String recruitmentStatus) {
        this.recruitmentStatus = recruitmentStatus;
    }

    /**
     *
     * @return
     * The actionStatus
     */
    public String getActionStatus() {
        return actionStatus;
    }

    /**
     *
     * @param actionStatus
     * The action_status
     */
    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }

}