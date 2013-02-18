package org.naruto.model;

public enum Rarity {
	COMMON("C"),
	UNCOMMON("U"),
	RARE("R"),
	SUPER_RARE("SR"),
	STARTER_EXCLUSIVE("ST"),
	STARTER_EXCLUSIVE_SUPER_RARE("STSR"),
	PROMO("PR"),
	NONE("NONE");
	
	private final String text;
	
	private Rarity(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
