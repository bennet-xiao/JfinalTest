/**
 * 
 */
package com.jfinal.test.validator;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.test.constants.CommonConstants;
import com.jfinal.test.constants.UserConstants;
import com.jfinal.test.model.User;
import com.jfinal.validate.Validator;

/**
 * @project name JFinalTest
 * @author PC
 * @modify time 2017年4月18日 上午11:28:25
 * @inputparmUserLoginValidator TODO
 */
public class UserLoginValidator extends Validator {
	private Logger logger=Logger.getLogger(UserLoginValidator.class);
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jfinal.validate.Validator#validate(com.jfinal.core.Controller)
	 */
	@Override
	protected void validate(Controller c) {
		this.setShortCircuit(true);//不设置此参数不会立即抛出
		super.validateRequiredString("user.phone", "message", "号码不能为空");
		super.validateRequiredString("user.password", "message", "密码不能为空");
		super.validateToken(UserConstants.USER_LOGIN_TOKEN,"message", "请刷新后重新提交登陆");
		User user =c.getModel(User.class, UserConstants.USER_TABLE_NAME);
		MessageDigest md;
		String pwd="";
		try {
			md = MessageDigest.getInstance("MD5");
			// 计算md5函数 
			md.update(user.getStr(UserConstants.USER_PASS_COLUMN).getBytes()); 
			// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符 
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值 
			pwd = new BigInteger(1, md.digest()).toString(16); 
			user.set(UserConstants.USER_PASS_COLUMN, pwd);
			c.setAttr(CommonConstants.USER, user);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			c.setAttr("message", "密码加密出错，请稍后重试");
			logger.info("登陆失败，密码加密出错"+e);
		} 
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jfinal.validate.Validator#handleError(com.jfinal.core.Controller)
	 */
	@Override
	protected void handleError(Controller c) {
		c.setAttr("code", -100);
		c.renderJson();
	}
}