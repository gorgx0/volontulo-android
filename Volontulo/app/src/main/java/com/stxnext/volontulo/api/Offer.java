package com.stxnext.volontulo.api;

import android.net.Uri;
import android.text.TextUtils;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.stxnext.volontulo.utils.realm.ImageParcelConverter;
import com.stxnext.volontulo.utils.realm.UserParcelConverter;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.util.HashMap;
import java.util.Map;

import io.realm.OfferRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Representation of an offer entity in local environment.
 *
 * Object can be pass between activities or fragments.
 */
@Parcel(implementations = {OfferRealmProxy.class},
        value = Parcel.Serialization.BEAN,
        analyze = {Offer.class})
public class Offer extends RealmObject {

    public static final String OFFER_ID = "OFFER-ID";
    public static final String OFFER_OBJECT = "OFFER-OBJECT";
    public static final String IMAGE_PATH = "IMAGE-PATH";
    public static final String IMAGE_RESOURCE = "IMAGE-RESOURCE";

    public static final String FIELD_ID = "id";
    public static final String FIELD_VOLUNTEERS = "volunteers";
    public static final String FIELD_OFFER_STATUS = "offerStatus";
    public static final String FIELD_ORGANIZATION = "organization";
    public static final String OFFER_STATUS_PUBLISHED = "published";

    private String url;
    @PrimaryKey
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
    private double locationLongitude;
    private double locationLatitude;

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
     * Returns organization which is owner of offer.
     *
     * @return
     * The organization
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     * Sets organization which is owner of offer.
     *
     * @param organization
     * The organization
     */
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    /**
     * Returns list of @see User associated with an offer.
     *
     * @return
     * The volunteers
     */
    public RealmList<User> getVolunteers() {
        return volunteers;
    }

    /**
     * Sets list of @see User associated with an offer.
     *
     * @param volunteers
     * The volunteers
     */
    @ParcelPropertyConverter(UserParcelConverter.class)
    public void setVolunteers(RealmList<User> volunteers) {
        this.volunteers = volunteers;
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

    /**
     * Returns requirements.
     *
     * @return
     * The requirements
     */
    public String getRequirements() {
        return requirements;
    }

    /**
     * Sets requirements.
     *
     * @param requirements
     * The requirements
     */
    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    /**
     * Returns time commitment.
     *
     * @return
     * The timeCommitment
     */
    public String getTimeCommitment() {
        return timeCommitment;
    }

    /**
     * Sets time commitment.
     *
     * @param timeCommitment
     * The time_commitment
     */
    public void setTimeCommitment(String timeCommitment) {
        this.timeCommitment = timeCommitment;
    }

    /**
     * Returns benefits.
     *
     * @return
     * The benefits
     */
    public String getBenefits() {
        return benefits;
    }

    /**
     * Sets benefits.
     *
     * @param benefits
     * The benefits
     */
    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    /**
     * Returns location.
     *
     * @return
     * The location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location
     * The location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns title.
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns started at.
     *
     * @return
     * The startedAt
     */
    public String getStartedAt() {
        return startedAt;
    }

    /**
     * Sets started at.
     *
     * @param startedAt
     * The started_at
     */
    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    /**
     * Returns finished at.
     *
     * @return
     * The finishedAt
     */
    public String getFinishedAt() {
        return finishedAt;
    }

    /**
     * Sets finished at.
     *
     * @param finishedAt
     * The finished_at
     */
    public void setFinishedAt(String finishedAt) {
        this.finishedAt = finishedAt;
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
     * It should be set to {@code "published"} to be available for users.
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
     * It should be set to {@code "published"} to be available for users.
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

    /**
     * Returns is votes.
     *
     * @return
     * The votes
     */
    public boolean isVotes() {
        return votes;
    }

    /**
     * Sets votes.
     *
     * @param votes
     * The votes
     */
    public void setVotes(boolean votes) {
        this.votes = votes;
    }

    /**
     * Returns recruitment start date.
     *
     * @return
     * The recruitmentStartDate
     */
    public String getRecruitmentStartDate() {
        return recruitmentStartDate;
    }

    /**
     * Sets recruitment start date.
     *
     * @param recruitmentStartDate
     * The recruitment_start_date
     */
    public void setRecruitmentStartDate(String recruitmentStartDate) {
        this.recruitmentStartDate = recruitmentStartDate;
    }

    /**
     * Returns recruitment end date.
     *
     * @return
     * The recruitmentEndDate
     */
    public String getRecruitmentEndDate() {
        return recruitmentEndDate;
    }

    /**
     * Sets recruitment end date.
     *
     * @param recruitmentEndDate
     * The recruitment_end_date
     */
    public void setRecruitmentEndDate(String recruitmentEndDate) {
        this.recruitmentEndDate = recruitmentEndDate;
    }

    /**
     * Returns reserve recruitment.
     *
     * @return
     * The reserveRecruitment
     */
    public boolean isReserveRecruitment() {
        return reserveRecruitment;
    }

    /**
     * Sets reserve recruitment.
     *
     * @param reserveRecruitment
     * The reserve_recruitment
     */
    public void setReserveRecruitment(boolean reserveRecruitment) {
        this.reserveRecruitment = reserveRecruitment;
    }

    /**
     * Returns reserve recruitment start date.
     *
     * @return
     * The reserveRecruitmentStartDate
     */
    public String getReserveRecruitmentStartDate() {
        return reserveRecruitmentStartDate;
    }

    /**
     * Sets reserve recruitment start date.
     *
     * @param reserveRecruitmentStartDate
     * The reserve_recruitment_start_date
     */
    public void setReserveRecruitmentStartDate(String reserveRecruitmentStartDate) {
        this.reserveRecruitmentStartDate = reserveRecruitmentStartDate;
    }

    /**
     * Returns reserve recruitment end date.
     *
     * @return
     * The reserveRecruitmentEndDate
     */
    public String getReserveRecruitmentEndDate() {
        return reserveRecruitmentEndDate;
    }

    /**
     * Sets reserve recruitment end date.
     *
     * @param reserveRecruitmentEndDate
     * The reserve_recruitment_end_date
     */
    public void setReserveRecruitmentEndDate(String reserveRecruitmentEndDate) {
        this.reserveRecruitmentEndDate = reserveRecruitmentEndDate;
    }

    /**
     * Returns action on going.
     *
     * @return
     * The actionOngoing
     */
    public boolean isActionOngoing() {
        return actionOngoing;
    }

    /**
     * Sets action on going.
     *
     * @param actionOngoing
     * The action_ongoing
     */
    public void setActionOngoing(boolean actionOngoing) {
        this.actionOngoing = actionOngoing;
    }

    /**
     * Returns constant coop.
     *
     * @return
     * The constantCoop
     */
    public boolean isConstantCoop() {
        return constantCoop;
    }

    /**
     * Sets constant coop.
     *
     * @param constantCoop
     * The constant_coop
     */
    public void setConstantCoop(boolean constantCoop) {
        this.constantCoop = constantCoop;
    }

    /**
     * Returns action start date.
     *
     * @return
     * The actionStartDate
     */
    public String getActionStartDate() {
        return actionStartDate;
    }

    /**
     * Sets action start date.
     *
     * @param actionStartDate
     * The action_start_date
     */
    public void setActionStartDate(String actionStartDate) {
        this.actionStartDate = actionStartDate;
    }

    /**
     * Returns action end date.
     *
     * @return
     * The actionEndDate
     */
    public String getActionEndDate() {
        return actionEndDate;
    }

    /**
     * Sets action end date.
     *
     * @param actionEndDate
     * The action_end_date
     */
    public void setActionEndDate(String actionEndDate) {
        this.actionEndDate = actionEndDate;
    }

    /**
     * Returns volunteers limit.
     *
     * @return
     * The volunteersLimit
     */
    public int getVolunteersLimit() {
        return volunteersLimit;
    }

    /**
     * Sets volunteers limit.
     *
     * @param volunteersLimit
     * The volunteers_limit
     */
    public void setVolunteersLimit(int volunteersLimit) {
        this.volunteersLimit = volunteersLimit;
    }

    /**
     * Returns weight.
     *
     * @return
     * The weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Sets weight.
     *
     * @param weight
     * The weight
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Returns list of images.
     *
     * @return
     * The images
     */
    public RealmList<Image> getImages() {
        return images;
    }

    /**
     * Sets list of images.
     *
     * @param images
     * The images
     */
    @ParcelPropertyConverter(ImageParcelConverter.class)
    public void setImages(RealmList<Image> images) {
        this.images = images;
    }

    /**
     * Returns longitude.
     *
     * @return
     */
    public double getLocationLongitude() {
        return locationLongitude;
    }

    /**
     * Sets longitude.
     *
     * @param locationLongitude
     */
    public void setLocationLongitude(double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    /**
     * Returns latitude.
     *
     * @return
     */
    public double getLocationLatitude() {
        return locationLatitude;
    }

    /**
     * Sets latitude.
     *
     * @param locationLatitude
     */
    public void setLocationLatitude(double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public String toString() {
        return "Offer " + id + ": '" + title + "' (" + location + ")";
    }

    /**
     * Prepare range duration of an offer.
     *
     * Use {@code now} when {code startedAt} is null. Use {@code finishedAt} is null.
     *
     * @param now
     * @param toSet
     * @return
     */
    public String getDuration(String now, String toSet) {
        StringBuilder sb = new StringBuilder();
        sb.append(TextUtils.isEmpty(startedAt) ? now : startedAt);
        sb.append(" - ");
        sb.append(TextUtils.isEmpty(finishedAt) ? toSet : finishedAt);
        return sb.toString();
    }

    /**
     * Returns is user joined to an offer.
     *
     * @param userId
     * @return
     */
    public boolean isUserJoined(int userId) {
        for (User user : volunteers) {
            if (user.getId() == userId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns if user is able to join to a offer.
     *
     * @param profile
     * @return
     */
    public boolean canBeJoined(UserProfile profile) {
        int organizationId = organization.getId();
        for (Organization organization : profile.getOrganizations()) {
            if (organization.getId() == organizationId) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns is there any image in an offer.
     *
     * @return
     */
    public boolean hasImage() {
        return images != null && images.size() > 0;
    }

    /**
     * Returns an image path of an offer.
     *
     * @return
     */
    public String retrieveImagePath() {
        return hasImage() ? images.get(0).getPath() : null;
    }

    /**
     * Returns map of selected fields for create or update
     *
     * Contains list of all required params.
     *
     * @return
     */
    public Map<String, String> getParams() {
        Map<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("time_commitment", timeCommitment);
        map.put("location", location);
        map.put("description", description);
        map.put("benefits", benefits);
        map.put("requirements", requirements);
        if (organization != null) {
            map.put("organization", String.valueOf(organization.getId()));
        }
        return map;
    }

    /**
     * Returns is offer allowed to edit
     *
     * @param profile
     * @return
     */
    public boolean canBeEdit(UserProfile profile) {
        final RealmList<Organization> organizations = profile.getOrganizations();
        return !organizations.isEmpty() && organizations.first().getId() == organization.getId();
    }

    /**
     * Returns is offer allowed to edit
     *
     * @param organizationId
     * @return
     */
    public boolean belongTo(int organizationId) {
        return organizationId == organization.getId();
    }

    /**
     * Set location, longitude and latitude from {@code Place}
     *
     * @param place
     */
    public void setLocationNameAndPosition(Place place) {
        final LatLng position = place.getLatLng();
        locationLongitude = position.longitude;
        locationLatitude = position.latitude;
        location = String.valueOf(place.getName());
    }

    /**
     * Set image path of an offer
     *
     * @param selectedImage
     */
    public void applyImagePath(Uri selectedImage) {
        if (selectedImage == null) {
            return;
        }
        Image image = hasImage() ? images.first() : new Image();
        image.setPath(selectedImage.getPath());
        images.add(0, image);
    }
}