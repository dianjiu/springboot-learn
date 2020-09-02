package co.dianjiu.security.config;

import co.dianjiu.security.handler.CustomAccessDeniedHandler;
import co.dianjiu.security.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * prePostEnabled :决定Spring Security的前注解是否可用 [@PreAuthorize,@PostAuthorize,..]
 * secureEnabled : 决定是否Spring Security的保障注解 [@Secured] 是否可用
 * jsr250Enabled ：决定 JSR-250 annotations 注解[@RolesAllowed..] 是否可用.
 * 开启 Spring Security 方法级安全注解 @EnableGlobalMethodSecurity
 */
@Configurable
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true,jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Resource
	private DataSource dataSource;
	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	@Autowired
	private UserDetailServiceImpl userDetailsService;
	
	/**
	 * web安全相关
	 * 过滤静态资源
	 */
	@Override
	public void configure(WebSecurity webSecurity) {
		//不拦截静态资源,所有用户均可访问的资源
		webSecurity.ignoring().antMatchers(
				"/",
				"/css/**",
				"/js/**",
				"/images/**",
				"/layui/**"
				);
	}

	/**
	 * 配置TokenRepository
	 * @return
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		// 配置数据源
		jdbcTokenRepository.setDataSource(dataSource);
		// 第一次启动的时候自动建表（可以不用这句话，自己手动建表，源码中有语句的）
//         jdbcTokenRepository.setCreateTableOnStartup(true);
		return jdbcTokenRepository;
	}

	/**
	 * 浏览器相关
	 * 配置http请求策略
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		//默认都会产生一个hiden标签 里面有安全相关的验证 这边我们不需要 可禁用掉
		//http.csrf().disable(); //注释就是使用 csrf 功能
		http.headers().frameOptions().disable();//解决 in a frame because it set 'X-Frame-Options' to 'DENY' 问题			
		//http.anonymous().disable();
		http.authorizeRequests()
			.antMatchers("/login/**","/initUserData")//不拦截登录相关方法		
			.permitAll()		
			//.antMatchers("/user").hasRole("ADMIN")  // user接口只有ADMIN角色的可以访问
			//.anyRequest()
			//.authenticated()// 任何尚未匹配的URL只需要验证用户即可访问
			.anyRequest()
			.access("@rbacPermission.hasPermission(request, authentication)")//根据账号权限访问			
			.and()
			.formLogin()
			.loginPage("/")
			.loginPage("/login")   //登录请求页
			.loginProcessingUrl("/login")  //登录POST请求路径
			.usernameParameter("username") //登录用户名参数
			.passwordParameter("password") //登录密码参数
			.defaultSuccessUrl("/main")   //默认登录成功页面
			.and()
			//.rememberMe()                                   // 记住我相关配置
			//.tokenRepository(persistentTokenRepository())
			//.tokenValiditySeconds(1209600)
			//.and()
			.exceptionHandling()
			.accessDeniedHandler(customAccessDeniedHandler) //无权限处理器
			.and()
			.logout()
			.logoutSuccessUrl("/login?logout");  //退出登录成功URL
			
	}

	/**
	 * 验证相关
	 * 自定义获取用户信息接口
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	/**
     * 密码加密算法
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
 
    }
}
