package com.leaderment.register.service;

import java.util.List;

import com.leaderment.register.pojo.WarrantyRegistration;

public interface IRegisterService {
	 	void deleteByPrimaryKey(Integer registerId);

	 	void insert(WarrantyRegistration record);

	    WarrantyRegistration selectByPrimaryKey(Integer registerId);

	    List<WarrantyRegistration> selectAll();

	    void updateByPrimaryKey(WarrantyRegistration record);

		Integer getCountByEmail(String orderId,String businessName);
}
