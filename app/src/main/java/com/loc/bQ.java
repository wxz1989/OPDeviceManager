package com.loc;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class bQ {
    private static final byte[] b = new byte[]{(byte) 0, (byte) 1, (byte) 1, (byte) 2, (byte) 3, (byte) 5, (byte) 8, (byte) 13, (byte) 8, (byte) 7, (byte) 6, (byte) 5, (byte) 4, (byte) 3, (byte) 2, (byte) 1};
    private static final char[] tN = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final IvParameterSpec tO = new IvParameterSpec(b);

    public static synchronized byte[] yl(byte[] bArr, String str) {
        byte[] doFinal;
        int i = 0;
        synchronized (bQ.class) {
            Key generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(aG.rm(str)));
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            instance.init(1, generatePrivate);
            int length = bArr.length;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i2 = 0;
            while (length - i > 0) {
                doFinal = length - i <= 245 ? instance.doFinal(bArr, i, length - i) : instance.doFinal(bArr, i, 245);
                byteArrayOutputStream.write(doFinal, 0, doFinal.length);
                i = i2 + 1;
                int i3 = i;
                i *= 245;
                i2 = i3;
            }
            doFinal = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        }
        return doFinal;
    }

    public static synchronized byte[] ym(byte[] bArr, String str) {
        byte[] doFinal;
        int i = 0;
        synchronized (bQ.class) {
            Key generatePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(aG.rm(str)));
            Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            instance.init(2, generatePrivate);
            int length = bArr.length;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i2 = 0;
            while (length - i > 0) {
                doFinal = length - i <= 256 ? instance.doFinal(bArr, i, length - i) : instance.doFinal(bArr, i, 256);
                byteArrayOutputStream.write(doFinal, 0, doFinal.length);
                i = i2 + 1;
                int i3 = i;
                i *= 256;
                i2 = i3;
            }
            doFinal = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
        }
        return doFinal;
    }

    public static byte[] yn(byte[] bArr, String str) {
        try {
            Key yp = yp(str);
            AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(yu());
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            try {
                instance.init(1, yp, ivParameterSpec);
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
            return instance.doFinal(bArr);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static byte[] yo(byte[] bArr, String str) {
        try {
            Key yp = yp(str);
            AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(yu());
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            try {
                instance.init(2, yp, ivParameterSpec);
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
            return instance.doFinal(bArr);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static SecretKeySpec yp(String str) {
        byte[] bytes;
        if (str == null) {
            str = "";
        }
        StringBuffer stringBuffer = new StringBuffer(16);
        stringBuffer.append(str);
        while (stringBuffer.length() < 16) {
            stringBuffer.append("0");
        }
        if (stringBuffer.length() > 16) {
            stringBuffer.setLength(16);
        }
        try {
            bytes = stringBuffer.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            bytes = null;
        }
        return new SecretKeySpec(bytes, "AES");
    }

    public static String yq(String str) {
        if (str != null) {
            try {
                if (str.length() != 0) {
                    return yr("MD5", yr("SHA1", str) + str);
                }
            } catch (Throwable th) {
                th.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String yr(String str, String str2) {
        if (str2 == null) {
            return null;
        }
        try {
            return ys(J.nb(str2.getBytes("UTF-8"), str));
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    private static String ys(byte[] bArr) {
        int length = bArr.length;
        StringBuilder stringBuilder = new StringBuilder(length * 2);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(tN[(bArr[i] >> 4) & 15]);
            stringBuilder.append(tN[bArr[i] & 15]);
        }
        return stringBuilder.toString();
    }

    public static byte[] yt(byte[] bArr, byte[] bArr2) {
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(2, new SecretKeySpec(bArr, "AES"), tO);
        return instance.doFinal(bArr2);
    }

    private static byte[] yu() {
        return bw.wY();
    }

    public static String yv(byte[] bArr) {
        try {
            Object obj = new byte[16];
            Object obj2 = new byte[(bArr.length - 16)];
            System.arraycopy(bArr, 0, obj, 0, 16);
            System.arraycopy(bArr, 16, obj2, 0, bArr.length - 16);
            Key secretKeySpec = new SecretKeySpec(obj, "AES");
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(2, secretKeySpec, new IvParameterSpec(bw.wY()));
            return new String(instance.doFinal(obj2), "UTF-8");
        } catch (Throwable e) {
            bq.vC(e);
            e.printStackTrace();
            return null;
        }
    }
}
