package com.stxnext.volontulo.api;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Offer extends RealmObject {

    public static String OFFER_ID = "OFFER-ID";
    public static String IMAGE_PATH = "IMAGE-PATH";
    public static String IMAGE_RESOURCE = "IMAGE-RESOURCE";

    private String url;
    private int id;
    private Organization organization;
    private RealmList<User> volunteers;
    private String description;
    private String requirements;
    @SerializedName("time_commitment")
    private String timeCommitment;
    private String benefits;
    private String location;
    private String title;
    @SerializedName("started_at")
    private String startedAt;
    @SerializedName("finished_at")
    private String finishedAt;
    @SerializedName("time_period")
    private String timePeriod;
    @SerializedName("status_old")
    private String statusOld;
    @SerializedName("offer_status")
    private String offerStatus;
    @SerializedName("recruitment_status")
    private String recruitmentStatus;
    @SerializedName("action_status")
    private String actionStatus;
    private boolean votes;
    @SerializedName("recruitment_start_date")
    private String recruitmentStartDate;
    @SerializedName("recruitment_end_date")
    private String recruitmentEndDate;
    @SerializedName("reserve_recruitment")
    private boolean reserveRecruitment;
    @SerializedName("reserve_recruitment_start_date")
    private String reserveRecruitmentStartDate;
    @SerializedName("reserve_recruitment_end_date")
    private String reserveRecruitmentEndDate;
    @SerializedName("action_ongoing")
    private boolean actionOngoing;
    @SerializedName("constant_coop")
    private boolean constantCoop;
    @SerializedName("action_start_date")
    private String actionStartDate;
    @SerializedName("action_end_date")
    private String actionEndDate;
    @SerializedName("volunteers_limit")
    private int volunteersLimit;
    private int weight;
    private RealmList<Image> images;

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
     * The organization
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     *
     * @param organization
     * The organization
     */
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    /**
     *
     * @return
     * The volunteers
     */
    public RealmList<User> getVolunteers() {
        return volunteers;
    }

    /**
     *
     * @param volunteers
     * The volunteers
     */
    public void setVolunteers(RealmList<User> volunteers) {
        this.volunteers = volunteers;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
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
     * The timeCommitment
     */
    public String getTimeCommitment() {
        return timeCommitment;
    }

    /**
     *
     * @param timeCommitment
     * The time_commitment
     */
    public void setTimeCommitment(String timeCommitment) {
        this.timeCommitment = timeCommitment;
    }

    /**
     *
     * @return
     * The benefits
     */
    public String getBenefits() {
        return benefits;
    }

    /**
     *
     * @param benefits
     * The benefits
     */
    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    /**
     *
     * @return
     * The location
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     * The location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The startedAt
     */
    public String getStartedAt() {
        return startedAt;
    }

    /**
     *
     * @param startedAt
     * The started_at
     */
    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    /**
     *
     * @return
     * The finishedAt
     */
    public String getFinishedAt() {
        return finishedAt;
    }

    /**
     *
     * @param finishedAt
     * The finished_at
     */
    public void setFinishedAt(String finishedAt) {
        this.finishedAt = finishedAt;
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

    /**
     *
     * @return
     * The votes
     */
    public boolean isVotes() {
        return votes;
    }

    /**
     *
     * @param votes
     * The votes
     */
    public void setVotes(boolean votes) {
        this.votes = votes;
    }

    /**
     *
     * @return
     * The recruitmentStartDate
     */
    public String getRecruitmentStartDate() {
        return recruitmentStartDate;
    }

    /**
     *
     * @param recruitmentStartDate
     * The recruitment_start_date
     */
    public void setRecruitmentStartDate(String recruitmentStartDate) {
        this.recruitmentStartDate = recruitmentStartDate;
    }

    /**
     *
     * @return
     * The recruitmentEndDate
     */
    public String getRecruitmentEndDate() {
        return recruitmentEndDate;
    }

    /**
     *
     * @param recruitmentEndDate
     * The recruitment_end_date
     */
    public void setRecruitmentEndDate(String recruitmentEndDate) {
        this.recruitmentEndDate = recruitmentEndDate;
    }

    /**
     *
     * @return
     * The reserveRecruitment
     */
    public boolean isReserveRecruitment() {
        return reserveRecruitment;
    }

    /**
     *
     * @param reserveRecruitment
     * The reserve_recruitment
     */
    public void setReserveRecruitment(boolean reserveRecruitment) {
        this.reserveRecruitment = reserveRecruitment;
    }

    /**
     *
     * @return
     * The reserveRecruitmentStartDate
     */
    public String getReserveRecruitmentStartDate() {
        return reserveRecruitmentStartDate;
    }

    /**
     *
     * @param reserveRecruitmentStartDate
     * The reserve_recruitment_start_date
     */
    public void setReserveRecruitmentStartDate(String reserveRecruitmentStartDate) {
        this.reserveRecruitmentStartDate = reserveRecruitmentStartDate;
    }

    /**
     *
     * @return
     * The reserveRecruitmentEndDate
     */
    public String getReserveRecruitmentEndDate() {
        return reserveRecruitmentEndDate;
    }

    /**
     *
     * @param reserveRecruitmentEndDate
     * The reserve_recruitment_end_date
     */
    public void setReserveRecruitmentEndDate(String reserveRecruitmentEndDate) {
        this.reserveRecruitmentEndDate = reserveRecruitmentEndDate;
    }

    /**
     *
     * @return
     * The actionOngoing
     */
    public boolean isActionOngoing() {
        return actionOngoing;
    }

    /**
     *
     * @param actionOngoing
     * The action_ongoing
     */
    public void setActionOngoing(boolean actionOngoing) {
        this.actionOngoing = actionOngoing;
    }

    /**
     *
     * @return
     * The constantCoop
     */
    public boolean isConstantCoop() {
        return constantCoop;
    }

    /**
     *
     * @param constantCoop
     * The constant_coop
     */
    public void setConstantCoop(boolean constantCoop) {
        this.constantCoop = constantCoop;
    }

    /**
     *
     * @return
     * The actionStartDate
     */
    public String getActionStartDate() {
        return actionStartDate;
    }

    /**
     *
     * @param actionStartDate
     * The action_start_date
     */
    public void setActionStartDate(String actionStartDate) {
        this.actionStartDate = actionStartDate;
    }

    /**
     *
     * @return
     * The actionEndDate
     */
    public String getActionEndDate() {
        return actionEndDate;
    }

    /**
     *
     * @param actionEndDate
     * The action_end_date
     */
    public void setActionEndDate(String actionEndDate) {
        this.actionEndDate = actionEndDate;
    }

    /**
     *
     * @return
     * The volunteersLimit
     */
    public int getVolunteersLimit() {
        return volunteersLimit;
    }

    /**
     *
     * @param volunteersLimit
     * The volunteers_limit
     */
    public void setVolunteersLimit(int volunteersLimit) {
        this.volunteersLimit = volunteersLimit;
    }

    /**
     *
     * @return
     * The weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     *
     * @param weight
     * The weight
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     *
     * @return
     * The images
     */
    public RealmList<Image> getImages() {
        return images;
    }

    /**
     *
     * @param images
     * The images
     */
    public void setImages(RealmList<Image> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Ofer " + id + ": '" + title + "' (" + location + ")";
    }

    public String getDuration(String now, String toSet) {
        StringBuilder sb = new StringBuilder();
        sb.append(TextUtils.isEmpty(startedAt) ? now : startedAt);
        sb.append(" - ");
        sb.append(TextUtils.isEmpty(finishedAt) ? toSet : finishedAt);
        return sb.toString();
    }

    public boolean isUserJoined() {
        return false;
    }

    public boolean hasImage() {
        return images != null && images.size() > 0;
    }

    public String getImagePath() {
        return images.get(0).getPath();
    }
}