package com.example.ba.jpushdemo.encryption_tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by BA on 2018/6/20 0020.采用SHA方式加密密码
 */

 class Encryption2SHA implements EncryptionTool {
    @Override
    public String getResult(String text) {
        // 验证传入的字符串
        if (text == null || text.equals("")) {
            return "";
        }
        // 创建具有指定算法名称的信息摘要
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
            // 使用指定的字节数组对摘要进行最后更新
            sha.update(text.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] bytes = sha.digest();
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            if ((b & 0xFF) < 0x10){
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
      }
}
