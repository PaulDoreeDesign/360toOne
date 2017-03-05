package com.ostojan.x360.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegionTypeAdapterTest {

    private Gson gson;

    @Before
    public void setUp() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Region.class, new RegionTypeAdapter());
        gson = gsonBuilder.create();
    }

    @Test
    public void createJsonFromRegion() {
        Region region = RegionHelper.createSimpleRegion();
        compareJsonWithParserResult(region);
    }

    @Test
    public void createJsonFromRegionWithoutId() {
        Region region = RegionHelper.createSimpleRegionWithoutId();
        compareJsonWithParserResult(region);
    }

    @Test
    public void createJsonFromRegionWithoutName() {
        Region region = RegionHelper.createSimpleRegionWithoutName();
        compareJsonWithParserResult(region);
    }

    @Test
    public void createJsonFromRegionWithoutCode() {
        Region region = RegionHelper.createSimpleRegionWithoutCode();
        compareJsonWithParserResult(region);
    }

    @Test
    public void readRegionFromJson() {
        Region region = RegionHelper.createSimpleRegion();
        compareRegionWithParserResult(region);
    }

    @Test
    public void readRegionWithoutIdFromJson() {
        Region region = RegionHelper.createSimpleRegionWithoutId();
        compareRegionWithParserResult(region);
    }

    @Test
    public void readRegionWithoutNameFromJson() {
        Region region = RegionHelper.createSimpleRegionWithoutName();
        compareRegionWithParserResult(region);
    }

    @Test
    public void readRegionWithoutCodeFromJson() {
        Region region = RegionHelper.createSimpleRegionWithoutCode();
        compareRegionWithParserResult(region);
    }

    private void compareJsonWithParserResult(Region region) {
        String json = createJsonStringFromRegion(region);
        assertEquals("Result json string is not correct", gson.toJson(region), json);
    }

    private String createJsonStringFromRegion(Region region) {
        String separator = "";
        StringBuilder json = new StringBuilder();
        json.append("{");
        if (region.getId() != null) {
            json.append(separator);
            json.append("\"id\":");
            json.append(region.getId());
            separator = ",";
        }
        if (region.getName() != null) {
            json.append(separator);
            json.append("\"name\":");
            json.append(String.format("\"%s\"", region.getName()));
            separator = ",";
        }
        if (region.getCode() != null) {
            json.append(separator);
            json.append("\"code\":");
            json.append(String.format("\"%s\"", region.getCode()));
            separator = ",";
        }
        json.append("}");
        return json.toString();
    }

    private void compareRegionWithParserResult(Region region) {
        String regionJson = createJsonStringFromRegion(region);
        Region resultRegion = gson.fromJson(regionJson, Region.class);
        assertEquals("Region object is not correct", region, resultRegion);
    }
}
