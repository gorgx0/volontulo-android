package com.stxnext.volontulo.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for interpreting login response
 */
public class LoginResponse {

    private String key;
    private List<String> nonFieldErrors = new ArrayList<>();
    private List<String> password = new ArrayList<>();

    /**
     * Returns authorization key.
     *
     * It's filled when login operation goes right.
     *
     * @return
     * The key
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets authorization key.
     *
     * It's filled when login operation goes right.
     *
     * @param key
     * The key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Returns list of general errors.
     *
     * It's filled when something goes wrong.
     *
     * @return
     * The nonFieldErrors
     */
    public List<String> getNonFieldErrors() {
        return nonFieldErrors;
    }

    /**
     * Sets list of general errors.
     *
     * It's filled when something goes wrong.
     *
     * @param nonFieldErrors
     * The non_field_errors
     */
    public void setNonFieldErrors(List<String> nonFieldErrors) {
        this.nonFieldErrors = nonFieldErrors;
    }

    /**
     * Returns list of errors connected with password
     *
     * It's used when there is no password filled in request.
     *
     * @return
     * The password
     */
    public List<String> getPassword() {
        return password;
    }

    /**
     * Sets list of errors connected with password
     *
     * It's used when there is no password filled in request.
     *
     * @param password
     * The password
     */
    public void setPassword(List<String> password) {
        this.password = password;
    }

}