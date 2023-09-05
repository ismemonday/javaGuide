package org.mgd.decoder;

import java.security.interfaces.RSAKey;
import java.util.Base64;

public class DecodeTest {
    public static void main(String[] args) {
        String hello="hello world";
        //base64加密
        byte[] encode = Base64.getEncoder().encode(hello.getBytes());
        System.out.println(new String(encode));
        byte[] encode1 = Base64.getEncoder().encode(hello.getBytes());
        System.out.println(new String(encode1));
        byte[] encode2 = Base64.getEncoder().encode(encode);
        System.out.println(new String(encode2));
        //base64解密
        byte[] decode = Base64.getDecoder().decode(encode);
        byte[] decode2 = Base64.getDecoder().decode(encode2);
        System.out.println(new String(decode));
        System.out.println(new String(decode2));
        byte[] decode21 = Base64.getDecoder().decode(decode2);
        System.out.println(new String(decode21));

    }
}
