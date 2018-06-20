package com.example.ba.jpushdemo.encryption_tool;

/**
 * Created by BA on 2018/6/20 0020.
 */

public class EncryptionFactory {
    public static final int TYPE_MD5 = 1;
    public static final int TYPE_SHA = 2;

    public EncryptionTool getClient(int type) {
        switch (type) {
            case TYPE_MD5:
                return new Encryption2MD5();
            case TYPE_SHA:
                return new Encryption2SHA();
            default:
                return null;
        }
    }
}
