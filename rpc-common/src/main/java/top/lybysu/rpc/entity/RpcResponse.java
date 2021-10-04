package top.lybysu.rpc.entity;

import lombok.Data;
import top.lybysu.rpc.enumeration.ResponseCode;

import java.io.Serializable;

/**
 * @author xyt
 * @version 1.0
 * @date 2021/10/3 7:46 下午
 */
@Data
public class RpcResponse<T> implements Serializable {

    /**
     * 响应状态码
     */
    private Integer statusCode;


    /**
     * 响应状态补充信息
     */
    private String message;


    /**
     * 响应数据
     */
    private T data;

    public static <T> RpcResponse<T> success(T data) {
        RpcResponse<T> responce = new RpcResponse<>();
        responce.setStatusCode(ResponseCode.SUCCESS.getCode());
        responce.setData(data);
        return responce;
    }

    public static <T> RpcResponse<T> fail(ResponseCode code) {
        RpcResponse<T> responce = new RpcResponse<>();
        responce.setStatusCode(code.getCode());
        responce.setMessage(code.getMessage());
        return responce;
    }

}
