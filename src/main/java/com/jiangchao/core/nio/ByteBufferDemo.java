package com.jiangchao.core.nio;

import java.nio.ByteBuffer;

// clear 清空缓冲区  mark limit pos全部回归初始
// limit 限定缓冲区
// position 位置指针
// mark 标记位置
// reset 回到mark处
public class ByteBufferDemo {

	public static void main(String[] args) {
		ByteBuffer buf = ByteBuffer.allocate(10);
		buf.put("HelloWorld".getBytes());
		System.out.printf("Capacity: %d, position: %d, limit: %d, buf: %s\n", buf.capacity(), buf.position(), buf.limit(), new String(buf.array()));
		buf.clear();
		System.out.printf("Capacity: %d, position: %d, limit: %d, buf: %s\n", buf.capacity(), buf.position(), buf.limit(), new String(buf.array()));
		buf.limit(5);
		System.out.printf("Capacity: %d, position: %d, limit: %d, buf: %s\n", buf.capacity(), buf.position(), buf.limit(), new String(buf.array()));
		buf.clear();
		buf.position(3);
		buf.mark();
		System.out.printf("Capacity: %d, position: %d, limit: %d, buf: %s\n", buf.capacity(), buf.position(), buf.limit(), new String(buf.array()));
		ByteBuffer buf2 = buf.slice();
		buf2.put((byte)0x73);
		System.out.printf("Capacity: %d, position: %d, limit: %d, buf: %s\n", buf2.capacity(), buf2.position(), buf2.limit(), new String(buf2.array()));
		buf2.clear();
//		ByteBuffer buf3 = ByteBuffer.wrap(buf.get(buf.array(), 3, 7).array());
		ByteBuffer buf3 = ByteBuffer.allocate(7).put(buf2);
		System.out.printf("Capacity: %d, position: %d, limit: %d, buf: %s\n", buf3.capacity(), buf3.position(), buf3.limit(), new String(buf3.array()));
	}
	
}
