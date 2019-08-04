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
    //上传用户信息
    public static final String UP_USERINFO = "user/uploadUser";
    //引导图接口
    public static final String GUIDE_BANNER = "ad/getGuideContent";
    //启动页接口
    public static final String LAUNCH_INFO = "ad/getLaunchContent";
    //首页官方的轮播图
    public static final String OFFICIAL_BANNER = "ad/getBannerByMe";
    //用户轮播
    public static final String USER_BANNER = "ad/getBannerByUser";
    //重置用户密码
    public static final String RESET_PASSWORD = "user/resetPassword";
    //获取用户展示列表
    public static final String HOME_USER_SHOW = "user/getShowUser";
    //获取用户所有信息
    public static final String USER_ALL_INFO = "user/getUserAll";

}
