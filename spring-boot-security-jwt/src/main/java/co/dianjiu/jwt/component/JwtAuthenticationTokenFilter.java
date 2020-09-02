package co.dianjiu.jwt.component;

import co.dianjiu.jwt.common.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT登录授权过滤器
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        //从请求头中获取Authorization属性值
        String authHeader = request.getHeader(this.tokenHeader);
        //token的合法性验证
        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
            // 取出真正的token值 The part after "dianjiu "
            String authToken = authHeader.substring(this.tokenHead.length());
            // 通过token工具类获取用户名
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            logger.info("checking username:{}", username);
            // 用户名不为空，且全局无授权信息时，执行下面的鉴权操作
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                //通过用户名去数据库查询用户信息
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                // TODO 这个if校验用户名是否相等，token是否过期
                /**
                 * 这个if校验用户名好像有问题
                 * 传入的userDetails是通过token中取到的用户名去数据库查到的
                 * 然后在用userDetails中的用户名和token中取到的用户名去比较 意义何在？
                 */
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.info("authenticated user:{}", username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}