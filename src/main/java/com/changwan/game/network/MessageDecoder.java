package com.changwan.game.network;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.BufferDataException;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.textline.LineDelimiter;

/**
 * {@link ProtocolDecoder} 可以将字节流转换为java对象.
 * 
 */
public class MessageDecoder implements ProtocolDecoder {
	/**
	 * 上下文存储了收到的数据包
	 */
	private class Context {
		/** The temporary buffer containing the decoded line */
		private final IoBuffer buf;

		private int readPosition = 0;

		/** A counter to signal that the line is too long */
		private int writePosition = 0;

		/** Create a new Context object with a default buffer */
		private Context(int bufferLength) {
			buf = IoBuffer.allocate(bufferLength).setAutoExpand(true)
					.setAutoShrink(true);
			buf.order(ByteOrder.BIG_ENDIAN);
		}

		public void append(IoBuffer in) {
			if (writePosition != 0) {
				discard(in);
			} else if (buf.position() > messageLength - in.remaining()) {
				writePosition = buf.position();
				buf.clear();
				discard(in);
			} else {
				getBuffer().put(in);
				writePosition = in.limit();
			}

			buf.position(readPosition);
		}

		public void compact() {
			buf.compact();
			buf.clear();
			writePosition -= readPosition;
			readPosition = 0;
		}

		public IoBuffer getBuffer() {
			return buf;
		}

		public byte[] getNext() {
			int length = buf.getChar();
			byte[] data = null;
			boolean has = length <= writePosition - readPosition;

			if (has) {
				data = new byte[length];
				buf.get(data);
				readPosition = buf.position();
			}

			return data;
		}

		public boolean hasNext() {
			buf.mark();
			int length = buf.getChar();
			if (length == 0) {
				writePosition = 0;
				readPosition = 0;
				buf.reset();
				return false;
			}
			boolean has = length <= writePosition - readPosition;
			buf.reset();
			return has;
		}

		private void clean() {
			buf.position(0);
			buf.limit(0);
			buf.shrink();
			writePosition = 0;
			readPosition = 0;
		}

		private void discard(IoBuffer in) {
			if (messageLength - in.remaining() < writePosition) {
				writePosition = messageLength;
				clean();
				return;
			} else {
				writePosition += in.remaining();
			}

			in.position(in.limit());
		}
	}

	private final AttributeKey CONTEXT = new AttributeKey(getClass(), "context");

	/** 默认的最大缓存大小 102400. */
	private int maxCacheLength = 102400;

	private int messageLength = maxCacheLength * 10;

	private char[] recvMap = new char[] {
			0x87, 0x2F, 0x54, 0xF3, 0x38, 0x45, 0x18, 0x86, 0xE6, 0x14, 0xAD,
			0x50, 0xAA, 0x3A, 0x94, 0xF2, 0xEF, 0x3B, 0xCC, 0xC2, 0x07, 0x44,
			0x7A, 0x6F, 0xA1, 0x9C, 0xC8, 0x51, 0x11, 0xA6, 0x7D, 0xED, 0xAE,
			0xC6, 0xD1, 0xEA, 0xDC, 0x2C, 0xC4, 0xDA, 0x13, 0x15, 0xE7, 0x78,
			0xA9, 0x60, 0x02, 0xF0, 0x68, 0xDD, 0x1F, 0x92, 0x6A, 0x76, 0x47,
			0x25, 0x2E, 0xFF, 0x01, 0x7C, 0xCF, 0x5F, 0xB0, 0x89, 0x9E, 0x88,
			0x23, 0xA4, 0x0A, 0x58, 0xB1, 0x42, 0x67, 0xDE, 0xD8, 0xF1, 0x00,
			0x6C, 0x7F, 0x03, 0xB4, 0x62, 0x9D, 0x8D, 0x40, 0x8E, 0xA3, 0x37,
			0xC9, 0x29, 0x1B, 0x55, 0x82, 0x5E, 0x1A, 0x96, 0x7E, 0xE3, 0x93,
			0x56, 0x24, 0x49, 0x33, 0x65, 0xF6, 0xF8, 0x20, 0x90, 0x2A, 0x53,
			0x0D, 0x73, 0x6B, 0x48, 0x26, 0xA7, 0x22, 0xD7, 0x6D, 0xBA, 0x8B,
			0xFC, 0xB5, 0x8F, 0x98, 0xB6, 0x21, 0x3F, 0xBF, 0x04, 0xD2, 0xD5,
			0xD6, 0xCE, 0x30, 0xBB, 0xCA, 0xE2, 0x64, 0x1D, 0x70, 0xD4, 0xCB,
			0x17, 0x75, 0x91, 0xB7, 0x80, 0xFA, 0xC7, 0xA0, 0x28, 0x9F, 0x5D,
			0xFE, 0x99, 0xE1, 0x0F, 0x97, 0x4D, 0xFD, 0x79, 0x36, 0x59, 0x84,
			0xF7, 0x08, 0xC5, 0x39, 0xDF, 0x63, 0xA5, 0xFB, 0x61, 0x7B, 0xD0,
			0xA8, 0xBE, 0x5B, 0x1C, 0xE8, 0x81, 0x9B, 0x77, 0x32, 0xF4, 0xCD,
			0x8C, 0x31, 0x35, 0xC0, 0x6E, 0x57, 0x27, 0xE4, 0x12, 0x4A, 0x71,
			0x43, 0xB9, 0xB8, 0x3E, 0x0C, 0xC1, 0xE0, 0x0B, 0x5C, 0x72, 0x4C,
			0x0E, 0x16, 0x4B, 0x66, 0x2B, 0xB3, 0x10, 0x4F, 0x1E, 0xD3, 0x69,
			0xD9, 0x3D, 0x74, 0x3C, 0xE5, 0xAF, 0xF9, 0x06, 0xEC, 0xF5, 0xB2,
			0xE9, 0x05, 0x41, 0xBD, 0xEE, 0x2D, 0x46, 0x9A, 0x34, 0x8A, 0xA2,
			0xAC, 0xDB, 0x19, 0x83, 0x52, 0x95, 0x4E, 0xBC, 0x09, 0xAB, 0x5A,
			0xEB, 0x85, 0xC3 };

	private int step = 7;

	/**
	 * Creates a new instance with the current default {@link Charset} and
	 * {@link LineDelimiter#AUTO} delimiter.
	 */
	public MessageDecoder() {
	}

	/**
	 * {@inheritDoc}
	 */
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
			throws Exception {
		Context ctx = getContext(session);
		// initialize
		in.clear();
		ctx.append(in);
		while (ctx.hasNext()) {
			writeMessage(session, ctx.getNext(), out);
		}
		ctx.compact();
	}

	/**
	 * {@inheritDoc}
	 */
	public void dispose(IoSession session) throws Exception {
		Context ctx = (Context) session.getAttribute(CONTEXT);

		if (ctx != null) {
			session.removeAttribute(CONTEXT);
		}
	}

	@Override
	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1)
			throws Exception {
		// do nothing
	}

	/**
	 * Returns the allowed maximum size of the line to be decoded. If the size
	 * of the line to be decoded exceeds this value, the decoder will throw a
	 * {@link BufferDataException}. The default value is <tt>102400</tt>
	 * (100KB).
	 */
	public int getMaxCacheLength() {
		return maxCacheLength;
	}

	/**
	 * Sets the allowed maximum size of the line to be decoded. If the size of
	 * the line to be decoded exceeds this value, the decoder will throw a
	 * {@link BufferDataException}. The default value is <tt>102400</tt>
	 * (100KB).
	 */
	public void setMaxCacheLength(int maxCacheLength) {
		if (maxCacheLength <= 0) {
			throw new IllegalArgumentException("maxLineLength ("
					+ maxCacheLength + ") should be a positive value");
		}

		this.maxCacheLength = maxCacheLength;
	}

	/**
	 * Return the context for this session
	 */
	private Context getContext(IoSession session) {
		Context ctx;
		ctx = (Context) session.getAttribute(CONTEXT);

		if (ctx == null) {
			ctx = new Context(maxCacheLength);
			session.setAttribute(CONTEXT, ctx);
		}

		return ctx;
	}

	protected byte[] transform(IoSession session, byte[] data) {
		int counter = 0;
		if (session.containsAttribute("recvPos")) {
			counter = (Integer) session.getAttribute("recvPos");
		}

		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) (recvMap[((short) data[i]) & 0xFF] - counter);
			counter += step;
		}

		session.setAttribute("recvPos", counter);
		return data;
	}

	/**
	 * By default, this method propagates the decoded line of text to
	 * {@code ProtocolDecoderOutput#write(Object)}. You may override this method
	 * to modify the default behavior.
	 * 
	 * @param session
	 *            the {@code IoSession} the received data.
	 * @param text
	 *            the decoded text
	 * @param out
	 *            the upstream {@code ProtocolDecoderOutput}.
	 */
	protected void writeMessage(IoSession session, byte[] data,
			ProtocolDecoderOutput out) {
		transform(session, data);
		out.write(data);
	}
}