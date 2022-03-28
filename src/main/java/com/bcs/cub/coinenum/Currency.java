package com.bcs.cub.coinenum;

public enum Currency {

	USD("USD", "美金"), GBP("GBP", "英鎊"), EUR("EUR", "歐元");

	Currency(String en, String chinese) {
		this.en = en;
		this.chinese = chinese;
	}

	private final String en;
	private final String chinese;

	public static Currency getCurrency(String i) {

		for (Currency currency : values()) {
			if (currency.getEn().equals(i)) {
				return currency;
			}
		}
		return null;

	}

	public String getEn() {
		return this.en;
	}

	public String getChinese() {
		return this.chinese;
	}
}
