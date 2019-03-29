package com.leaderment.register.handler;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.leaderment.constant.StatusCode;
import com.leaderment.entity.ResultBean;
import com.leaderment.register.pojo.WarrantyRegistration;
import com.leaderment.register.service.IRegisterService;

@RestController
@RequestMapping(value="/register")
@SuppressWarnings("all")
public class RegisterHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterHandler.class);
	//@Resource(name="registerServiceImpl")
	@Autowired
	@Qualifier("registerServiceImpl")
	IRegisterService registerService;
	/*@Autowired
	RedisTemplate redisTemplate;
*/
	// 解决redis客户端显示乱码问题
	/*@Autowired(required = false)
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		RedisSerializer stringSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringSerializer);
		redisTemplate.setValueSerializer(stringSerializer);
		redisTemplate.setHashKeySerializer(stringSerializer);
		redisTemplate.setHashValueSerializer(stringSerializer);
		this.redisTemplate = redisTemplate;
	}*/
	@RequestMapping(value = "/selectRegisterById", method = RequestMethod.GET)
	public ResultBean selectRegisterById(@RequestParam(value="registerId") Integer registerId) {
		ResultBean resultBean = new ResultBean();
		try {
			WarrantyRegistration warrantyRegistration = registerService.selectByPrimaryKey(registerId);
			resultBean.setData(warrantyRegistration);
		} catch (Exception e) {
			resultBean.setCode(StatusCode.HTTP_FAILURE);
			resultBean.setMsg("获取id失败:id="+registerId);
			LOGGER.error("参数信息：id="+registerId,e);
		}
		return resultBean;
	}
	
	@RequestMapping(value = "/addRegister", method = RequestMethod.POST)
	public ResultBean addRegister(@RequestBody WarrantyRegistration register) {
		ResultBean resultBean = new ResultBean();
		try {
			register.setRegisterTime(new Date());
			registerService.insert(register);
			resultBean.setData("ok");
		} catch (Exception e) {
			//redisTemplate.opsForValue().set(register.getName(), register.toString());
			resultBean.setCode(StatusCode.HTTP_FAILURE);
			resultBean.setMsg("add register Failed！"+e.getMessage());
			LOGGER.error("参数信息："+register,e);
		}
		return resultBean;
	}
	@RequestMapping(value = "/deleteRegisterById", method = RequestMethod.GET)
	public ResultBean deleteRegisterById(@RequestParam(value="registerId") Integer registerId) {
		ResultBean resultBean = new ResultBean();
		try {
			registerService.deleteByPrimaryKey(registerId);
			resultBean.setData("ok");
		} catch (Exception e) {
			resultBean.setCode(StatusCode.HTTP_FAILURE);
			resultBean.setMsg("删除失败:"+e.getMessage());
			LOGGER.error("参数信息：id="+registerId,e);
		}
		return resultBean;
	}
	@RequestMapping(value = "/updateRegister", method = RequestMethod.POST)
	public ResultBean updateRegister(@RequestBody WarrantyRegistration register) {
		ResultBean resultBean = new ResultBean();
		try {
			registerService.updateByPrimaryKey(register);
			resultBean.setData("ok");
		} catch (Exception e) {
			resultBean.setCode(StatusCode.HTTP_FAILURE);
			resultBean.setMsg("update Register Failed！"+e.getMessage());
			LOGGER.error("参数信息："+register,e);
		}
		return resultBean;
	}
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public ResultBean findAll() {
		ResultBean resultBean = new ResultBean();
		try {
			List<WarrantyRegistration> selectAll = registerService.selectAll();
			resultBean.setData(selectAll);
		} catch (Exception e) {
			resultBean.setCode(StatusCode.HTTP_FAILURE);
			resultBean.setMsg("获取失败:"+e.getMessage());
			LOGGER.error("参数信息：",e);
		}
		return resultBean;
	}
}
