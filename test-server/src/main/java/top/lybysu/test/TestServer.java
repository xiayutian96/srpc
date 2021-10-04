package top.lybysu.test;

import top.lybysu.rpc.api.HelloService;
import top.lybysu.rpc.registry.DefaultServiceRegistry;
import top.lybysu.rpc.server.RpcServer;

/**
 * @author xyt
 * @date 2021/10/3 10:04 下午
 */
public class TestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        DefaultServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        RpcServer rpcServer = new RpcServer(serviceRegistry);
        rpcServer.start(9000);

    }
}
