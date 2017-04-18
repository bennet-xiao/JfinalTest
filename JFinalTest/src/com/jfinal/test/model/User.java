package com.jfinal.test.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.TableMapping;

/**
 * @author bennet-xiao
 *数据库对应实体user
 */
public class User extends Model<User> {
    /** * */
	private static final long serialVersionUID = 1L;
	public static final User dao = new User();
	
	/**
	 * @param name
	 * @param pass
	 * @return根据电话号码和密码查用户
	 */
	public List<User> findUser(String name,String pass) {
		String sql="select * from "+TableMapping.me().getTable(User.class).getName()
				+ " where phone=? and password=?";
		return User.dao.find(sql,
				name,pass);
	}
}
