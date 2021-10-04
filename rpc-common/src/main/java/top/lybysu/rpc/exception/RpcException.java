package top.lybysu.rpc.exception;

import top.lybysu.rpc.enumeration.RpcError;

/**
 * @author xyt
 * @date 2021/10/3 8:20 下午
 */
public class RpcException extends RuntimeException {

    public RpcException(RpcError error, String details) {
        super(error.getMessage() + ": " + details);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcError error) {
        super(error.getMessage());

    }
}
