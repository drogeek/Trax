package com.trax.mapelement;

/**
 * Created by unautre on 25/11/14.
 */
public class WayPoint extends Point {
    String name, desc;

    public WayPoint(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
