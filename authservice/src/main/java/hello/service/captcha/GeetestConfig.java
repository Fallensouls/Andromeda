package hello.service.captcha;

public class GeetestConfig {
    // 填入自己的captcha_id和private_key
    private static final String geetest_id = "25e9940acca6ec1d6aa58134046ed2b4";
    private static final String geetest_key = "ac4a3fd99bf8b8f2d16c84f951ee6587";
    private static final boolean newfailback = true;

    public static final String getGeetest_id() {
        return geetest_id;
    }

    public static final String getGeetest_key() {
        return geetest_key;
    }

    public static final boolean isnewfailback() {
        return newfailback;
    }
}
