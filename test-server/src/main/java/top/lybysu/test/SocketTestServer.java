package top.lybysu.test;

import top.lybysu.rpc.api.HelloService;
import top.lybysu.rpc.registry.DefaultServiceRegistry;
import top.lybysu.rpc.registry.ServiceRegistry;
import top.lybysu.rpc.serializer.KryoSerializer;
import top.lybysu.rpc.socket.server.SocketServer;

/**
 * @author xyt
 * @date 2021/10/3 10:04 下午
 */
public class SocketTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        SocketServer socketServer = new SocketServer(serviceRegistry);
        socketServer.setSerializer(new KryoSerializer());
        socketServer.start(9999);

    }
}
