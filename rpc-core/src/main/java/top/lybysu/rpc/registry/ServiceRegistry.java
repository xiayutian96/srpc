package top.lybysu.rpc.registry;


/**
 * 服务注册表通用接口
 */
public interface ServiceRegistry {

    /**
     * 将一个服务注册进注册表
     * @param service 待注册的服务实体
     * @param <T> 服务实体类
     */
    <T> void register(T service);

    /**
     * 根据服务名获取服务实体
     * @param serviceName
     * @return
     */
    Object getService(String serviceName);

}
