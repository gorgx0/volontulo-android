package com.stxnext.volontulo.api;

import java.util.ArrayList;
import java.util.List;

public class LoginResponse {

    private String key;
    private List<String> nonFieldErrors = new ArrayList<>();
    private List<String> password = new ArrayList<>();

    /**
     *
     * @return
     * The key
     */
    public String getKey() {
        return key;
    }

    /**
     *
     * @param key
     * The key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     *
     * @return
     * The nonFieldErrors
     */
    public List<String> getNonFieldErrors() {
        return nonFieldErrors;
    }

    /**
     *
     * @param nonFieldErrors
     * The non_field_errors
     */
    public void setNonFieldErrors(List<String> nonFieldErrors) {
        this.nonFieldErrors = nonFieldErrors;
    }

    /**
     *
     * @return
     * The password
     */
    public List<String> getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * The password
     */
    public void setPassword(List<String> password) {
        this.password = password;
    }

}