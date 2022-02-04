package com.example.aesutiltestapp.util;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES256Util {
    final static String TAG = AES256Util.class.getSimpleName();
    private String iv;
    private Key keySpec;
    /**
     * 16자리의 키값을 입력하여 객체를 생성한다.
     *
     * @param key
     * 암/복호화를 위한 키값
     * @throws UnsupportedEncodingException
     * 키값의 길이가 16이하일 경우 발생
     */
    final static String key = "slkndflskndflkdfnoio454";

    public AES256Util() throws UnsupportedEncodingException {
        this.iv = key.substring(0, 16);
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length) {
            len = keyBytes.length;
        }
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        this.keySpec = keySpec;
    }

    public boolean encryptFile(String fileName) {
        String outPath = fileName.replace(".mp4", "_encrypted.mp4");
        File encryptFile = new File(outPath);
        try {
            if(!encryptFile.createNewFile()) {
                Log.d(TAG, "create file fail");
                return false;
            }

            FileInputStream inputStream = new FileInputStream(fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(encryptFile);
            int read;
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
            CipherInputStream cis = new CipherInputStream(inputStream, cipher);

            byte[] buffer = new byte[8192];
            while ((read = cis.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, read);
            }
            fileOutputStream.close();
            cis.close();
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException
                | InvalidAlgorithmParameterException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        Log.d(TAG, fileName + " encrypt success");
        return true;
    }

    public boolean decryptFile(String fileName) {
        String outPath = fileName.replace(".mp4", "_decrypted.mp4");
        File encryptFile = new File(fileName);
        File decryptFile = new File(outPath);
        try {
            if(!decryptFile.createNewFile()) {
                Log.d(TAG, "create file fail");
                return false;
            }
            FileInputStream inputStream = new FileInputStream(encryptFile);
            FileOutputStream fileOutputStream = new FileOutputStream(decryptFile);
            int read;
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
            CipherInputStream cis = new CipherInputStream(inputStream, cipher);
            byte[] buffer = new byte[8192];
            while ((read = cis.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, read);
                Log.d(TAG, "write " + read);
            }
            fileOutputStream.close();
            cis.close();
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException
                | InvalidAlgorithmParameterException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        Log.d(TAG, fileName + " decrypt success");
        return true;
    }
}
