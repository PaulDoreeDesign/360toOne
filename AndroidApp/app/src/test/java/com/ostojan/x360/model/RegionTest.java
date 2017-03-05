package com.ostojan.x360.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class RegionTest {

    @Test
    public void checkGetters() {
        Region region = RegionHelper.createSimpleRegion();
        assertEquals("Id is not correct", RegionHelper.ID, region.getId());
        assertEquals("Name is not correct", RegionHelper.NAME, region.getName());
        assertEquals("Code is not correct", RegionHelper.CODE, region.getCode());
    }

    @Test
    public void compareTwoSameRegions() {
        Region region1 = RegionHelper.createSimpleRegion();
        Region region2 = RegionHelper.createSimpleRegion();
        assertEquals("Regions are not equals", region1, region2);
        assertEquals("Regions are not equals", region2, region1);
    }

    @Test
    public void compareRegionToItself() {
        Region region = RegionHelper.createSimpleRegion();
        assertEquals("Region is not equals to itself", region, region);
    }

    @Test
    public void compareTwoRegionsWithDifferentIds() {
        Integer id1 = 1;
        Integer id2 = 2;
        Region region1 = RegionHelper.createRegionFromData(id1, RegionHelper.NAME, RegionHelper.CODE);
        Region region2 = RegionHelper.createRegionFromData(id2, RegionHelper.NAME, RegionHelper.CODE);
        assertNotEquals("Regions are equals", region1, region2);
        assertNotEquals("Regions are equals", region2, region1);
    }

    @Test
    public void compareTwoRegionsWithDifferentNames() {
        String name1 = "Country1";
        String name2 = "Country2";
        Region region1 = RegionHelper.createRegionFromData(RegionHelper.ID, name1, RegionHelper.CODE);
        Region region2 = RegionHelper.createRegionFromData(RegionHelper.ID, name2, RegionHelper.CODE);
        assertNotEquals("Regions are equals", region1, region2);
        assertNotEquals("Regions are equals", region2, region1);
    }

    @Test
    public void compareTwoRegionsWithDifferentCodes() {
        String code1 = "cu";
        String code2 = "co";
        Region region1 = RegionHelper.createRegionFromData(RegionHelper.ID, RegionHelper.NAME, code1);
        Region region2 = RegionHelper.createRegionFromData(RegionHelper.ID, RegionHelper.NAME, code2);
        assertNotEquals("Regions are equals", region1, region2);
        assertNotEquals("Regions are equals", region2, region1);
    }
}
