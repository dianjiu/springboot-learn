package co.dianjiu.security.service.impl;

import co.dianjiu.security.dao.UserDao;
import co.dianjiu.security.model.UserEntity;
import co.dianjiu.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	/**
	 * 保存用户
	 */
	@Override
	public void saveUser(UserEntity user) {
		userDao.insertUser(user);
	}

}
