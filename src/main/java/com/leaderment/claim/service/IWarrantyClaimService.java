package com.leaderment.claim.service;

import java.util.List;

import com.leaderment.claim.pojo.db.WarrantyClaim;

public interface IWarrantyClaimService {
	void deleteByPrimaryKey(Integer claimId);

	void insert(WarrantyClaim record);

    WarrantyClaim selectByPrimaryKey(Integer claimId);

    List<WarrantyClaim> selectAll();

    void updateByPrimaryKey(WarrantyClaim record);
}