# SpringBoot 整合 SpringSecurity

### 内容概述
SpringBoot 整合 SpringSecurity 实现注册、登录、登出、RABC权限管理

### 流程图


### 实现步骤
1. 引入依赖
```java
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
2. 新建数据库
- 创建用户表sys_user:
```sql
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

```
- 创建角色表sys_role:
```sql
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

```

- 创建菜单表
- 创建用户角色中间表
- 创建角色菜单中间表

3. 利用easyCode生成基础类

4. 准备静态页面

5. 自定义WebSecurityConfigurerAdapter实现类



