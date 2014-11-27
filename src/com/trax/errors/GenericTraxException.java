package com.trax.errors;

import android.util.Log;

/**
 * Created by unautre on 24/11/14.
 */
public class GenericTraxException extends Exception {
    GenericTraxException(){
        Log.e("TRAX", toString());
    }

    GenericTraxException(String err){ Log.e("TRAX", err); }
}
