package top.lybysu.rpc.exception;

/**
 * 序列化异常
 * @author xyt
 * @date 2021/10/7
 */
public class SerializeException extends RuntimeException {
    public SerializeException(String msg) {
        super(msg);
    }
}
