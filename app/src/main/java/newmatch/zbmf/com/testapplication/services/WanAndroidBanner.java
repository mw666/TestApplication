package newmatch.zbmf.com.testapplication.services;


import java.util.List;

import io.reactivex.Observable;
import newmatch.zbmf.com.testapplication.entity.BannerService;
import newmatch.zbmf.com.testapplication.net.beans.BaseResponse;
import newmatch.zbmf.com.testapplication.presenter.YeHiBean.GuideBanner;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by pq
 * on 2018/8/23.
 */

public interface WanAndroidBanner {

    //获取晚Android的Banner图  无参数
    @GET("banner/json")
    Call<BannerService> getBanner();

    //加入RxJava配合使用，返回的将是一个Observable对象
    @GET("banner/json")
    Observable<BannerService> getBannerRx();

    @GET("ad/getGuideContent")
    Observable<BaseResponse<List<GuideBanner>>> getLottery();

//    /**
//     * 玩Android注册的数据：
//     * {"data":{"collectIds":[],"email":"","icon":"","id":9829,"password":"123456",
//     * "token":"","type":0,"username":"15558191831"},"errorCode":0,"errorMsg":""}
//     * 在泛型中填入pojo实体类
//     * <p>
//     * 注册、登录时，用的表单提交数据的注解 Field
//     *
//     * @return
//     */
//    //注册
//    @FormUrlEncoded
//    @POST(NetConfigApi.Register/*"user/register"*/)
//    Observable<WAndroidLogin> register(@Field("username") String username,
//                                       @Field("password") String password,
//                                       @Field("repassword") String repassword);

    //登录
    /**
     * {"data":{"collectIds":[],"email":"","icon":"","id":9829,"password":"123456","token":"",
     * "type":0,"username":"15558191831"},"errorCode":0,"errorMsg":""}
     */
//    @FormUrlEncoded
//    @POST(NetConfigApi.Login_in)
//    Observable<IWanLogin> login(@Field("username") String username, @Field("password") String password);

    //文件的上传与下载


}
