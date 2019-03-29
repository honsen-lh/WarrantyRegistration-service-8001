package com.leaderment.register.mapper;

import java.util.List;

import com.leaderment.register.pojo.WarrantyRegistration;

public interface WarrantyRegistrationMapper {
    int deleteByPrimaryKey(Integer registerId);

    int insert(WarrantyRegistration record);

    WarrantyRegistration selectByPrimaryKey(Integer registerId);

    List<WarrantyRegistration> selectAll();

    int updateByPrimaryKey(WarrantyRegistration record);
}