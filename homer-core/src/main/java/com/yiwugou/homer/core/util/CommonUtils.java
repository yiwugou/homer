package com.yiwugou.homer.core.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

import com.yiwugou.homer.core.constant.Constants;

public class CommonUtils {

    public static String bytesToString(byte[] bs, String def) {
        if (bs == null || bs.length == 0) {
            return def;
        }
        return new String(bs, Constants.UTF_8);
    }

    public static <T extends Object> boolean isEmpty(T[] objs) {
        return (objs == null || objs.length == 0);
    }

    public static boolean hasTest(String str) {
        return !(str == null || "".equals(str.trim()));
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
            }
        }
    }

    public static byte[] inputStreamToBytes(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            copy(input, output);
            byte[] bs = output.toByteArray();
            return bs;
        } finally {
            close(output);
        }
    }

    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = 0;
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    public static Object stringToBasic(String str, Type type) {
        if (!CommonUtils.hasTest(str)) {
            return null;
        }
        if (String.class.equals(type)) {
            return str;
        } else if (Long.class.equals(type)) {
            return Long.parseLong(str);
        } else if (Integer.class.equals(type)) {
            return Integer.parseInt(str);
        } else if (Double.class.equals(type)) {
            return Double.parseDouble(str);
        } else if (Float.class.equals(type)) {
            return Float.parseFloat(str);
        } else if (Short.class.equals(type)) {
            return Short.parseShort(str);
        } else if (Boolean.class.equals(type)) {
            return Boolean.valueOf(str);
        }
        throw new IllegalArgumentException(
                "type is " + type + ", can not cast to String, Long, Integer, Double, Float, Short, Boolean !");
    }

    public static String joinToString(String separator, Object[] objs) {
        if (objs == null || objs.length == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (Object obj : objs) {
            if (sb.length() == 0) {
                sb.append(obj);
            } else {
                sb.append(separator).append(obj);
            }
        }
        return sb.toString();
    }

    public static boolean isDefaultMethod(Method method) {
        final int SYNTHETIC = 0x00001000;
        return ((method.getModifiers()
                & (Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC | SYNTHETIC)) == Modifier.PUBLIC)
                && method.getDeclaringClass().isInterface();
    }

    public static String nullToEmptyString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static String urlDecode(String arg) {
        try {
            return URLDecoder.decode(arg, Constants.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String urlEncode(Object arg) {
        try {
            return URLEncoder.encode(String.valueOf(arg), Constants.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String expandMap(String str, Map<String, Object> param) {
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            str = CommonUtils.expandOne(str, entry.getKey(), entry.getValue());
        }
        return str;
    }

    public static String expandOne(String str, String name, Object value) {
        if (str.contains("{" + name + "}")) {
            str = str.replaceAll("\\{" + name + "\\}", CommonUtils.nullToEmptyString(value));
        }
        return str;
    }
}
