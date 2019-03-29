package com.leaderment.register.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leaderment.register.mapper.WarrantyRegistrationMapper;
import com.leaderment.register.pojo.WarrantyRegistration;
import com.leaderment.register.service.IRegisterService;

@Service(value="registerServiceImpl")
public class RegisterServiceImpl implements IRegisterService {

	@Autowired
	WarrantyRegistrationMapper warrantyRegistrationMapper;
	public void deleteByPrimaryKey(Integer registerId) {
		warrantyRegistrationMapper.deleteByPrimaryKey(registerId);
	}

	public void insert(WarrantyRegistration record) {
		warrantyRegistrationMapper.insert(record);
	}

	public WarrantyRegistration selectByPrimaryKey(Integer registerId) {
		return warrantyRegistrationMapper.selectByPrimaryKey(registerId);
		
	}

	public List<WarrantyRegistration> selectAll() {
		return warrantyRegistrationMapper.selectAll();
	}

	public void updateByPrimaryKey(WarrantyRegistration record) {
		warrantyRegistrationMapper.updateByPrimaryKey(record);
	}

}
