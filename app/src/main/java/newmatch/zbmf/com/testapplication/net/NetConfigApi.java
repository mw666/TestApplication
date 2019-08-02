package newmatch.zbmf.com.testapplication.net;

/**
 * Created by **
 * on 2018/8/23.
 * <p>
 * API地址配置
 */

public class NetConfigApi {

    //玩Android接口
    public static final String BASE_URL =
//            "http://106.12.103.105:8081/web/";//测试地址
            "http://106.12.103.105:9101/yehai-web/";

    //注册接口
    public static final String REGISTER = "register/check";
    //登录接口
    public static final String LOGIN = "user/login";
    /***********新的接口*********/
    //引导图接口
    public static final String GUIDE_BANNER = "ad/getGuideContent";
    //启动页接口
    public static final String LAUNCH_INFO = "ad/getLaunchContent";


}
