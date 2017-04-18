package com.jfinal.test.controller;

import com.jfinal.core.Controller;

public class HtmlController extends Controller {

	public void index(){
		super.setAttr("current_item", "home");
		super.render("index.html");
	}
	
	public void birthday(){
		super.setAttr("current_item", "birthday");
		super.render("products.html");
	}
	
	public void wed(){
		super.setAttr("current_item", "wed");
		super.render("products.html");
	}
	
	public void special(){
		super.setAttr("current_item", "special");
		super.render("products.html");
	}
	public void store(){
		super.setAttr("current_item", "store");
		super.render("products.html");
	}
}
