package co.dianjiu.security.service.impl;

import co.dianjiu.security.dao.MenuDao;
import co.dianjiu.security.dao.RoleDao;
import co.dianjiu.security.dao.UserDao;
import co.dianjiu.security.model.Menu;
import co.dianjiu.security.model.Role;
import co.dianjiu.security.model.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * 获取用户相关信息
 * @author charlie
 *
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
	private Logger log = LoggerFactory.getLogger(UserDetailServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;

	@Override
	public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
		// 根据用户名查找用户
		UserEntity user = userDao.getUserByUsername(username);
		System.out.println(user);
		if (user != null) {
			System.out.println("UserDetailsService");
			//根据用户id获取用户角色
			List<Role> roles = roleDao.getUserRoleByUserId(user.getId());
			// 填充权限
			Collection<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();
			for (Role role : roles) {
				authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
			}
			//填充权限菜单
			List<Menu> menus=menuDao.getRoleMenuByRoles(roles);
			return new UserEntity(username,user.getPassword(),authorities,menus);
		} else {
			System.out.println(username +" not found");
			throw new UsernameNotFoundException(username +" not found");
		}		
	}

}
