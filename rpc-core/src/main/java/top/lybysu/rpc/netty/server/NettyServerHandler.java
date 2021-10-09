package top.lybysu.rpc.netty.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lybysu.rpc.entity.RpcRequest;
import top.lybysu.rpc.entity.RpcResponse;
import top.lybysu.rpc.registry.DefaultServiceRegistry;
import top.lybysu.rpc.registry.ServiceRegistry;
import top.lybysu.rpc.RequestHandler;

/**
 *
 * Netty中处理RpcRequest的Handler
 * @author xyt
 * @date 2021/10/5
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    private static RequestHandler requestHandler;
    private static ServiceRegistry serviceRegistry;

    static {
        requestHandler = new RequestHandler();
        serviceRegistry = new DefaultServiceRegistry();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        try {
            logger.info("服务器收到请求:{}", msg);
            String interfaceName = msg.getInterfaceName();

            Object service = serviceRegistry.getService(interfaceName);
            Object result = requestHandler.handle(msg, service);
            ChannelFuture future = ctx.writeAndFlush(RpcResponse.success(result, msg.getRequestId()));
            future.addListener(ChannelFutureListener.CLOSE);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        logger.error("处理过程调用时有错误发生");
        cause.printStackTrace();
        ctx.close();
    }
}
