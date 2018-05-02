package com.djl.javaUtils;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @author DJL E-mail:
 * @date 2015-6-16 上午9:33:48
 * @version 1.0
 * @parameter
 * @since
 */
public class EscapedCharacter {
	public static HashMap<String, String> map;

	static String change(String s) {
		if (s == null) {
			return null;
		}
		s = s.replace("&", "&amp");
		for (Entry<String, String> set : map.entrySet()) {
			s = s.replace(set.getKey(), set.getValue());
		}
		return s;

	}

	static String back(String s) {
		if (s == null) {
			return null;
		}

		for (Entry<String, String> set : map.entrySet()) {
			s = s.replace(set.getValue(), set.getKey());
		}
		s = s.replace("&amp", "&");
		return s;

	}

	static {
		if (map == null) {
			map = new HashMap<>();
			// // & & &amp; Ampersand
			// map.put("&", "&amp");
			// " 	" ; &quot; 双引号Quotation mark
			map.put("\"", "&quot");
			// < < &lt; 小于号Less than
			map.put("<", "&lt");
			// > > &gt; 大于号Greater than
			map.put(">", "&gt");
			// &nbsp; 空格Nonbreaking space
			map.put(" ", "&nbsp");
		}
		// // ? ? &iexcl; Inverted exclamation
		// map.put("?", "&iexcl");
		// // ? ? &cent; 货币分标志Cent sign
		// map.put("?", "&iexcl");
		// // ? ? &pound; 英镑标志Pound sterling
		// map.put("?", "&iexcl");
		// // ¤ ¤ &curren ; 通用货币标志General currency sign
		// map.put("?", "&iexcl");
		// // ? ? &yen; 日元标志Yen sign
		// map.put("?", "&iexcl");
		// // ? ? &brvbar; or &brkbar; 断竖线Broken vertical bar
		// map.put("?", "&iexcl");
		// // § § &sect; 分节号Section sign
		// map.put("?", "&iexcl");
		// // ¨ ¨ &uml ; or &die; 变音符号Umlaut
		// map.put("?", "&iexcl");
		// // ? ? &copy ; 版权标志Copyright
		// map.put("?", "&iexcl");
		// // ? ? &ordf ; Feminine ordinal
		// map.put("?", "&iexcl");
		// // ? ? &laquo; Left angle quote, guillemet left
		// map.put("?", "&iexcl");
		// // ? ? &not Not sign
		// map.put("?", "&iexcl");
		// // &shy; Soft hyphen
		// map.put("?", "&iexcl");
		// // ? ? &reg; 注册商标标志Registered trademark
		// map.put("?", "&iexcl");
		// // ? ? &macr; or &hibar ; 长音符号Macron accent
		// map.put("?", "&iexcl");
		// // ° ° &deg ; 度数标志Degree sign
		// map.put("?", "&iexcl");
		// // ± ± &plusmn ; 加或减Plus or minus
		// map.put("?", "&iexcl");
		// // ? ? &sup2; 上标2 Superscript two
		// map.put("?", "&iexcl");
		// // ? ? &sup3 ; 上标3 Superscript three
		// map.put("?", "&iexcl");
		// // ? ? &acute; 尖重音符Acute accent
		// map.put("?", "&iexcl");
		// // ? ? &micro; Micro sign
		// map.put("?", "&iexcl");
		// // ? ? &para; Paragraph sign
		// map.put("?", "&iexcl");
		// // · · &middot; Middle dot
		// map.put("?", "&iexcl");
		// // ? ? &cedil ; Cedilla
		// map.put("?", "&iexcl");
		// // ? ? &sup1; 上标1 Superscript one
		// map.put("?", "&iexcl");
		// // ? ? &ordm; Masculine ordinal
		// map.put("?", "&iexcl");
		// // ? ? ; &raquo ; Right angle quote, guillemet right
		// map.put("?", "&iexcl");
		// // ? ? &frac14 ; 四分之一Fraction one-fourth
		// map.put("?", "&iexcl");
		// // ? ? &frac12; 二分之一Fraction one-half
		// map.put("?", "&iexcl");
		// // ? ? &frac34; 四分之三Fraction three-fourths
		// map.put("?", "&iexcl");
		// // ? ? &iquest; Inverted question mark
		// map.put("?", "&iexcl");
		// // ? ? ; &Agrave ; Capital A, grave accent
		// map.put("?", "&iexcl");
		// // ? ? &Aacute; Capital A , acute accent
		// map.put("?", "&iexcl");
		// // ? ? &Acirc; Capital A , circumflex
		// map.put("?", "&iexcl");
		// // ? ? &Atilde; Capital A, tilde
		// map.put("?", "&iexcl");
		// // ? ? ; &Auml; Capital A, di?esis / umlaut
		// map.put("?", "&iexcl");
		// // ? ? &Aring; Capital A, ring
		// map.put("?", "&iexcl");
		// // ? ? &AElig; Capital AE ligature
		// map.put("?", "&iexcl");
		// // ? ? &Ccedil; Capital C, cedilla
		// map.put("?", "&iexcl");
		// // ? ? &Egrave; Capital E, grave accent
		// map.put("?", "&iexcl");
		// // ? ? ; &Eacute; Capital E, acute accent
		// map.put("?", "&iexcl");
		// // ? ? &Ecirc ; Capital E, circumflex
		// map.put("?", "&iexcl");
		// // ? ? &Euml; Capital E, di?esis / umlaut
		// map.put("?", "&iexcl");
		// // ? ? &Igrave; Capital I, grave accent
		// map.put("?", "&iexcl");
		// // ? ? &Iacute ; Capital I, acute accent
		// map.put("?", "&iexcl");
		// // ? ? &Icirc ; Capital I, circumflex
		// map.put("?", "&iexcl");
		// // ? ? ; &Iuml; Capital I , di?esis / umlaut
		// map.put("?", "&iexcl");
		// // ? ? &ETH; Capital Eth, Icel andic
		// map.put("?", "&iexcl");
		// // ? ? ; &Ntilde; Capital N , tilde
		// map.put("?", "&iexcl");
		// // ? ? &Ograve; Capital O, grave accent
		// map.put("?", "&iexcl");
		// // ? ? ; &Oacute; Capital O , acute accent
		// map.put("?", "&iexcl");
		// // ? ? &Ocirc; Capital O, circumflex
		// map.put("?", "&iexcl");
		// // ? ? &Otilde; Capital O, tilde
		// map.put("?", "&iexcl");
		// // ? ? &Ouml; Capital O, di?esis / umlaut
		// map.put("?", "&iexcl");
		// // × × &times; 乘号Multiply sign
		// map.put("?", "&iexcl");
		// // ? ? &Oslash; Capital O, slash
		// map.put("?", "&iexcl");
		// // ? ? &Ugrave; Capital U, grave accent
		// map.put("?", "&iexcl");
		// // ? ? &Uacute; Capital U, acute accent
		// map.put("?", "&iexcl");
		// // ? ? &Ucirc; Capital U, circumflex
		// map.put("?", "&iexcl");
		// // ? ? &Uuml; Capital U, di?esis / umlaut
		// map.put("?", "&iexcl");
		// // ? ? &Yacute ; Capital Y, acute accent
		// map.put("?", "&iexcl");
		// // ? ? &TH ORN ; Capital Thorn, Icel andic
		// map.put("?", "&iexcl");
		// // ? ? &szlig ; Small sharp s, German sz
		// map.put("?", "&iexcl");
		// // à à &agrave ; Small a, grave accent
		// map.put("?", "&iexcl");
		// // á á &aacute; Small a, acute accent
		// map.put("?", "&iexcl");
		// // ? ? &acirc; Small a, circumflex
		// map.put("?", "&iexcl");
		// // ? ? &atilde; Small a, tilde
		// map.put("?", "&iexcl");
		// // ? ? &auml; Small a , di?esis / umlaut
		// map.put("?", "&iexcl");
		// // ? ? &aring; Small a, ring
		// map.put("?", "&iexcl");
		// // ? ? &aelig; Small ae ligature
		// map.put("?", "&iexcl");
		// // ? ? &ccedil; Small c, cedilla
		// map.put("?", "&iexcl");
		// // è è ; &egrave; Small e, grave accent
		// map.put("?", "&iexcl");
		// // é é ; &eacute; Small e, acute accent
		// map.put("?", "&iexcl");
		// // ê ê &ecirc; Small e, circumflex
		// map.put("?", "&iexcl");
		// // ? ? &euml; Small e, di?esis / umlaut
		// map.put("?", "&iexcl");
		// // ì ì &igrave; Small i, grave accent
		// map.put("?", "&iexcl");
		// // í í &iacute; Small i, acute accent
		// map.put("?", "&iexcl");
		// // ? ? &icirc ; Small i, circumflex
		// // ? ? &iuml; Small i, di?esis / umlaut
		// // ? ? &eth; Small eth, Icelandic
		// // ? ? &ntilde; Small n, tilde
		// // ò ò &ograve; Small o, grave accent
		// // ó ó ; &oacute; Small o, acute accent
		// // ? ? ; &ocirc; Small o, circumflex
		// // ? ? &otilde; Small o , tilde
		// // ? ? &ouml; Small o, di?esis / umlaut
		// // ÷ ÷ &divide; 除号Division sign
		// // ? ? &oslash; Small o, slash
		// // ù ù &ugrave; Small u, grave accent
		// // ú ú &uacute; Small u, acute accent
		// // ? ? &ucirc; Small u, circumflex
		// // ü ü &uuml ; Small u, di?esis / umlaut
		// // ? ? &yacute ; Small y, acute accent
		// // ? ? &thorn; Small thorn, Icelandic
		// // ? ? &yuml; Small y, umlaut
	}
}
