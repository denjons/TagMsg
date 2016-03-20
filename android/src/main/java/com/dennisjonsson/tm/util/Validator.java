package com.dennisjonsson.tm.util;

/**
 * Created by dennis on 2016-03-20.
 */
public class Validator {

    public boolean validateTag(String tag) throws ValidatorException {
        String trimmed = tag.trim();
        if(trimmed.length() != tag.length()) {
            throw new ValidatorException(
                    "tags should not begin or end with white spaces");
        }
        if(trimmed.length() <= 0){
            throw new ValidatorException(
                    "tags should contain at least one character which is not ' ' or ','");
        }if(tag.contains(",")){
            throw new ValidatorException(
                    "tags should not contain ',' characters");
        }
        return true;
    }
}
