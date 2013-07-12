package com.chris.algo;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * 加密解密，曾经是我一个毕业设计的重要组件。在工作了多年以后回想当时那个加密、解密算法，实在是太单纯了。
 * 言归正传，这里我们主要描述Java已经实现的一些加密解密算法，最后介绍数字证书。
 * 如基本的单向加密算法：
 * <p/>
 * BASE64 严格地说，属于编码格式，而非加密算法
 * MD5(Message Digest algorithm 5，信息摘要算法)
 * SHA(Secure Hash Algorithm，安全散列算法)
 * HMAC(Hash Message Authentication Code，散列消息鉴别码)
 * <p/>
 * <p/>
 * 复杂的对称加密（DES、PBE）、非对称加密算法：
 * <p/>
 * DES(Data Encryption Standard，数据加密算法)
 * PBE(Password-based encryption，基于密码验证)
 * RSA(算法的名字以发明者的名字命名：Ron Rivest, AdiShamir 和Leonard Adleman)
 * DH(Diffie-Hellman算法，密钥一致协议)
 * DSA(Digital Signature Algorithm，数字签名)
 * ECC(Elliptic Curves Cryptography，椭圆曲线密码编码学)
 * <p/>
 * <p/>
 * <p/>
 * 本篇内容简要介绍BASE64、MD5、SHA、HMAC几种方法。
 * MD5、SHA、HMAC这三种加密算法，可谓是非可逆加密，就是不可解密的加密方法。我们通常只把他们作为加密的基础。单纯的以上三种的加密并不可靠。
 * <p/>
 * BASE64
 * 按照RFC2045的定义，Base64被定义为：Base64内容传送编码被设计用来把任意序列的8位字节描述为一种不易被人直接识别的形式。（The Base64 Content-Transfer-Encoding is designed to represent arbitrary sequences of octets in a form that need not be humanly readable.）
 * 常见于邮件、http加密，截取http信息，你就会发现登录操作的用户名、密码字段通过BASE64加密的。
 * <p/>
 * User: zhong.huang
 * Date: 13-6-6
 */
public class BMP {

    public static void main(String[] args) {
        try {
            byte[] by = decryptBASE64("chris");
            System.out.println(by.toString());
            String key = encryptBASE64(by);
            System.out.println(key);

        } catch (Exception e) {
        }
    }

    public static void bmp() {
        //创建一个具有10000000位的bitset　初始所有位的值为false
        java.util.BitSet bitSet = new java.util.BitSet(10000000);
        //将指定位的值设为true
        bitSet.set(9999, true);
        bitSet.set(9990, true);
        //输出指定位的值
        System.out.println(bitSet.get(9999));
        System.out.println(bitSet.get(9998));
    }

    /**
     * BASE64解密  http://www.bt285.cn http://www.5a520.cn
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * MD5 -- message-digest algorithm 5 （信息-摘要算法）缩写，广泛用于加密和解密技术，常用于文件校验。
     * 校验？不管文件多大，经过MD5后都能生成唯一的MD5值。好比现在的ISO校验，都是MD5校验。
     * 怎么用？当然是把ISO经过MD5后产生MD5的值。
     * 一般下载linux-ISO的朋友都见过下载链接旁边放着MD5的串。就是用来验证文件是否一致的。
     * <p/>
     * 通常我们不直接使用上述MD5加密。通常将MD5产生的字节数组交给BASE64再加密一把，得到相应的字符串。
     * <p/>
     * MD5加密 http://www.bt285.cn http://www.5a520.cn
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptMD5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);

        return md5.digest();

    }

    /**
     * SHA(Secure Hash Algorithm，安全散列算法），数字签名等密码学应用中重要的工具，
     * 被广泛地应用于电子商务等信息安全领域。虽然，SHA与MD5通过碰撞法都被破解了，
     * 但是SHA仍然是公认的安全加密算法，较之MD5更为安全。
     * <p/>
     * SHA加密 http://www.5a520.cn http://www.bt285.cn
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptSHA(byte[] data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA");
        sha.update(data);
        return sha.digest();

    }

    /**
     * 初始化HMAC密钥  http://www.guihua.org  http://www.feng123.com
     *
     * @return
     * @throws Exception
     */
    public static String initMacKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");

        SecretKey secretKey = keyGenerator.generateKey();
        return encryptBASE64(secretKey.getEncoded());
    }

    /**
     * HMAC
     * HMAC(Hash Message Authentication Code，散列消息鉴别码，基于密钥的Hash算法的认证协议。
     * 消息鉴别码实现鉴别的原理是，用公开函数和密钥产生一个固定长度的值作为认证标识，
     * 用这个标识鉴别消息的完整性。使用一个密钥生成一个固定大小的小数据块，即MAC，
     * 并将其加入到消息中，然后传输。接收方利用与发送方共享的密钥进行鉴别认证等。
     * HMAC加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptHMAC(byte[] data, String key) throws Exception {

        SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), "HmacMD5");
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);

        return mac.doFinal(data);

    }
}

