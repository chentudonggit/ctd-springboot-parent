package com.ctd.springboot.common.core.utils.rsa;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 加密工具
 *
 * @author chentudong
 * @date 2019/8/20 21:11
 */
public final class RsaUtils {

    private static final String CIPHER_INSTANCE = "RSA/ECB/PKCS1Padding";
    private static final String RSA = "RSA";

    private static final KeyPair KEY_PAIR = initKey();

    private static final String RM = "1485911093980466539602251665617432573407094000417447004282924779707911060961966661" +
            "77857622373761787549416835748972964450253950685016373794898455705722054144000951974374894836815944566223251" +
            "225005475733338880518270152564313806057014117937280775741076170704355375933801070732869063448839888298721357589130064073";
    private static final String RE = "4940642484234607376668548460483310680549000619969877728661552081208338790213500088" +
            "01393937645397731432525484505831800103069081492468651504437531208842223678146045722759843529259848363093552" +
            "91957135659316198844588118790243734652643395389156065029573103258872818271296403492500974672509805784899854018633986413";
    private static RSAPrivateKey DEFAULT_PRIVATE_KEY = getPrivateKey(RM, RE);

    /**
     * 用默认的公锁加密
     */
    public static String encryptBase64Default(String string) {
        return encryptBase64(string, DEFAULT_PUBLIC_KEY);
    }

    private static final String PM = "1485911093980466539602251665617432573407094000417447004282924779707911060961966661" +
            "77857622373761787549416835748972964450253950685016373794898455705722054144000951974374894836815944566223251" +
            "225005475733338880518270152564313806057014117937280775741076170704355375933801070732869063448839888298721357589130064073";
    private static final String PE = "65537";
    private static RSAPublicKey DEFAULT_PUBLIC_KEY = getPublicKey(PM, PE);


    /**
     * 加密
     *
     * @param string string
     * @return String
     */
    public static String encryptBase64(String string) {
        return new String(Base64.encodeBase64(encrypt(string.getBytes())));
    }

    /**
     * 用默认的私钥解密
     */
    public static String decryptBase64Default(String string) {
        return decryptBase64(string, DEFAULT_PRIVATE_KEY);
    }

    /**
     * 用私钥解密
     *
     * @param string string
     * @param prk    prk
     * @return String
     */
    public static String decryptBase64(String string, RSAPrivateKey prk) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");

            cipher.init(Cipher.DECRYPT_MODE, prk);
            byte[] plainText = cipher.doFinal(Base64.decodeBase64(string));
            return new String(plainText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 用公匙加密
     */
    public static String encryptBase64(String string, PublicKey pbk) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, pbk);
            byte[] plainText = cipher.doFinal(string.getBytes());
            return new String(Base64.encodeBase64(plainText));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] encrypt(byte[] string) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
            PublicKey pbk = KEY_PAIR.getPublic();
            cipher.init(Cipher.ENCRYPT_MODE, pbk);
            return cipher.doFinal(string);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * getPublicKey
     *
     * @param modulus  modulus
     * @param exponent exponent
     * @return RSAPublicKey
     */
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static KeyPair initKey() {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            SecureRandom random = new SecureRandom();
            random.setSeed(System.currentTimeMillis());
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
            generator.initialize(1024, random);
            return generator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取私匙
     *
     * @param modulus  modulus
     * @param exponent exponent
     * @return RSAPrivateKey
     */
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
        try {
            BigInteger b1 = new BigInteger(modulus);
            BigInteger b2 = new BigInteger(exponent);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 公匙
     *
     * @return String
     */
    public static String base64PublicKey() {
        return new String(Base64.encodeBase64(KEY_PAIR.getPublic().getEncoded()));
    }

    /**
     * 私匙
     *
     * @return String
     */
    public static String base64PrivateKey() {
        return new String(Base64.encodeBase64(KEY_PAIR.getPrivate().getEncoded()));
    }

    /**
     * 获取Default公匙
     *
     * @return String
     */
    public static String base64PublicKeyDefault() {
        return new String(Base64.encodeBase64(DEFAULT_PUBLIC_KEY.getEncoded()));
    }

    /**
     * 获取Default私匙
     *
     * @return String
     */
    public static String base64PrivateKeyDefault() {
        return new String(Base64.encodeBase64(DEFAULT_PRIVATE_KEY.getEncoded()));
    }

    /**
     * String转公钥PublicKey
     *
     * @param key 公钥字符
     */
    public static RSAPublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    /**
     * String转私钥PrivateKey
     *
     * @param key 私钥字符
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = (new BASE64Decoder()).decodeBuffer(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(keySpec);
    }
}
