package top.lybysu.rpc;


import top.lybysu.rpc.entity.RpcRequest;
import top.lybysu.rpc.serializer.CommonSerializer;

/**
 * 客户端通用接口
 */
public interface RpcClient {

    /**
     * 发送rpcRequest请求
     * @param rpcRequest
     * @return
     */
    Object sendRequest(RpcRequest rpcRequest);

    /**
     * 设置序列化
     * @param serializer
     */
    void setSerializer(CommonSerializer serializer);


}


