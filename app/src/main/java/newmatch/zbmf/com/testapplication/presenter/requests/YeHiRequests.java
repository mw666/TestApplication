package newmatch.zbmf.com.testapplication.presenter.requests;

import java.util.List;

import io.reactivex.Observable;
import newmatch.zbmf.com.testapplication.entity.RegisterBean;
import newmatch.zbmf.com.testapplication.net.NetConfigApi;
import newmatch.zbmf.com.testapplication.net.beans.BaseResponse;
import newmatch.zbmf.com.testapplication.presenter.YeHiBean.GuideBanner;
import newmatch.zbmf.com.testapplication.presenter.YeHiBean.YeHiLaunch;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by pq
 * on 2018/11/15.
 * 甜甜圈的各种请求
 *
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
     *
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
    Observable<RegisterBean> register(@Field("appkey")String appkey,
                                      @Field("phone")String phone,
                                      @Field("zone")String zone,
                                      @Field("code")String code);

    //引导图接口
    @GET(NetConfigApi.GUIDE_BANNER)
    Observable<BaseResponse<List<GuideBanner>>> guidePic();

    //启动页接口
    @GET(NetConfigApi.LAUNCH_INFO)
    Observable<BaseResponse<List<YeHiLaunch>>> lunchInfo();




}
