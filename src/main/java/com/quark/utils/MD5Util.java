package com.quark.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class MD5Util {

    private static final String ALGORITHM_NAME = "MD5";

    private static int HASH_ITERATIONS = 2;

    public static String encrypt(String password){
        return new SimpleHash(ALGORITHM_NAME,password, ByteSource.Util.bytes(password),HASH_ITERATIONS).toHex();
    }

    public static String encrypt(String username,String password){
        return new SimpleHash(ALGORITHM_NAME,password,ByteSource.Util.bytes(username+password.toLowerCase()),HASH_ITERATIONS).toHex();
    }

    public static void main(String[] args) {
        System.out.println(encrypt("asdqwe123"));
    }

}
