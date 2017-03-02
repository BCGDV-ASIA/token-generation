package com.bcgdv.jwt.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Base token class.
 */
public abstract class BaseJwtToken implements Serializable {

    /**
     * token expires in n ms or -1 for no expiration
     */
    protected Long expiryInMilliSeconds;

    /**
     * date created
     */
    protected Long dateCreated = new Date().getTime();

    /**
     * get expiry time
     * @return
     */
    public Long getExpiryInMilliSeconds() {
        return expiryInMilliSeconds;
    }

    /**
     * set expiry time
     * @param expiryInMilliSeconds the expiry time
     */
    public void setExpiryInMilliSeconds(Long expiryInMilliSeconds) {
        this.expiryInMilliSeconds = expiryInMilliSeconds;
    }

    /**
     * get date created
     * @return date as long system time in ms
     */
    public Long getDateCreated() {
        return dateCreated;
    }

    /**
     * set the date created
     * @param dateCreated the system time in ms
     */
    public void setDateCreated(Long dateCreated) {
        this.dateCreated = dateCreated;
    }
}
