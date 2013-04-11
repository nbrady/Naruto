package org.naruto.model;

public enum Element {
	FIRE("Fire"),
	WATER("Water"),
	WIND("Wind"),
	EARTH("Earth"),
	LIGHTNING("Lightning"),
	VOID("Void"),
	FIRE_WATER("Fire/Water"),
	FIRE_WIND("Fire/Wind"),
	FIRE_EARTH("Fire/Earth"),
	FIRE_LIGHTNING("Fire/Lightning"),
	FIRE_VOID("Fire/Void"),
	WATER_WIND("Water/Wind"),
	WATER_EARTH("Water/Earth"),
	WATER_LIGHTNING("Water/Lightning"),
	WATER_VOID("Water/Void"),
	WIND_EARTH("Wind/Earth"),
	WIND_LIGHTNING("Wind/Lightning"),
	WIND_VOID("Wind/Void"),
	EARTH_LIGHTNING("Earth/Lightning"),
	EARTH_VOID("Earth/Void"),
	LIGHTNING_VOID("Lightning/Void");
	
    private final String text;
	
	private Element(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
    
    public String getValue() {
        return text;
    }
}
