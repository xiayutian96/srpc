package top.lybysu.rpc.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lybysu.rpc.enumeration.RpcError;
import top.lybysu.rpc.exception.RpcException;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xyt
 * @date 2021/10/3 9:04 下午
 */
public class DefaultServiceRegistry implements ServiceRegistry {

    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceRegistry.class);

    private static final Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    private static final Set<String> registeredService = ConcurrentHashMap.newKeySet();



    @Override
    public synchronized <T> void register(T service) {
        //getCanonicalName()是获取所传类从java语言规范定义的格式输出
        String serviceName = service.getClass().getCanonicalName();
        if (registeredService.contains(serviceName)) {
            return;
        }
        registeredService.add(serviceName);
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if (interfaces.length == 0) {
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        }
        for (Class<?> i : interfaces) {
            serviceMap.put(i.getCanonicalName(), service);
        }
        logger.info("向接口：{} 注册服务: {}", interfaces, serviceName);
    }

    @Override
    public Object getService(String serviceName) {

        logger.info(String.valueOf(serviceMap.size()));

        Object service = serviceMap.get(serviceName);
        if (service == null) {
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        return service;
    }
}
