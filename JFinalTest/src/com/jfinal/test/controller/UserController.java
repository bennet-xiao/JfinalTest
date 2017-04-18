package com.jfinal.test.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.test.constants.CommonConstants;
import com.jfinal.test.constants.UserConstants;
import com.jfinal.test.model.User;
import com.jfinal.test.validator.UserLoginValidator;
import com.jfinal.test.validator.UserRegisterValidator;

public class UserController extends Controller {
	private Logger logger=Logger.getLogger(UserController.class);
	/**
	 * 注册用户
	 */
	@Before(UserRegisterValidator.class)
	public void addUser() {
		Map<String,Object> resultmap=new HashMap<String,Object>();
		User user = getAttr(CommonConstants.USER);
		if (PropKit.get(CommonConstants.DRIVER_CLASS_NAME).contains("sqlserver")) {
			 user.set(UserConstants.REGIST_TIME_COLUMN, new Timestamp(new Date().getTime()));
		}else{
		   user.set(UserConstants.REGIST_TIME_COLUMN, new Date());
		}
		user.set(UserConstants.IS_VALID_COLUMN, 1);
		user.save();
		int result=user.getInt(UserConstants.USER_ID_COLUMN);
		resultmap.put("code", result);
		resultmap.put("message", "注册成功！点击确定自动登录");
		resultmap.put("next_url", super.getSessionAttr(CommonConstants.LAST_URL));
		super.setSessionAttr(CommonConstants.USER, user);
		logger.info("用户id："+result+"注册成功");
		super.renderJson(resultmap);
	}
	public void register(){
		super.createToken(UserConstants.REGISTER_ADD_TOKEN);
		super.setAttr("current_item", "home");
		super.render("register.html");
	}
	@Before(UserLoginValidator.class)
	public void logIn(){
		User user = getAttr(CommonConstants.USER);
		List<User> userlist=User.dao.findUser(user.getStr(UserConstants.USER_PHONE_COLUMN),user.getStr(UserConstants.USER_PASS_COLUMN));
		Map<String,Object> resultmap=new HashMap<String,Object>();
		if(userlist!=null && userlist.size()==1){
			super.setSessionAttr(CommonConstants.USER, userlist.get(0));
			resultmap.put("next_url", super.getSessionAttr(CommonConstants.LAST_URL));
			resultmap.put("code",1);
			resultmap.put("message", "登陆成功");
		}else{
			resultmap.put("code", -100);
			resultmap.put("message", "用户名或密码错误，请重新登陆");
		}
		super.renderJson(resultmap);
	}
	
	public void logOut(){
		super.setSessionAttr(CommonConstants.USER, null);
		super.redirect(""+ super.getSessionAttr(CommonConstants.LAST_URL));
	}
}
