package com.leaderment.claim.pojo.db;

import java.io.Serializable;
import java.util.Date;

public class WarrantyClaim implements Serializable{
   
	private static final long serialVersionUID = 1L;

	private Integer claimId;

    private String orderId;

    private String emailAddress;

    private String productModel;

    private Integer defectiveProductQty;

    private String seriesNo;

    private String orderDetails;

    private String photoPath;
    
    private String screenshotPath;

    private String receiverName;

    private String phoneNumber;

    private String addressLine1;

    private String addressLine2;

    private String countryCode;

    private String state;

    private String city;

    private String postCode;
    
    private Boolean subscribe;
    
    private String businessName;

    private Date claimTime;

    public Integer getClaimId() {
        return claimId;
    }

    public void setClaimId(Integer claimId) {
        this.claimId = claimId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress == null ? null : emailAddress.trim();
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel == null ? null : productModel.trim();
    }

    public Integer getDefectiveProductQty() {
        return defectiveProductQty;
    }

    public void setDefectiveProductQty(Integer defectiveProductQty) {
        this.defectiveProductQty = defectiveProductQty;
    }

    public String getSeriesNo() {
        return seriesNo;
    }

    public void setSeriesNo(String seriesNo) {
        this.seriesNo = seriesNo == null ? null : seriesNo.trim();
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails == null ? null : orderDetails.trim();
    }
    
    public String getScreenshotPath() {
		return screenshotPath;
	}

	public void setScreenshotPath(String screenshotPath) {
		this.screenshotPath = screenshotPath;
	}

	public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath == null ? null : photoPath.trim();
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1 == null ? null : addressLine1.trim();
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2 == null ? null : addressLine2.trim();
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode == null ? null : countryCode.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }
    
    public Boolean getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Boolean subscribe) {
		this.subscribe = subscribe;
	}
	
	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public Date getClaimTime() {
        return claimTime;
    }

    public void setClaimTime(Date claimTime) {
        this.claimTime = claimTime;
    }

	@Override
	public String toString() {
		return "WarrantyClaim [claimId=" + claimId + ", orderId=" + orderId + ", emailAddress=" + emailAddress
				+ ", productModel=" + productModel + ", defectiveProductQty=" + defectiveProductQty + ", seriesNo="
				+ seriesNo + ", orderDetails=" + orderDetails + ", photoPath=" + photoPath + ", receiverName="
				+ receiverName + ", phoneNumber=" + phoneNumber + ", addressLine1=" + addressLine1 + ", addressLine2="
				+ addressLine2 + ", countryCode=" + countryCode + ", state=" + state + ", city=" + city + ", postCode="
				+ postCode + ", subscribe=" + subscribe + ", claimTime=" + claimTime + "]";
	}
    
}