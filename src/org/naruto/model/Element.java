package org.naruto.model;

public enum Element {
	FIRE("Fire"),
	WATER("Water"),
	WIND("Wind"),
	EARTH("Earth"),
	LIGHTNING("Lightning"),
	VOID("Void");
	
    private final String text;
	
	private Element(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
