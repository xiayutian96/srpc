package top.lybysu.rpc;

import top.lybysu.rpc.serializer.CommonSerializer;

/**
 * @author xyt
 * @date 2021/10/5
 */
public interface RpcServe {

    /**
     * 启动
     * @param port
     */
    void start(int port);

    /**
     * 设置序列化
     * @param serializer
     */
    void setSerializer(CommonSerializer serializer);
}
