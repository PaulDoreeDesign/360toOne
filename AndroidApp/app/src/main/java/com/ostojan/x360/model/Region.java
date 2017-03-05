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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Region region = (Region) o;

        if (id != null ? !id.equals(region.id) : region.id != null) {
            return false;
        }
        if (name != null ? !name.equals(region.name) : region.name != null) {
            return false;
        }
        return code != null ? code.equals(region.code) : region.code == null;

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
