package com.healthyfish.healthyfishdoctor.POJO;

public class BeanHospCardAuthReq extends BeanBaseReq {
	private String hosp;
	private String cardId;
	private String name;
				
	BeanHospCardAuthReq(){super(BeanHospCardAuthReq.class.getSimpleName());}
	
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHosp() {
		return hosp;
	}

	public void setHosp(String hosp) {
		this.hosp = hosp;
	}

}
