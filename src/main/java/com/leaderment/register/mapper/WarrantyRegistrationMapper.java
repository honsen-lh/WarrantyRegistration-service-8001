package com.leaderment.register.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.leaderment.register.pojo.WarrantyRegistration;

public interface WarrantyRegistrationMapper {
    int deleteByPrimaryKey(Integer registerId);

    int insert(WarrantyRegistration record);

    WarrantyRegistration selectByPrimaryKey(Integer registerId);

    List<WarrantyRegistration> selectAll();

    int updateByPrimaryKey(WarrantyRegistration record);

	Integer getCountByEmail(@Param("orderId") String orderId,@Param("businessName") String businessName);
}