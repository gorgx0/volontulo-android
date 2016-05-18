package com.stxnext.volontulo.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for interpreting response of join to offer (action) operation
 */
public class JoinResponse {

    private String info;
    private List<String> email = new ArrayList<>();
    private List<String> phoneNo = new ArrayList<>();
    private List<String> fullname = new ArrayList<>();
    private String detail;

    /**
     * Returns info about result of operation when all parameters are valid.
     *
     * @return
     * The info
     */
    public String getInfo() {
        return info;
    }

    /**
     * Sets info about result of operation when all parameters are valid.
     *
     * @param info
     * The info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Returns list of errors connected with email.
     *
     * It's used when there is no email filled in request.
     *
     * @return
     * The email
     */
    public List<String> getEmail() {
        return email;
    }

    /**
     * Sets list of errors connected with email.
     *
     * It's used when there is no email filled in request.
     *
     * @param email
     * The email
     */
    public void setEmail(List<String> email) {
        this.email = email;
    }

    /**
     * Returns list of errors connected with phone number.
     *
     * It's used when there is no phoneNumber filled in request.
     *
     * @return
     * The phoneNo
     */
    public List<String> getPhoneNo() {
        return phoneNo;
    }

    /**
     * Sets list of errors connected with phone number.
     *
     * It's used when there is no phoneNumber filled in request.
     *
     * @param phoneNo
     * The phone_no
     */
    public void setPhoneNo(List<String> phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * Returns list of errors connected with full name.
     *
     * It's used when there is no fullname filled in request.
     *
     * @return
     * The fullname
     */
    public List<String> getFullname() {
        return fullname;
    }

    /**
     * Sets list of errors connected with full name.
     *
     * It's used when there is no fullname filled in request.
     *
     * @param fullname
     * The fullname
     */
    public void setFullname(List<String> fullname) {
        this.fullname = fullname;
    }

    /**
     * Returns potential problems appeared during processing request.
     *
     * For example: authorization data problem
     *
     * @return
     * The detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Sets potential problems appeared during processing request.
     *
     * For example: authorization data problem
     *
     * @param detail
     * The detail
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

}