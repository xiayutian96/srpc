package top.lybysu.test;

import top.lybysu.rpc.api.HelloService;
import top.lybysu.rpc.netty.server.NettyServer;
import top.lybysu.rpc.registry.DefaultServiceRegistry;
import top.lybysu.rpc.registry.ServiceRegistry;
import top.lybysu.rpc.serializer.HessianSerializer;


/**
 * @author xyt
 * @date 2021/10/7
 */
public class NettyTestServer {

    public static void main(String[] args) {

        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry registry = new DefaultServiceRegistry();
        registry.register(helloService);
        NettyServer server = new NettyServer();
        server.setSerializer(new HessianSerializer());
        server.start(9999);
        System.out.println("test");
        System.out.println("test");

    }
}
