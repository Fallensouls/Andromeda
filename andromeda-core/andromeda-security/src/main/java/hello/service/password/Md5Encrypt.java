package hello.service.password;
import sun.misc.BASE64Encoder;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Md5Encrypt {
    /**利用MD5进行加密
     * @param str  待加密的字符串
     * @return  加密后的字符串
     */

    public static String EncoderByMd5(String str){
        //确定计算方法
        MessageDigest md5= null;
        String result="";
        try {
            md5 = MessageDigest.getInstance("MD5");
            result=new BASE64Encoder().encode(md5.digest(str.getBytes("utf-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //加密后的字符串
        return result;
    }
}
