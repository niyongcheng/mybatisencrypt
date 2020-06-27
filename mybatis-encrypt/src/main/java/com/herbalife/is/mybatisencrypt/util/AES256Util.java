package com.herbalife.is.mybatisencrypt.util;

import java.nio.charset.StandardCharsets;
import java.security.*;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

public class AES256Util {

    private static Cipher cipher;
    private static SecretKeySpec key;

    private static final SecretKeySpec getSecretKeySpec(String password) throws NoSuchAlgorithmException {
        if(key == null){
            //"AES"：請求的金鑰演算法的標準名稱
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            //256：金鑰生成引數；securerandom：金鑰生成器的隨機源
            SecureRandom securerandom = new SecureRandom(tohash256Deal(password));
            kgen.init(256, securerandom);
            //生成祕密（對稱）金鑰
            SecretKey secretKey = kgen.generateKey();
            //返回基本編碼格式的金鑰
            byte[] enCodeFormat = secretKey.getEncoded();
            //根據給定的位元組陣列構造一個金鑰。enCodeFormat：金鑰內容；"AES"：與給定的金鑰內容相關聯的金鑰演算法的名稱
            key = new SecretKeySpec(enCodeFormat, "AES");
            //將提供程式新增到下一個可用位置
            Security.addProvider(new BouncyCastleProvider());
        }
        return key;
    }

    private static final Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        if(cipher == null){
            cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
        }
        return cipher;
    }

    public static byte[] encrypt(String content, String password) {
        try {
            SecretKeySpec secretKeySpec = getSecretKeySpec(password);
            Cipher cipher = getCipher();
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            System.out.println(cipher);

            byte[] byteContent = content.getBytes("utf-8");
            byte[] cryptograph = cipher.doFinal(byteContent);
            return Base64.encode(cryptograph);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String encryptToString(String content, String password) {
        try {
            SecretKeySpec secretKeySpec = getSecretKeySpec(password);
            Cipher cipher = getCipher();
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            System.out.println(cipher);

            byte[] byteContent = content.getBytes("utf-8");
            byte[] cryptograph = cipher.doFinal(byteContent);
            return new String(Base64.encode(cryptograph), StandardCharsets.UTF_8) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(byte[] cryptograph, String password) {
        try {
            SecretKeySpec secretKeySpec = getSecretKeySpec(password);
            Cipher cipher = getCipher();
            cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);

            System.out.println(cipher);

            byte[] content = cipher.doFinal(Base64.decode(cryptograph));
            //return new String(content);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptToString(byte[] cryptograph, String password) {
        try {
            SecretKeySpec secretKeySpec = getSecretKeySpec(password);
            Cipher cipher = getCipher();
            cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);

            System.out.println(cipher);

            byte[] content = cipher.doFinal(Base64.decode(cryptograph));
            return new String(content,StandardCharsets.UTF_8);
            //return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] tohash256Deal(String datastr) {
        try {
            MessageDigest digester=MessageDigest.getInstance("SHA-256");
            digester.update(datastr.getBytes());
            byte[] hex=digester.digest();
            return hex;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
