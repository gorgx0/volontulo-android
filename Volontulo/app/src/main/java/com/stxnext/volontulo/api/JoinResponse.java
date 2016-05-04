package com.stxnext.volontulo.api;

import java.util.ArrayList;
import java.util.List;

public class JoinResponse {

    private String info;
    private List<String> email = new ArrayList<>();
    private List<String> phoneNo = new ArrayList<>();
    private List<String> fullname = new ArrayList<>();
    private String detail;

    /**
     *
     * @return
     * The info
     */
    public String getInfo() {
        return info;
    }

    /**
     *
     * @param info
     * The info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     *
     * @return
     * The email
     */
    public List<String> getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(List<String> email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The phoneNo
     */
    public List<String> getPhoneNo() {
        return phoneNo;
    }

    /**
     *
     * @param phoneNo
     * The phone_no
     */
    public void setPhoneNo(List<String> phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     *
     * @return
     * The fullname
     */
    public List<String> getFullname() {
        return fullname;
    }

    /**
     *
     * @param fullname
     * The fullname
     */
    public void setFullname(List<String> fullname) {
        this.fullname = fullname;
    }

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

}