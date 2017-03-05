package com.ostojan.x360.model;

public class RegionHelper {

    public static final Integer ID = 1;
    public static final String NAME = "Country";
    public static final String CODE = "cu";

    public static Region createSimpleRegion() {
        return createRegionFromData(ID, NAME, CODE);
    }

    public static Region createSimpleRegionWithoutId() {
        return createRegionFromData(null, NAME, CODE);
    }

    public static Region createSimpleRegionWithoutName() {
        return createRegionFromData(ID, null, CODE);
    }

    public static Region createSimpleRegionWithoutCode() {
        return createRegionFromData(ID, NAME, null);
    }

    public static Region createRegionFromData(Integer id, String name, String code) {
        Region.Builder regionBuilder = new Region.Builder();
        if (id != null) {
            regionBuilder.id(id);
        }
        if (name != null) {
            regionBuilder.name(name);
        }
        if (code != null) {
            regionBuilder.code(code);
        }
        return regionBuilder.build();
    }
}
