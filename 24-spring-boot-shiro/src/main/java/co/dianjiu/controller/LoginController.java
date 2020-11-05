package co.dianjiu.controller;

import co.dianjiu.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    /**
     * 登出
     */
    @PostMapping("/logout")
    public String logout() {
        //return loginService.logout();
        return "logout success";
    }

    @RequestMapping("/login")
    public String login(User user) {
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUserName(),
                user.getPassword()
        );
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
//            subject.checkRole("admin");
//            subject.checkPermissions("query", "add");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "账号或密码错误！";
        } catch (AuthorizationException e) {
            e.printStackTrace();
            return "没有权限";
        }
        return "login success";
    }

    /**
     * 注解验角色和权限
     */
    @RequiresRoles("admin")
    @RequiresPermissions("add")
    @RequestMapping("/add")
    public String add() {
        return "success!";
    }

    /**
     * 需要登录才能访问
     */
    @GetMapping("/1")
    public String test1() {
        return "success";
    }

    /**
     * 需要 admin 角色才能访问
     */
    @GetMapping("/2")
    @RequiresRoles("admin")
    public String test2() {
        return "success";
    }

    /**
     * 需要 "admin:add" 权限才能访问
     */
    @GetMapping("/3")
    @RequiresPermissions("add")
    public String test3() {
        return "success";
    }

}
