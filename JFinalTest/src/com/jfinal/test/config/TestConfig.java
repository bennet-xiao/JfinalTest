package com.jfinal.test.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.SqlServerDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.jfinal.test.constants.CommonConstants;
import com.jfinal.test.constants.UserConstants;
import com.jfinal.test.controller.HtmlController;
import com.jfinal.test.controller.UserController;
import com.jfinal.test.interceptor.LoginInterceptor;
import com.jfinal.test.model.User;

/**
 * @author bennet-xiao 运行参数WebRoot 8088 / 5
 */
public class TestConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {
		// TODO Auto-generated method stub
		PropKit.use("jdbc.properties"); 
		me.setDevMode(true);
	}

	@Override
	public void configRoute(Routes me) {
		// TODO Auto-generated method stub
		// me.add("/hello", TestController.class);
//		me.addInterceptor(new UserRegisterValidator());
		me.setBaseViewPath("/WEB-INF/cake");
		//不加“/”跳转页面的路径为/WEB-INF/cake/user/test.html
		me.add("/cake/index", HtmlController.class,"/");
		me.add("/cake/user", UserController.class,"/");
	}

	/* (non-Javadoc)
	 * @see com.jfinal.config.JFinalConfig#configEngine(com.jfinal.template.Engine)
	 * 可以在此处添加自定义的一些模板 #define
	 */
	@Override
	public void configEngine(Engine me) {
		// TODO Auto-generated method stub
//		me.addSharedFunction("/_view/common/__layout.html");
//		me.addSharedFunction("/_view/common/_paginate.html");
//		me.addSharedFunction("/_view/common/__admin_layout.html");
	}

	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub
		DruidPlugin dp = new DruidPlugin(PropKit.get(CommonConstants.DB_URL),
				PropKit.get(CommonConstants.DB_USER), PropKit.get(CommonConstants.DB_PWD));
		if (PropKit.get(CommonConstants.DRIVER_CLASS_NAME).contains("sqlserver")) {
		     dp.setDriverClass(PropKit.get(CommonConstants.DRIVER_CLASS_NAME));
		}
		me.add(dp);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
		if (PropKit.get(CommonConstants.DRIVER_CLASS_NAME).contains("sqlserver")) {
			arp.setDialect(new SqlServerDialect());
		}
		me.add(arp);
		// 表与model的映射关系，user_id为主键
		if(PropKit.get(CommonConstants.DRIVER_CLASS_NAME).contains("mysql")){
		    arp.addMapping(UserConstants.USER_TABLE_NAME, UserConstants.USER_ID_COLUMN, User.class);
		}else if(PropKit.get(CommonConstants.DRIVER_CLASS_NAME).contains("sqlserver")){
			arp.addMapping(PropKit.get(CommonConstants.DB_SQL_PREFIX).replace("*", UserConstants.USER_TABLE_NAME), UserConstants.USER_ID_COLUMN, User.class);
		}
		// arp.addMapping("article", "article_id", Article.class);
	}

	/* (non-Javadoc)
	 * @see com.jfinal.config.JFinalConfig#configInterceptor(com.jfinal.config.Interceptors)
	 * 这里是添加全部url的拦截器
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub
		me.add(new LoginInterceptor());
		me.add(new SessionInViewInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
		// me.add(new UrlSkipHandler("\\*.html",true));
	}

}
