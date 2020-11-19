package co.dianjiu.hello.utils;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	public static final String WHITE_SPACE = " \t\n\r";
	private static Pattern VARIABLE_PATTERN = Pattern.compile("\\$\\s*\\{?\\s*([\\._0-9a-zA-Z]+)\\s*\\}?");

	public static String captureName(String name) {
		char[] cs = name.toCharArray();
		int tmp7_6 = 0;
		char[] tmp7_5 = cs;
		tmp7_5[tmp7_6] = (char) (tmp7_5[tmp7_6] - ' ');
		return String.valueOf(cs);
	}

	public static boolean isWhitespace(String s) {
		if (isEmpty(s)) {
			return true;
		}
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (" \t\n\r".indexOf(c) == -1) {
				return false;
			}
		}
		return true;
	}

	public static boolean isEmpty(String s) {
		return (s == null) || (s.trim().length() == 0);
	}

	public static String replaceProperty(String expression, Properties params) {
		if ((expression == null) || (expression.length() == 0) || (expression.indexOf(36) < 0)) {
			return expression;
		}
		Matcher matcher = VARIABLE_PATTERN.matcher(expression);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String key = matcher.group(1);

			String value = System.getenv(key);
			if ((value == null) || (value.trim().equals(""))) {
				value = System.getProperty(key);
			}
			if ((value == null) && (params != null)) {
				value = (String) params.get(key);
			}
			if (value == null) {
				value = "";
			}
			matcher.appendReplacement(sb, Matcher.quoteReplacement(value));
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
}
