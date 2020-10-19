package co.dianjiu.security.handler;

import co.dianjiu.security.model.Menu;
import co.dianjiu.security.model.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * RBAC数据模型控制权限
 * @author charlie
 *
 */
@Component("rbacPermission")
public class RbacPermission{

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		Object principal = authentication.getPrincipal();
		boolean hasPermission = false;
		/*
		 * instanceof 严格来说是Java中的一个双目运算符，用来测试一个对象是否为一个类的实例
		 * 用法为：boolean result = obj instanceof Class
		 * 其中 obj 为一个对象，Class 表示一个类或者一个接口，
		 * 当 obj 为 Class 的对象，或者是其直接或间接子类，或者是其接口的实现类，结果result 都返回 true，否则返回false。
		 */
		if (principal instanceof UserEntity) {
			// 读取用户所拥有的权限菜单
			List<Menu> menus = ((UserEntity) principal).getRoleMenus();
			System.out.println(menus.size());
			for (Menu menu : menus) {
				if (antPathMatcher.match(menu.getMenuUrl(), request.getRequestURI())) {
					hasPermission = true;
					break;
				}
			}
		}
		return hasPermission;
	}
}
