package newmatch.zbmf.com.testapplication.mvp.requests;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import newmatch.zbmf.com.testapplication.entity.RegisterBean;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.EmptyData;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.GuideBanner;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.OfficialHomeBanner;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.UserAllInfo;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.UserBanner;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.UserHomeShow;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.UserInfo;
import newmatch.zbmf.com.testapplication.mvp.YeHiBean.YeHiLaunch;
import newmatch.zbmf.com.testapplication.net.NetConfigApi;
import newmatch.zbmf.com.testapplication.net.beans.BaseResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by pq
 * on 2018/11/15.
 * 甜甜圈的各种请求
 */

public interface YeHiRequests {

    /*
     * 示例   GET
     * */
    //加入RxJava配合使用，返回的将是一个Observable对象
    //    @GET("banner/json")
    //    Observable<BannerService> getBannerRx();

    /**
     * 示例   POST
     * <p>
     * 玩Android注册的数据：
     * {"data":{"collectIds":[],"email":"","icon":"","id":9829,"password":"123456",
     * "token":"","type":0,"username":"15558191831"},"errorCode":0,"errorMsg":""}
     * 在泛型中填入pojo实体类
     * <p>
     * 注册、登录时，用的表单提交数据的注解 Field
     *
     * @return
     */
    //注册
    //    @FormUrlEncoded
    //    @POST("user/register")
    //    Observable<WAndroidLogin> register(@Field("username") String username,
    //                                       @Field("password") String password,
    //                                       @Field("repassword") String repassword);

    //注册
    @FormUrlEncoded
    @POST(NetConfigApi.REGISTER)
    Observable<RegisterBean> register(@Field("appkey") String appkey,
                                      @Field("phone") String phone,
                                      @Field("zone") String zone,
                                      @Field("code") String code);

    //登录 http://106.12.103.105:9101/yehai-web/user/login?phone=21474836474&&password=yh123456
    @GET(NetConfigApi.LOGIN+"?{phone}&&{password}")
    Observable<BaseResponse<UserInfo>> login(@Field("phone") String phone,
                                             @Field("password") String password);

    //上传用户信息
    @Multipart
    @POST(NetConfigApi.LAUNCH_INFO)
    Observable<BaseResponse<EmptyData>> upUserInfo(@Part("files") List<File> files,
                                                   @Part("tbUser") UserInfo tbUser);

    //引导图接口
    @GET(NetConfigApi.GUIDE_BANNER)
    Observable<BaseResponse<List<GuideBanner>>> guidePic();

    //启动页接口
    @GET(NetConfigApi.LAUNCH_INFO)
    Observable<BaseResponse<YeHiLaunch>> launchInfo();

    //首页官方的轮播图
    @GET(NetConfigApi.OFFICIAL_BANNER)
    Observable<BaseResponse<List<OfficialHomeBanner>>> officialBanner();

    //用户的轮播图
    @GET(NetConfigApi.USER_BANNER)
    Observable<BaseResponse<List<UserBanner>>> userBanner();

    //重置密码
    @FormUrlEncoded
    @POST(NetConfigApi.RESET_PASSWORD)
    Observable<BaseResponse<EmptyData>> resetPassword(@Field("appkey") String appkey,
                                                      @Field("phone") String phone,
                                                      @Field("code") String code,
                                                      @Field("userId") String userId,
                                                      @Field("password") String password);

    //获取用户展示列表
    @GET(NetConfigApi.HOME_USER_SHOW)
    Observable<BaseResponse<UserHomeShow>> homeUserShow(@Field("pageNum") int pageNum,
                                                        @Field("pageSize") int pageSize,
                                                        @Field("address") String address,
                                                        @Field("type") int type);

    //获取用户的所有信息
    @GET(NetConfigApi.USER_ALL_INFO)
    Observable<BaseResponse<UserAllInfo>> getUserAllInfo(@Field("userid") int userid);

}
