package top.lybysu.rpc.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xyt
 * @version 1.0
 * @date 2021/10/3 7:43 下午
 */

@Data
@Builder
public class RpcRequest implements Serializable {


    /**
     * 待调用接口名称
     */
    private String interfaceName;

    /**
     * 待调用方法名称
     */
    private String methodName;

    /**
     * 获取方法的参数
     */
    private Object[] parameters;


    /**
     * 调用方法的参数类型
     */
    private Class<?>[] paramTypes;
}
