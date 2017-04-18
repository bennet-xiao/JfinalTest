package com.jfinal.test.interceptor;

import org.apache.log4j.Logger;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.test.constants.CommonConstants;
import com.jfinal.test.constants.UserConstants;

/**
 * @project name JFinalTest
 * @author PC
 * @modify time 2017年4月18日 上午10:58:39
 * @inputparm LoginInterceptor拦截器
 *TODO
 */
public class LoginInterceptor implements Interceptor {
    public Logger logger=Logger.getLogger(LoginInterceptor.class);
	/* (non-Javadoc)
	 * @see com.jfinal.aop.Interceptor#intercept(com.jfinal.aop.Invocation)
	 */
	@Override
	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
		logger.info("Before invoking " + inv.getActionKey());
		if (inv.getController().getSessionAttr(CommonConstants.USER) == null) {
			if(inv.getController().getSessionAttr(UserConstants.USER_LOGIN_TOKEN)==null){
			    inv.getController().createToken(UserConstants.USER_LOGIN_TOKEN);
			}
			inv.getController().setSessionAttr(CommonConstants.USER, null);
		}
		inv.getController().setSessionAttr(CommonConstants.CURRENT_URL, inv.getActionKey() + (inv.getController().getPara()==null?"":inv.getController().getPara()));
		inv.invoke();
		inv.getController().setSessionAttr(CommonConstants.LAST_URL, inv.getActionKey() + (inv.getController().getPara()==null?"":inv.getController().getPara()));
		logger.info("After invoking " + inv.getActionKey());
	}

}
