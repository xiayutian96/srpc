package top.lybysu.test;

import top.lybysu.rpc.api.HelloObject;
import top.lybysu.rpc.api.HelloService;
import top.lybysu.rpc.client.RpcClientProxy;

/**
 * @author xyt
 * @date 2021/10/3 9:55 下午
 */
public class TestClient {
    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }
}
