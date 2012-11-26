package net.downtools;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 多线程下载
 * 
 * @author zhaojp
 */
public class HttpMultiThreadDownload {
	public static final int DEFAULT_TASKNUM = 5;// 默认线程数
	public static final int DEFAULT_PART_SIZE = 1024 * 1024 * 10;// 默认拆分文件长度
	public static final File DEFAULT_TARGET = null;// 默认下载文件输出位置
	public static final String DEFAULT_DIR = "D:/temp";// 下载文件默认保存目录

	private LinkedList<Range> ranges = new LinkedList<Range>();// 任务列表
	private LinkedList<Range> datas = new LinkedList<Range>();// 下载下来的数据列表
	private Map<String, String> headers = null;// 头信息

	private ExecutorService executor = null;// 线程池
	private RandomAccessFile randomfile = null;// 随机访问文件器
	private URL url = null;// 下载文件的URL对象
	private int partsize;// 拆分文件长度
	private long length;// 文件长度

	static {
		File dir = new File(DEFAULT_DIR); // 初始化下载文件保存记录

		// 判断指定目录是否存在 如果不存在则创建
		if (!dir.exists()) {
			dir.mkdir();
		}
	}

	/**
	 * 下载指定文件
	 * 
	 * @param url
	 *            文件所在URL
	 * @throws IOException
	 */
	public HttpMultiThreadDownload(String url) throws IOException {
		this(url, DEFAULT_TASKNUM);
	}

	/**
	 * 下载指定文件
	 * 
	 * @param url
	 *            文件所在URL
	 * @param tasknum
	 *            线程总数
	 * @throws IOException
	 */
	public HttpMultiThreadDownload(String url, int tasknum) throws IOException {
		this(url, tasknum, DEFAULT_PART_SIZE, DEFAULT_TARGET);
	}

	/**
	 * 下载指定文件
	 * 
	 * @param url
	 *            文件所在URL
	 * @param tasknum
	 *            线程总数
	 * @param partsize
	 *            任务字节大小
	 * @param target
	 *            输出位置
	 * @throws IOException
	 */
	public HttpMultiThreadDownload(String url, int tasknum, int partsize, File target) throws IOException {
		this.url = new URL(url);
		this.executor = Executors.newFixedThreadPool(tasknum + 1);
		this.partsize = partsize;
		if (target != null) {
			if (target.exists()) {
				target.delete();
			}
			try {
				this.randomfile = new RandomAccessFile(target, "rw");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 初始化下载对象
	 * 
	 * @throws IOException
	 */
	public void init() throws IOException {
		getHeader();// 获得头信息

		// 判断是否能够从头信息中获取下载对象的长度 如果无法获得则抛出异常
		if (headers.containsKey("Content-Length")) {
			length = Long.parseLong(headers.get("Content-Length"));
		} else {
			throw new RuntimeException("can't get content-length");
		}

		// 如果初始化的时候没有指定输出位置 则通过头信息或者URL获取文件名称
		if (randomfile == null) {
			String path = null;
			if (headers.containsKey("Content-Disposition")) {
				path = headers.get("Content-Disposition");
			} else {
				if (url.getFile() != null && url.getFile().trim().indexOf("/") != -1) {
					path = url.getFile().substring(url.getFile().lastIndexOf("/") + 1);
				}
			}
			if (path == null || path.trim().equals("")) {
				throw new RuntimeException("File name is empty");
			}
			String filename = DEFAULT_DIR + "/" + path;

			File file = new File(filename);
			if (file.exists()) {
				file.delete();
			}

			randomfile = new RandomAccessFile(filename, "rw");
			randomfile.setLength(length);
		}

		ranges = getRanges(Long.parseLong(headers.get("Content-Length")));// 根据文件长度拆分成不同片段的任务
	}

	/**
	 * 启动任务
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException {
		init();// 初始化

		// 启动内容写入文件任务 并且获得Future对象
		Future<Boolean> result = executor.submit(new WriteTask(ranges.size()));

		for (int i = 0; i < DEFAULT_TASKNUM; i++) {
			executor.submit(new Task());// 初始化下载任务
		}
		executor.shutdown();// 停止注册任务

		try {
			result.get();// 在写入文件任务结束之前阻塞主线程
		} catch (InterruptedException e) {} catch (ExecutionException e) {}
	}

	/**
	 * 根据文件内容长度将文件分成多个片段
	 * 
	 * @param contentLength
	 *            文件内容长度
	 * @return 片段任务列表
	 */
	public LinkedList<Range> getRanges(long contentLength) {
		LinkedList<Range> ranges = new LinkedList<Range>();// 创建一个片段列表

		long last = contentLength;
		long start = 0;

		// 根据length来分下载任务
		while (true) {
			if (last - partsize > 0) {
				ranges.add(new Range(start, start + partsize));
				last -= partsize;
				start += partsize;
				start++;
			} else {
				ranges.add(new Range(start, contentLength));
				break;
			}
		}
		return ranges;
	}

	/**
	 * 获取头信息Map
	 * 
	 * @return java.util.Map&lt;String, String&gt; 头信息字典
	 */
	public Map<String, String> getHeader() {
		if (headers == null) {
			try {
				Map<String, List<String>> h = url.openConnection().getHeaderFields();// 获取原始头信息
				headers = new HashMap<String, String>();// 创建头信息

				for (Iterator<String> it = h.keySet().iterator(); it.hasNext();) {
					String key = it.next();
					headers.put(key, h.get(key).get(0));// 将原始头信息赋值给头信息
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return headers;
	}

	/**
	 * 写入任务
	 * 
	 * @author zhaojp
	 */
	class WriteTask implements Callable<Boolean> {
		private int count;// 片段数量
		private int cbytes;// 已经写入的内容产度

		/**
		 * 创建任务对象
		 * 
		 * @param count
		 *            片段数量
		 */
		WriteTask(int count) {
			this.count = count;// 初始化片段数量
		}

		@Override
		public Boolean call() throws Exception {
			// 循环获取datas中的下载完成片段
			while (true) {
				Range range = null;

				synchronized (datas) {
					// 判断已经完成的片段是否为空
					if (datas.isEmpty()) {
						// 如果为空则查看是否有剩余未完成的字段 如果有 则挂起 如果没有 则break 结束循环
						if (count == 0) {
							break;
						} else {
							try {
								datas.wait();// 挂起线程
								continue;
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
					count--;// 减少片段数量
					range = datas.removeFirst();// 移除下载完成队列中的第一个下载完成片段
				}
				try {
					byte[] data = range.getData();
					cbytes += data.length;// 累计完成字节

					randomfile.seek(range.getStart());// 将randomfile的指针seek到指定位置
					randomfile.write(data, 0, data.length);// 写入数据
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			try {
				randomfile.close();// 关闭读写流

				return true;
			} catch (IOException e) {
				e.printStackTrace();

				return false;
			}
		}
	}

	/**
	 * 下载任务
	 * 
	 * @author zhaojp
	 */
	class Task implements Runnable {
		@Override
		public void run() {
			// 做一个循环 不断的去获取片段下载任务
			while (true) {
				Range range = null;

				// 做一个同步 从任务列表中获取片段下载任务 如果没有任务则break
				synchronized (ranges) {
					if (ranges.isEmpty()) {
						break;
					}
					range = ranges.removeFirst();// 移除队列中第一个任务
				}
				Socket socket = null;
				try {
					String host = url.getHost();
					Integer port = url.getPort() > -1 ? url.getPort() : 80;

					socket = new Socket(host, port);// 创建客户端套接字

					BufferedInputStream in = new BufferedInputStream(socket.getInputStream());// 创建输入流
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));// 创建字符输出流

					writer.write(range.getMessage());// 发送HTTP报文
					writer.flush();// 清理缓存

					StringBuilder header = new StringBuilder();
					char c;

					while ((c = (char) in.read()) != -1) {
						header.append(c);
						if (header.lastIndexOf("\r\n\r\n") != -1) {
							break;
						}
					}
					range.read(in);// 将内容读取到下载完成片段对象中

					synchronized (datas) {
						datas.add(range); // 将下载完成片段对象添加到下载完成任务中
						datas.notifyAll();// 唤醒等待在该对象上的线程
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					// 关闭套接字
					if (socket != null) {
						try {
							socket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * 下载片段对象
	 * 
	 * @author zhaojp
	 */
	class Range {
		private static final int DEFAULT_BUFFER = 1024;

		private String message;// HTTP报文
		private long start;// 范围起始
		private long end;// 范围结束

		private ByteArrayOutputStream out;// 内存输出流

		/**
		 * 创建下载任务对象
		 * 
		 * @param start
		 *            范围起始
		 * @param end
		 *            范围结束
		 */
		public Range(long start, long end) {
			// 创建HTTP报文
			StringBuilder message = new StringBuilder();

			message.append("GET ").append(url.getPath()).append(" HTTP/1.1\r\n");
			message.append("Range: bytes=").append(start).append("-").append(end).append("\r\n");
			message.append("Host: ").append(url.getHost()).append("\r\n\r\n");

			this.start = start;
			this.end = end;
			this.message = message.toString();
		}

		/**
		 * 将输入流中的内容读取到内存输出流里
		 * 
		 * @param in
		 *            输入流
		 */
		public void read(BufferedInputStream in) {
			out = new ByteArrayOutputStream();// 创建内存输出流
			try {
				byte[] buffer = new byte[partsize < DEFAULT_BUFFER ? partsize : DEFAULT_BUFFER];// 创建缓存
				int i = 0;// 偏移
				while ((i = in.read(buffer)) != -1) {
					out.write(buffer, 0, i);
				}
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public String getMessage() {
			return message;
		}

		public String toString() {
			return start + "-" + end;
		}

		public long getStart() {
			return start;
		}

		public void setStart(long start) {
			this.start = start;
		}

		public long getEnd() {
			return end;
		}

		public void setEnd(long end) {
			this.end = end;
		}

		public byte[] getData() {
			return out.toByteArray();
		}
	}

	public static void main(String[] args) throws IOException {
		HttpMultiThreadDownload download = new HttpMultiThreadDownload("http://localhost:8080/tomcat.gif", 2, 512, null);

		download.start();
	}
}
