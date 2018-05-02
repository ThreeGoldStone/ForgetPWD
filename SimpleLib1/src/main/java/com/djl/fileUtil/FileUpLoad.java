package com.djl.fileUtil;

import android.os.Handler;

import com.djl.androidutils.DJLUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author DJL E-mail:
 * @date 2016-2-19 上午10:23:22
 * @version 1.0
 * @parameter
 * @since
 */
public class FileUpLoad {

	private static final int corePoolSize = 0;
	private static final int maximumPoolSize = 5;
	private static final long keepAliveTime = 30000;
	private static final TimeUnit unit = TimeUnit.MILLISECONDS;
	public static final int File_upload_what = 55338;
	private static FileUpLoad fileUpLoad;
	private ThreadPoolExecutor mThreadPoolExecutor;

	private FileUpLoad() {
	}

	public static FileUpLoad getInstance() {
		if (fileUpLoad == null) {
			fileUpLoad = new FileUpLoad();
			fileUpLoad.init();
		}
		return fileUpLoad;
	}

	public void init() {
		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(100);
		// workQueue.
		RejectedExecutionHandler handler = new RejectedExecutionHandler() {

			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				// TODO Auto-generated method stub

			}
		};
		mThreadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
				unit, workQueue, handler);

	}

	/**
	 * post表单可以是文件或文本
	 * 
	 * @param tag
	 *            这个任务的唯一标示
	 * @param actionUrl
	 * @param boundary
	 * @param forms
	 * @param handler
	 *            handler.obtainMessage( File_upload_what, new String[] { tag,
	 *            con.getResponseCode() == 200 ? "success" : "fail", response })
	 *            .sendToTarget();
	 */
	public void addTask(String tag, String actionUrl, String boundary, ArrayList<FormBean> forms,
			Handler handler) {
		FileUpLoadRunable r = new FileUpLoadRunable(tag, actionUrl, boundary, forms, handler);
		if (!mThreadPoolExecutor.getQueue().contains(r)) {
			DJLUtils.log("addTask" + tag + true);
			mThreadPoolExecutor.execute(r);
		} else {
			DJLUtils.log("addTask" + tag + false);
		}
	}

	public static class FileUpLoadRunable implements Runnable {
		private String tag;
		private String actionUrl;
		private String boundary;
		private ArrayList<FormBean> forms;
		private Handler handler;

		public FileUpLoadRunable(String tag, String actionUrl, String boundary,
				ArrayList<FormBean> forms, Handler handler) {
			this.tag = tag;
			this.actionUrl = actionUrl;
			this.boundary = boundary;
			this.forms = forms;
			this.handler = handler;
		}

		@Override
		public void run() {
			DJLUtils.log("run>>>>>   " + tag);
			postForm(tag, actionUrl, boundary, forms, handler);
		}

		@Override
		public boolean equals(Object o) {
			FileUpLoadRunable f = (FileUpLoadRunable) o;
			return f != null && this.tag.equals(f.tag);
		}

	}

	/**
	 * post表单可以是文件或文本
	 * 
	 * @param actionUrl
	 * @param boundary
	 * @param forms
	 * @param handler
	 * @return
	 */
	public static String postForm(String tag, String actionUrl, String boundary,
			ArrayList<FormBean> forms, Handler handler) {
		String end = "\r\n";
		String Hyphens = "--";
		URL url;
		HttpURLConnection con = null;
		try {
			url = new URL(actionUrl);
			handler.obtainMessage(File_upload_what, new String[] { tag, "start", "" })
					.sendToTarget();
			con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Referer", "http://hehe");
			con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			for (int j = 0; j < forms.size(); j++) {
				FormBean fb = forms.get(j);
				// 拼接表单条目的前缀
				StringBuilder sbFormHead = new StringBuilder().append(Hyphens + boundary + end)
						.append("Content-Disposition: form-data; ");
				String[][] parameterPairs = fb.parameterPairs;
				// 遍历拼接参数
				for (int i = 0; i < parameterPairs.length; i++) {
					String[] pair = parameterPairs[i];
					sbFormHead.append(" ").append(pair[0]).append("=").append("\"").append(pair[1])
							.append("\"");
					if (i < parameterPairs.length - 1) {
						sbFormHead.append("; ");
					}
				}
				sbFormHead.append(end).append(end);
				// 写入表单条目的前缀
				ds.writeBytes(sbFormHead.toString());
				// 写入表单条目的content
				switch (fb.contentType) {
				case text:
					ds.writeBytes(fb.content);
					break;
				case file:
					writeFile(ds, fb.content);
					break;

				default:
					break;
				}
				ds.writeBytes(j < forms.size() - 1 ? end : new StringBuilder().append(end)
						.append(Hyphens + boundary).append((Hyphens + end)).toString());
			}
			InputStream is = con.getInputStream();
			int length = -1;
			byte[] cache = new byte[1024];
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			while ((length = is.read(cache)) != -1) {
				byteArray.write(cache, 0, length);
			}
			byte[] data = byteArray.toByteArray();
			String response = new String(data, "utf-8");

			handler.obtainMessage(
					File_upload_what,
					new String[] { tag, con.getResponseCode() == 200 ? "success" : "fail", response })
					.sendToTarget();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			handler.obtainMessage(File_upload_what, new String[] { tag, "fail", e.toString() })
					.sendToTarget();
			return e.toString();
		}

	}

	private static void writeFile(DataOutputStream ds, String fileName)
			throws FileNotFoundException, IOException {
		@SuppressWarnings("resource")
		FileInputStream fStream = new FileInputStream(new File(fileName));
		/* 设定每次写入1024bytes */
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		int length = -1;
		/* 从文件读取数据到缓冲区 */
		while ((length = fStream.read(buffer)) != -1) {
			/* 将数据写入DataOutputStream中 */
			ds.write(buffer, 0, length);
		}
	}

	/**
	 * 
	 * @author DJL E-mail:
	 * @date 2016-2-23 下午2:03:06
	 * @version 1.0
	 * @parameter
	 * @since
	 */
	public static class FormBean {
		public static enum ContentType {
			text, file
		};

		public ContentType contentType;
		public String content;
		public String[][] parameterPairs;

		/**
		 * 
		 * @param contentType
		 *            表单元素的类型
		 * @param content
		 *            可以是文件路径，或文本
		 * @param parameterPairs
		 *            表单条目的键值对 new String[] {key,value}
		 */
		public FormBean(ContentType contentType, String content, String[]... parameterPairs) {
			this.contentType = contentType;
			this.content = content;
			this.parameterPairs = parameterPairs;
		}

	}

}
