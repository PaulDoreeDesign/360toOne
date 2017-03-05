package com.ostojan.x360.model;

import android.content.Intent;

public class Region {
    private Integer id;
    private String name;
    private String code;

    public Region() {
        id = null;
        name = null;
        code = null;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public static class Builder {
        private Region region;

        public Builder() {
            region = new Region();
        }

        public Builder id(int id) {
            region.id = id;
            return this;
        }

        public Builder name(String name) {
            region.name = name;
            return this;
        }

        public Builder code(String code) {
            region.code = code;
            return this;
        }

        public Region build() {
            return region;
        }
    }
}
