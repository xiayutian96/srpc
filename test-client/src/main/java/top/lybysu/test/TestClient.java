package top.lybysu.test;

import top.lybysu.rpc.RpcClientProxy;
import top.lybysu.rpc.api.HelloObject;
import top.lybysu.rpc.api.HelloService;
import top.lybysu.rpc.serializer.HessianSerializer;
import top.lybysu.rpc.serializer.KryoSerializer;
import top.lybysu.rpc.socket.client.SocketClient;

/**
 * @author xyt
 * @date 2021/10/3 9:55 下午
 */
public class TestClient {
    public static void main(String[] args) {
        SocketClient client = new SocketClient("127.0.0.1", 9999);
        client.setSerializer(new HessianSerializer());
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
