package com.datawise.satisactual.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Encryption and Decryption of String data; PBE(Password Based Encryption and Decryption)
 * @author Vikram
 */
/*
 ** This program has been changed in October, 2020
 ** The old encrypt/decrypt functions have been deprecated and renamed to __encrypt and __decrypt. The corresponding key has been renamed to __key
 ** The new encrypt and decrypt functions have been created to handle the new format accessdetails.txt file which can be read both by the Java code
 ** which forms the bulk of the Infinity and SatisACTual applications, AND by the PHP APIs that are used to interface with the datTab and InfinityField
 ** Android front-end applications.
 */
public class CryptoUtil {

    Cipher ecipher;
    Cipher dcipher;
    static String __key="ezeon8547";
    static String key = "BDFHJLNPpnljhfdb";
    final String ALGO = "AES";

    // 8-byte Salt
    byte[] salt = {
            (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
            (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
    };
    // Iteration count
    int iterationCount = 19;
    public CryptoUtil() {

    }

    /**
     *
     * @param secretKey Key used to encrypt data
     * @param plainText Text input to be encrypted
     * @return Returns encrypted text
     *
     */
    public String __encrypt(String secretKey, String plainText)
            throws NoSuchAlgorithmException,
            InvalidKeySpecException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            UnsupportedEncodingException,
            IllegalBlockSizeException,
            BadPaddingException{
        //Key generation for enc and desc
        KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
        SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
        // Prepare the parameter to the ciphers
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

        //Enc process
        ecipher = Cipher.getInstance(key.getAlgorithm());
        ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
        String charSet="UTF-8";
        byte[] in = plainText.getBytes(charSet);
        byte[] out = ecipher.doFinal(in);
        String encStr= new String(Base64.getEncoder().encode(out));
        return encStr;
    }
    /**
     * @param encryptedText encrypted text input to decrypt
     * @return Returns plain text after decryption
     */
    public String __decrypt(String encryptedText)
            throws NoSuchAlgorithmException,
            InvalidKeySpecException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            UnsupportedEncodingException,
            IllegalBlockSizeException,
            BadPaddingException,
            IOException{
        //Key generation for enc and desc
        KeySpec keySpec = new PBEKeySpec(__key.toCharArray(), salt, iterationCount);
        SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
        // Prepare the parameter to the ciphers
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
        //Decryption process; same key will be used for decr
        dcipher=Cipher.getInstance(key.getAlgorithm());
        dcipher.init(Cipher.DECRYPT_MODE, key,paramSpec);
        byte[] enc = Base64.getDecoder().decode(encryptedText);
        byte[] utf8 = dcipher.doFinal(enc);
        String charSet="UTF-8";
        String plainStr = new String(utf8, charSet);
        return plainStr;
    }

    public  String encrypt(String Data) throws Exception {

        Key l_key = new SecretKeySpec(key.getBytes(), ALGO);

        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, l_key);
        byte[] encVal = c.doFinal(Data.getBytes());
        char[] encArr = new Base64Coder().encode(encVal, 1, 1000);
        String encStr = new String(encArr);
        return encStr;
    }

    /**
     * @param encryptedData encrypted text input to decrypt
     * @return Returns plain text after decryption
     */
    public String decrypt(String encryptedData) throws Exception
    {
        Key l_key = new SecretKeySpec(key.getBytes(), ALGO);
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, l_key);

        byte[] decodedValue = new Base64Coder().decode(encryptedData);
        byte[] decValue = c.doFinal(decodedValue);
        String plainStr = new String(decValue);
        return plainStr;
    }

    public static String getEncryptedPassword(String plainTextPassword) {
        String encryptedPassword = "";
        try {
            //encrypt codefor password
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainTextPassword.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            encryptedPassword = sb.toString();
        } catch (Exception e) {
            System.out.println("Error encrypting password");
            e.printStackTrace();
        }

        return encryptedPassword;
    }

    public static String generatePassword(String firstName, String lastName) throws NoSuchAlgorithmException {
        String password = firstName + "_" + lastName;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}
