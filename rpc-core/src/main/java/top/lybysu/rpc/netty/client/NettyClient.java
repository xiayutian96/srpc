package top.lybysu.rpc.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lybysu.rpc.RpcClient;
import top.lybysu.rpc.codec.CommonDecoder;
import top.lybysu.rpc.codec.CommonEncoder;
import top.lybysu.rpc.entity.RpcRequest;
import top.lybysu.rpc.entity.RpcResponse;
import top.lybysu.rpc.enumeration.RpcError;
import top.lybysu.rpc.exception.RpcException;
import top.lybysu.rpc.serializer.CommonSerializer;
import top.lybysu.rpc.util.RpcMessageChecker;

/**
 * @author xyt
 * @date 2021/10/4 10:15 下午
 */
public class NettyClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private static final Bootstrap bootstrap;

    private CommonSerializer serializer;

    static {

        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true);
    }

    private String host;
    private int port;


    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {

        if (serializer == null) {
            logger.error("未设置序列化器");//静态变量之间传进来的
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new CommonDecoder())
                    .addLast(new CommonEncoder(serializer))
                    .addLast(new NettyClientHandler());
            }
        });
        try {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            logger.info("客户端连接到服务器 {} : {}", host, port);
            Channel channel = future.channel();
            if (channel != null) {
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if (future1.isSuccess()) {
                        logger.info(String.format("客户端发送消息: %s", rpcRequest.toString()));
                    } else {
                        logger.error("发送消息时有错误发生: ", future1.cause());
                    }
                });
                channel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse" + rpcRequest.getRequestId());
                RpcResponse rpcResponse = channel.attr(key).get();
                RpcMessageChecker.check(rpcRequest, rpcResponse);
                return rpcResponse.getData();
            }
        } catch (InterruptedException e) {
            logger.error("发送消息时有错误发生: ", e);
        }
        return null;
    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}
