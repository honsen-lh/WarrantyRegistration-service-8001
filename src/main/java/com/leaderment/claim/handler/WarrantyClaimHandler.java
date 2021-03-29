package com.leaderment.claim.handler;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leaderment.claim.pojo.db.WarrantyClaim;
import com.leaderment.claim.service.IWarrantyClaimService;
import com.leaderment.constant.StatusCode;
import com.leaderment.entity.ResultBean;

@RestController
@RequestMapping(value="/warrantyClaim")
public class WarrantyClaimHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(WarrantyClaimHandler.class);
	@Resource(name="WarrantyClaimServiceImpl")
	IWarrantyClaimService warrantyClaimService;
	
	@RequestMapping(value="/addWarrantyClaim",method=RequestMethod.POST)
	public ResultBean addwarrantyClaim(@RequestBody WarrantyClaim warrantyClaim){
		ResultBean resultBean = new ResultBean();
		try {
			warrantyClaimService.insert(warrantyClaim);
			resultBean.setData("ok");
		} catch (Exception e) {
			resultBean.setCode(StatusCode.HTTP_FAILURE);
			resultBean.setMsg("Create warrantyClaim Failed！"+e);
			LOGGER.error("参数信息："+warrantyClaim.toString(),e);
		}
		return resultBean;
	}
	@RequestMapping(value="/updateWarrantyClaim",method=RequestMethod.POST)
	public ResultBean updateWarrantyClaim(@RequestBody WarrantyClaim warrantyClaim){
		ResultBean resultBean = new ResultBean();
		try {
			warrantyClaimService.updateByPrimaryKey(warrantyClaim);
			resultBean.setData("ok");
		} catch (Exception e) {
			resultBean.setCode(StatusCode.HTTP_FAILURE);
			resultBean.setMsg("update warrantyClaim Failed！"+e.getMessage());
			LOGGER.error("参数信息："+warrantyClaim.toString(),e);
		}
		return resultBean;
	}
	@RequestMapping(value = "/selectClaimById", method = RequestMethod.GET)
	public ResultBean selectClaimById(@RequestParam(value="claimId") Integer claimId) {
		ResultBean resultBean = new ResultBean();
		try {
			WarrantyClaim warrantyClaim = warrantyClaimService.selectByPrimaryKey(claimId);
			resultBean.setData(warrantyClaim);
		} catch (Exception e) {
			resultBean.setCode(StatusCode.HTTP_FAILURE);
			resultBean.setMsg("获取id失败:id="+claimId);
			LOGGER.error("参数信息：id="+claimId,e);
		}
		return resultBean;
	}
	
	@RequestMapping(value = "/deleteClaimById", method = RequestMethod.GET)
	public ResultBean deleteRegisterById(@RequestParam(value="claimId") Integer claimId) {
		ResultBean resultBean = new ResultBean();
		try {
			warrantyClaimService.deleteByPrimaryKey(claimId);
			resultBean.setData("ok");
		} catch (Exception e) {
			resultBean.setCode(StatusCode.HTTP_FAILURE);
			resultBean.setMsg("删除失败:"+e.getMessage());
			LOGGER.error("参数信息：id="+claimId,e);
		}
		return resultBean;
	}
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public ResultBean findAll() {
		ResultBean resultBean = new ResultBean();
		try {
			List<WarrantyClaim> selectAll = warrantyClaimService.selectAll();
			resultBean.setData(selectAll);
		} catch (Exception e) {
			resultBean.setCode(StatusCode.HTTP_FAILURE);
			resultBean.setMsg("获取失败:"+e.getMessage());
			LOGGER.error("参数信息：",e);
		}
		return resultBean;
	}
}
