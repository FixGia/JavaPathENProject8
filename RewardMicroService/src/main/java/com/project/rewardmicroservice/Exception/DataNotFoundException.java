package com.project.rewardmicroservice.Exception;


/**
 * Data Not Found Exception Class
 *
 * @author Etternell
 *
 */
public class DataNotFoundException extends RuntimeException {


    /**
     * Data Not Found Exception Method
     *
     * @param message the message;
     *
     */
    public DataNotFoundException(final String message) {
        super(message);
    }

}