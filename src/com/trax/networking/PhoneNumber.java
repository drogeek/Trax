package com.trax.networking;

import android.telephony.PhoneNumberUtils;

/**
 * Created by unautre on 06/12/14.
 */
public class PhoneNumber implements Comparable<PhoneNumber> {
    private String self;

    public PhoneNumber(String self) {
        this.self = self;
    }

    public String getNum() {
        return self;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneNumber that = (PhoneNumber) o;
        return PhoneNumberUtils.compare(that.self, this.self);
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "self='" + self + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return self.hashCode();
    }

    @Override
    public int compareTo(PhoneNumber another) {
        return self.compareTo(another.self);
    }
}
