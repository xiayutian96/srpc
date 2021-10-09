package top.lybysu.test;

import top.lybysu.rpc.RpcClient;
import top.lybysu.rpc.RpcClientProxy;
import top.lybysu.rpc.api.HelloObject;
import top.lybysu.rpc.api.HelloService;
import top.lybysu.rpc.netty.client.NettyClient;
import top.lybysu.rpc.serializer.HessianSerializer;

/**
 * @author xyt
 * @date 2021/10/7
 */
public class NettyTestClient {

    public static void main(String[] args) {
        RpcClient client = new NettyClient("127.0.0.1", 9999);
        client.setSerializer(new HessianSerializer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
    }



}
