package com.jfinal.test.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class TestController extends Controller {

	public void index(){
		Record user=new Record().set("user_id", 1).set("user_name", "test1");
		Db.save("user", user);
		List<Record> userlist=Db.find("select * from user where 1=1");
		if(userlist!=null && userlist.size()>0){
		    super.renderJson(userlist);
		}else{
			 super.renderText("插入数据失败");
		}
	}
}
