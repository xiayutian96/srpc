package top.lybysu.rpc.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.lybysu.rpc.RpcServe;
import top.lybysu.rpc.codec.CommonDecoder;
import top.lybysu.rpc.codec.CommonEncoder;
import top.lybysu.rpc.enumeration.RpcError;
import top.lybysu.rpc.exception.RpcException;
import top.lybysu.rpc.serializer.CommonSerializer;

/**
 * NIO方式提供服务侧
 *
 * @author xyt
 * @date 2021/10/5
 */
public class NettyServer implements RpcServe {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private CommonSerializer serializer;

    @Override
    public void start(int port) {

        if (serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();//bossGroup负责处理TCP/IP连接
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();//workerGroup负责处理Channel的I/O时间

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    //option和handler是给bossGroup设置的
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //指定队列的大小
                    .option(ChannelOption.SO_BACKLOG, 256)
                    //该参数用于设置TCP连接，当设置该选项后，会测试连接的状态，当两个小时没有数据探查后，TCP会自动发一个活动探测数据
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    //childOption和childHandler是为workerGroup设置的
                    //禁用NAGLE算法，确保低延迟
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new CommonEncoder(serializer));
                            pipeline.addLast(new CommonDecoder());
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(port).sync();
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            logger.error("启动服务时有错误发生: ", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}
