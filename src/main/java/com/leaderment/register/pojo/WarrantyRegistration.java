package com.leaderment.register.pojo;

import java.util.Date;

public class WarrantyRegistration {
    private Integer registerId;

    private String name;

    private String country;

    private String emailAddress;

    private String phoneModel;

    private String channel;

    private String productModel;

    private String orderId;

    private Date registerTime;

    public Integer getRegisterId() {
        return registerId;
    }

    public void setRegisterId(Integer registerId) {
        this.registerId = registerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress == null ? null : emailAddress.trim();
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel == null ? null : phoneModel.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel == null ? null : productModel.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

	@Override
	public String toString() {
		return "WarrantyRegistration [registerId=" + registerId + ", name=" + name + ", country=" + country
				+ ", emailAddress=" + emailAddress + ", phoneModel=" + phoneModel + ", channel=" + channel
				+ ", productModel=" + productModel + ", orderId=" + orderId + ", registerTime=" + registerTime + "]";
	}
    
}