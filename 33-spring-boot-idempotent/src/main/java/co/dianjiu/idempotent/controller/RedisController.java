package co.dianjiu.idempotent.controller;


import co.dianjiu.idempotent.pojo.ModelMap;
import co.dianjiu.idempotent.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Slf4j
@Controller
@RequestMapping(value="/redis")
public class RedisController {
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 默认最大链接数为8个
     * 优化为最大连接数为10个
     * @return
     */
    @RequestMapping(value="/testMaxTotal")
    @ResponseBody
    public String testRedisMaxTotal(){
        try {
            for(int i=0;i<15;i++) {
                Jedis jedis = jedisPool.getResource();
                System.out.println(jedis);
                jedis.close();
            }
        } catch (Exception e) {
            log.error("获取连接池时发生异常："+e.getMessage());
        }
        return "result";
    }


    /**
     * @Description: 执行redis写/读/生命周期
     */
    @RequestMapping(value = "getRedis",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap getRedis(){
        redisUtil.set("20182018","这是一条测试数据", 1);
        Long resExpire = redisUtil.expire("20200418", 60, 1);//设置key过期时间
        log.info("resExpire="+resExpire);
        String resp = redisUtil.get("20182018", 1);
        return new ModelMap(200,"执行成功",resp);
    }

}
