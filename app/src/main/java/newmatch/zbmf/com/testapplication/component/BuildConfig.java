package newmatch.zbmf.com.testapplication.component;

/**
 * Created by **
 * on 2018/8/28.
 * 所有常量存放的类
 */

public final class BuildConfig {
    public static final boolean DEBUG = Boolean.parseBoolean("true");
    //mob的 appkey
    public static final String MOB_APPKEY="28b730acbedd5";

    public static final String APPLICATION_ID = "com.xiecc.seeWeather";
    public static final String BUILD_TYPE = "debug";
    public static final String FLAVOR = "";
    public static final int VERSION_CODE = 34;
    public static final String VERSION_NAME = "2.3.1";

    public static final String TAG_D="====TianQuan";

    //页面间传值的Key
    public static final String TAB_POSITION="tab_position";
    //用户信息页面跳转城市选择页面的请求码
    public static final Integer USERINFO_SKIP_CITYS=3001;

    public static final String PROVINCE="province";//省
    public static final String CITY="locality";//城市
    public static final String GET_CITY="get_locality";
    public static final String SUB_LOCALITY="subLocality";//区/镇
    public static final String GET_SUB_LOCALITY="get_subLocality";
    public static final String FEATURE_NAME="featureName";//详细的街道地址
    public static final String GET_FEATURE_NAME="get_featureName";
    public static final String CITY_TO_USERINFO="city_userinfo";



}
