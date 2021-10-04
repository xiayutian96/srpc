package top.lybysu.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lybysu.rpc.api.HelloObject;
import top.lybysu.rpc.api.HelloService;

/**
 * @author xyt
 * @version 1.0
 * @date 2021/10/3 7:23 下午
 */
public class HelloServiceImpl implements HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("接收到：{}", object.toString());
        return "这是调用返回值，id=" + object.getId();
    }
}
