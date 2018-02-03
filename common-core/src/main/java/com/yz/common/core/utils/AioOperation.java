package com.yz.common.core.utils;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AioOperation {
	public static byte[] readFile() throws Exception {
		Path path = Paths.get("e:\\1.txt");
		AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(path);
		final ByteBuffer buffer = ByteBuffer.allocate(1024 * 10);
		asynchronousFileChannel.read(buffer, 0, "", new CompletionHandler<Integer, Object>() {

			@Override
			public void completed(Integer result, Object attachment) {
				System.out.println("读取成功！");
			}

			@Override
			public void failed(Throwable exc, Object attachment) {
				// TODO Auto-generated method stub

			}
		});
		Thread.sleep(1000);
		return buffer.array();
	}
}
