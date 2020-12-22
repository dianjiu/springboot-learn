package co.dianjiu.mybatis.controller;


import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    //注入Druid数据库连接池对象
    @Autowired
    DruidDataSource dataSource;

    @PostMapping("/reset")
    public List<Map<String, Object>> reset(){
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        //填写一个不存在的数据库，测试用
        dataSource.setUrl("jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC\n");
        //dataSource.setDriver("这里填驱动对象");
        dataSource.setUsername("demo");
        dataSource.setPassword("123456");
        return maps;
    }

    @GetMapping("/restart")
    public List<Map<String, Object>> restart() throws SQLException {
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        dataSource.restart();
        return maps;
    }

    @GetMapping("/userList")
    public List<Map<String, Object>> userList(){
        String sql = "select * from t_user";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return maps;
    }
}
