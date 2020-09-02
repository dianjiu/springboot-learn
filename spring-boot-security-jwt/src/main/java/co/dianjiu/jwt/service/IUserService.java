package co.dianjiu.jwt.service;

import co.dianjiu.jwt.model.Role;
import co.dianjiu.jwt.model.UserEntity;

import java.util.List;

/**
 * 后台管理员Service
 * Created by macro on 2018/4/26.
 */
public interface IUserService {
    /**
     * 根据用户名获取后台管理员
     */
    UserEntity getUserByUsername(String username);

    /**
     * 注册功能
     */
    UserEntity register(UserEntity umsAdminParam);

    /**
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     */
    List<Role> getPermissionList(Long adminId);
}
