package com.djl.javaUtils;

import android.util.Base64;

import com.djl.androidutils.DJLUtils;

import java.io.UnsupportedEncodingException;

/**
 * 
 * @author DJL
 * @since 2015年4月15日9:48:28
 * 
 */
public class XmlAppendUtil {
	private StringBuilder hb;
	private StringBuilder bb;
	private StringBuilder xb;

	public XmlAppendUtil addHeader(String key, String value) {
		key = EscapedCharacter.change(key);
		value = EscapedCharacter.change(value);
		if (hb == null) {
			hb = new StringBuilder();
		}
		hb.append("<").append(key).append(">").append(value).append("<")
				.append("/").append(key).append(">");
		return this;

	}

	public XmlAppendUtil addBody(String key, String value) {
		key = EscapedCharacter.change(key);
		value = EscapedCharacter.change(value);
		if (bb == null) {
			bb = new StringBuilder();
		}
		bb.append("<").append(key).append(">").append(value).append("<")
				.append("/").append(key).append(">");
		return this;
	}

	public String getXml() {
		if (xb == null) {
			xb = new StringBuilder();
			xb.append("<Request>").append("<Head>").append(hb)
					.append("</Head>").append("<Body>").append(bb)
					.append("</Body>").append("</Request>");
		}
		String messageR = xb.toString();
		DJLUtils.log(messageR);
		return messageR;
	}

	public String getBase64() {
		String messageF = null;
		try {
			byte[] bytes = getXml().getBytes("UTF-8");
			messageF = Base64.encodeToString(bytes, Base64.DEFAULT);
			DJLUtils.log( "messageF");
			DJLUtils.log(messageF);
		} catch (UnsupportedEncodingException e) {
			DJLUtils.log("UnsupportedEncodingException" + e.getMessage());
			e.printStackTrace();
		}
		return messageF;

	}
}
