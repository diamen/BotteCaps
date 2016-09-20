package com.stobinski.bottlecaps.ejb.wrappers;

public class CountriesWithAmount {
	
	private String name;
	private String flag;
	private long id;
	private long amount;
	private long beer;
	private long nobeer;
	
	public CountriesWithAmount(Long id, String name, String flag, Long amount, Long beer, Long nobeer) {
		this.id = id;
		this.name = name;
		this.flag = flag;
		this.amount = amount;
		this.beer = beer;
		this.nobeer = nobeer;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public long getBeer() {
		return beer;
	}
	public void setBeer(long beer) {
		this.beer = beer;
	}
	public long getNobeer() {
		return nobeer;
	}
	public void setNobeer(long nobeer) {
		this.nobeer = nobeer;
	}
	
	@Override
	public String toString() {
		return String.format("[id: %d, name: %s, flag: %s, amount: %d]", id, name, flag, amount);
	}
	
}