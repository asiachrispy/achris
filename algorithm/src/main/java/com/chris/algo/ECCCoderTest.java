package com.chris.algo;

import org.junit.Test;

import java.util.Map;

/**
 *
 * @author 梁栋  http://www.5a520.cn http://www.feng123.com
 * @version 1.0
 * @since 1.0
 */
public class ECCCoderTest {

    @Test
    public void test() throws Exception {
        String inputStr = "abc";
        byte[] data = inputStr.getBytes();

        Map<String, Object> keyMap = ECCCoder.initKey();

        String publicKey = ECCCoder.getPublicKey(keyMap);
        String privateKey = ECCCoder.getPrivateKey(keyMap);
        System.err.println("公钥: \n" + publicKey);
        System.err.println("私钥： \n" + privateKey);

        byte[] encodedData = ECCCoder.encrypt(data, publicKey);

        byte[] decodedData = ECCCoder.decrypt(encodedData, privateKey);

        String outputStr = new String(decodedData);
        System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
        //assertEquals(inputStr, outputStr);
    }
}
