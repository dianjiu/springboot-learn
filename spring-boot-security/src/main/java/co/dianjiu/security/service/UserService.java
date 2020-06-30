package co.dianjiu.security.service;


import co.dianjiu.security.model.UserEntity;

public interface UserService {
	/**
	 * 保存用户
	 * @param user
	 */
	void saveUser(UserEntity user);

}
