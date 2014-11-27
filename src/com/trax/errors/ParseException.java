package com.trax.errors;

/**
 * Created by unautre on 27/11/14.
 */
public class ParseException extends GenericTraxException {
    private String err;

    public ParseException(String err){
        super(err);
        this.err = err;
    }
}
