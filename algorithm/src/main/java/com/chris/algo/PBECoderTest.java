package com.chris.algo;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * 除了DES，我们还知道有DESede(TripleDES,就是3DES)、AES、Blowfish、RC2、RC4(ARCFOUR
 * 等多种对称加密方式，其实现方式大同小异，这里介绍对称加密的另一个算法——PBE
 * PBE——Password-based encryption（基于密码加密）。其特点在于口令由用户自己掌管，不借助任何物理媒体；
 * 采用随机数（这里我们叫做盐）杂凑多重加密等方法保证数据的安全性。是一种简便的加密方式。
 *
 * @author 梁栋  http://www.5a520.cn http://www.feng123.com
 * @version 1.0
 * @since 1.0
 */
public class PBECoderTest {

    @Test
    public void test() throws Exception {
        String inputStr = "abc";
        System.err.println("原文: " + inputStr);
        byte[] input = inputStr.getBytes();

        String pwd = "efg";
        System.err.println("密码: " + pwd);

        byte[] salt = PBECoder.initSalt();

        byte[] data = PBECoder.encrypt(input, pwd, salt);

        System.err.println("加密后: " + PBECoder.encryptBASE64(data));

        byte[] output = PBECoder.decrypt(data, pwd, salt);
        String outputStr = new String(output);

        System.err.println("解密后: " + outputStr);
        assertEquals(inputStr, outputStr);
    }

}