package com.sskarga.shoptoolsexample;

import java.io.Serializable;

public class RechargeableTools implements Serializable {
    private String title;
    private String info;
    private Integer imageResId;

    public RechargeableTools(String title, String info, Integer imageResId) {
        this.title = title;
        this.info = info;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public Integer getImageResId() {
        return imageResId;
    }

    @Override
    public String toString() {
        return title;
    }
}
