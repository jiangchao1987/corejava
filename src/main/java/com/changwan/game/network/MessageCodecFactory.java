package com.changwan.game.network;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * {@link ProtocolCodecFactory} 用来处理二进制字节流与 Java 对象之间转换的一个过滤器.  
 * 本编解码器是它的一个子类，用来将收到的数据包分组，并且按照 map 进行字节转换
 */
public class MessageCodecFactory implements ProtocolCodecFactory {

    private final MessageEncoder encoder;

    private final MessageDecoder decoder;

    /**
     * 创建解码器对象
     */
    public MessageCodecFactory() {
        encoder = new MessageEncoder();
        decoder = new MessageDecoder();
    }

    public ProtocolEncoder getEncoder(IoSession session) {
        return encoder;
    }

    public ProtocolDecoder getDecoder(IoSession session) {
        return decoder;
    }

    /**
     * 返回编码器可以容纳的最大缓存字节数
     * 默认值是 {@link Integer#MAX_VALUE}.
     * <p>
     * 同 {@link MessageEncoder#getMaxCacheLength()}.
     */
    public int getEncoderMaxCacheLength() {
        return encoder.getMaxCacheLength();
    }

    /**
     * 设置编码器可以容纳的最大缓存字节数
     * 默认值是 {@link Integer#MAX_VALUE}.
     * <p>
     * 同 {@link MessageEncoder#setMaxCacheLength(int)}.
     */
    public void setEncoderMaxCacheLength(int maxCacheLength) {
        encoder.setMaxCacheLength(maxCacheLength);
    }

    /**
     * 返回解码器可以容纳的最大缓存字节数
     * 默认值是 <tt>1024</tt> (1KB).
     * <p>
     * 同  {@link MessageDecoder#getMaxCacheLength()}.
     */
    public int getDecoderMaxCacheLength() {
        return decoder.getMaxCacheLength();
    }

    /**
     * 设置解码器可以容纳的最大缓存字节数
     * 默认值是 <tt>1024</tt> (1KB).
     * <p>
     * 同 {@link MessageDecoder#setMaxCacheLength(int)}.
     */
    public void setDecoderMaxCacheLength(int maxCacheLength) {
        decoder.setMaxCacheLength(maxCacheLength);
    }
}
