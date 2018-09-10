package newmatch.zbmf.com.testapplication.interfaces;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * Created by **
 * on 2018/8/30.
 *
 * 模拟上传图片的Api
 *
 */

public interface UpLoadFileApi {
    //上传图片的地址
    String UPLOAD_FILE_URL = "https://passport.zbmf.com/rest/json/zbmf.users.uploadImg";

    @Multipart
    @POST
    Observable<ResponseBody> uploadFile(@Url String method, @Body MultipartBody body);


    /*上传文件
    *
    * 注意：Multipart是指定大文件上传过程中的标示，一般上传图片的过程中我们需要附带信息，
    * 所以我们需要用到@part指定传递的数值，MultipartBody.Part是指定传递的文件；
    *
    * */
    @Multipart
    @POST("AppYuFaKu/uploadHeadImg")
    Observable<ResponseBody> uploadImage(@Part("method") String url,
                                         @Part("auth_token") String auth_token,
                                         @Part MultipartBody.Part file);

}
