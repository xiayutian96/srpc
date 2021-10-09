package top.lybysu.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import top.lybysu.rpc.entity.RpcRequest;
import top.lybysu.rpc.enumeration.PackageType;
import top.lybysu.rpc.serializer.CommonSerializer;

/**
 * 编码拦截器
 *          魔数
 *          包类型 0表示Request 1表示Response
 *          序列化
 *          消息长度
 *          消息内容
 *
 * @author xyt
 * @date 2021/10/5
 */
public class CommonEncoder extends MessageToByteEncoder {


    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    private final CommonSerializer serializer;

    public CommonEncoder(CommonSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        out.writeInt(MAGIC_NUMBER);
        if (msg instanceof RpcRequest) {
            out.writeInt(PackageType.REQUEST_PACK.getCode());
        } else {
            out.writeInt(PackageType.RESPONSE_PACK.getCode());
        }

        out.writeInt(serializer.getCode());
        byte[] bytes = serializer.serialize(msg);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
