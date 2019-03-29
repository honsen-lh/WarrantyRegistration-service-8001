package com.leaderment.claim.mapper;

import java.util.List;

import com.leaderment.claim.pojo.db.WarrantyClaim;

public interface WarrantyClaimMapper {
	void deleteByPrimaryKey(Integer claimId);

	void insert(WarrantyClaim record);

    WarrantyClaim selectByPrimaryKey(Integer claimId);

    List<WarrantyClaim> selectAll();

    void updateByPrimaryKey(WarrantyClaim record);
}