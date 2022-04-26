package com.qlsv.qlsv;

import org.apache.commons.codec.digest.DigestUtils;

public class generateMD5 {
    public static void generate(String[] args){
        String key = "ThisIsAKey";
        String hash;
        for(int i=19000001; i <= 19000020;i++) {
            hash = DigestUtils.md5Hex(String.valueOf(i));
            hash = DigestUtils.md5Hex(hash + key);
            System.out.println(i - 19000000 + ": " + hash);
        }
    }
}
