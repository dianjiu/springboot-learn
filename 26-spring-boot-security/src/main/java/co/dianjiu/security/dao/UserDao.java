package co.dianjiu.security.dao;

import co.dianjiu.security.model.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	UserEntity getUserByUsername(String username);
	/**
	 * 新增用户
	 * @param user
	 */
	void insertUser(UserEntity user);

}
