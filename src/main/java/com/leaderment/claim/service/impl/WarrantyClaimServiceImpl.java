package com.leaderment.claim.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leaderment.claim.mapper.WarrantyClaimMapper;
import com.leaderment.claim.pojo.db.WarrantyClaim;
import com.leaderment.claim.service.IWarrantyClaimService;

@Service("WarrantyClaimServiceImpl")
public class WarrantyClaimServiceImpl implements IWarrantyClaimService {
	
	@Autowired
	WarrantyClaimMapper warrantyClaimMapper;
	@Override
	public void deleteByPrimaryKey(Integer claimId) {
		warrantyClaimMapper.deleteByPrimaryKey(claimId);
	}

	@Override
	public void insert(WarrantyClaim record) {
		warrantyClaimMapper.insert(record);

	}

	@Override
	public WarrantyClaim selectByPrimaryKey(Integer claimId) {
		return warrantyClaimMapper.selectByPrimaryKey(claimId);
	}

	@Override
	public List<WarrantyClaim> selectAll() {
		return warrantyClaimMapper.selectAll();
	}

	@Override
	public void updateByPrimaryKey(WarrantyClaim record) {
		warrantyClaimMapper.updateByPrimaryKey(record);

	}

}
