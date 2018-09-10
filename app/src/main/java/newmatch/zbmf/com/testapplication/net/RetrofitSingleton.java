package newmatch.zbmf.com.testapplication.net;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.litesuits.orm.db.assit.WhereBuilder;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.component.BuildConfig;
import newmatch.zbmf.com.testapplication.component.C;
import newmatch.zbmf.com.testapplication.component.OrmLite;
import newmatch.zbmf.com.testapplication.component.PLog;
import newmatch.zbmf.com.testapplication.interfaces.UpLoadFileApi;
import newmatch.zbmf.com.testapplication.net.beans.CityORM;
import newmatch.zbmf.com.testapplication.net.loader.FileUploadObserver;
import newmatch.zbmf.com.testapplication.net.loader.MultipartBuilder;
import newmatch.zbmf.com.testapplication.net.loader.UploadFileRequestBody;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;
import newmatch.zbmf.com.testapplication.utils.Util;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by **
 * on 2018/8/28.
 * 网络请求
 */

public class RetrofitSingleton {
    private static Retrofit sRetrofit = null;
    private static OkHttpClient sOkHttpClient = null;

    private void init() {
        initOkHttp();
        initRetrofit();
//        sApiService = sRetrofit.create(ApiInterface.class);
    }

    private RetrofitSingleton() {
        init();
    }

    public static RetrofitSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final RetrofitSingleton INSTANCE = new RetrofitSingleton();
    }

    private static void initOkHttp() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 缓存 http://www.jianshu.com/p/93153b34310e
        File cacheFile = new File(C.NET_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = chain -> {
            Request request = chain.request();
            //如果网络没有连接
            if (!Util.isNetworkConnected(MyApplication.getInstance())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            Response.Builder newBuilder = response.newBuilder();
            if (Util.isNetworkConnected(MyApplication.getInstance())) {
                int maxAge = 0;
                // 有网络时 设置缓存超时时间0个小时
                newBuilder.header("Cache-Control", "public, max-age=" + maxAge);
            } else {
                // 无网络时，设置超时为4周
                int maxStale = 60 * 60 * 24 * 28;
                newBuilder.header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
            }
            return newBuilder.build();
        };
        builder.cache(cache).addInterceptor(cacheInterceptor);
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //声明日志类
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //设定日志级别
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //配置拦截器
        // 添加公共参数拦截器 ---》可自定义拦截器，添加相同的参数，这样就不用每次都添加形同的参数了
        /**
         * 以上只是配置了一些简单的项，如，连接超时时间，实际项目中，我们可能有一些公共的参数，
         * 如 ，设备信息，渠道，Token 之类的，每个接口都需要用，我们可以写一个拦截器，然后配置
         * 到OKHttpClient里，通过 builder.addInterceptor(basicParamsInterceptor) 添加，这样我们
         * 就不用每个接口都添加这些参数了。缓存也可以通过写一个拦截器来实现
         */
//        BasicParamsInterceptor basicParamsInterceptor = new BasicParamsInterceptor.Builder()
//                .addHeaderParam("userName","")//添加公共参数
//                .addHeaderParam("device","")
//                .build();
        builder.addInterceptor(httpLoggingInterceptor);
        //错误重连
        builder.retryOnConnectionFailure(true);
        sOkHttpClient = builder.build();
    }

    private static void initRetrofit() {
        sRetrofit = new Retrofit.Builder()
                .baseUrl(NetConfigApi.BASE_URL)
                .client(sOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 获取对应的Service  实例
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service) {
        T t = sRetrofit.create(service);
        return t;
    }

    public static Consumer<Throwable> disposeFailureInfo(Throwable t) {
        return throwable -> {
            if (t.toString().contains("GaiException") || t.toString().contains("SocketTimeoutException") ||
                    t.toString().contains("UnknownHostException")) {
                ToastUtils.showSingleToast(MyApplication.getInstance(),"网络问题");
            } else if (t.toString().contains("API没有")) {
                OrmLite.getInstance()
                        .delete(new WhereBuilder(CityORM.class).where("name=?", Util.replaceInfo(t.getMessage())));
                ToastUtils.showSingleToast(MyApplication.getInstance(),"错误: " + t.getMessage());
            }
            PLog.w(t.getMessage());
        };
    }

   /* public Observable<Version> fetchVersion() {
        return sApiService.mVersionAPI(C.API_TOKEN)
                .doOnError(RetrofitSingleton::disposeFailureInfo)
                .compose(RxUtil.io());
    }*/

    /**
     * 单上传文件的封装.
     *
     * @param url 完整的接口地址
     * @param file 需要上传的文件
     * @param fileUploadObserver 上传回调
     */
    public void upLoadFile(String url, File file, FileUploadObserver<ResponseBody> fileUploadObserver) {

        UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(file, fileUploadObserver);

        create(UpLoadFileApi.class)
                .uploadFile(url, MultipartBuilder.fileToMultipartBody(file, uploadFileRequestBody))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(fileUploadObserver);

    }

    /**
     * 多文件上传.
     *
     * @param url 上传接口地址
     * @param files 文件列表
     * @param fileUploadObserver 文件上传回调
     */
    public void upLoadFiles(String url, List<File> files, FileUploadObserver<ResponseBody> fileUploadObserver) {
        create(UpLoadFileApi.class)
                .uploadFile(url, MultipartBuilder.filesToMultipartBody(files, fileUploadObserver))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(fileUploadObserver);

    }
    /**
     * 上传文件具体的使用方法示例：
     *
     * 获取到Retrofit对象的实例，调用上传文件的方法
     */
   /* RetrofitClient.getInstance().upLoadFiles(UploadFileApi.UPLOAD_FILE_URL, files,
                new FileUploadObserver<ResponseBody>() {
        @Override
        public void onUploadSuccess(ResponseBody responseBody) {

            if (responseBody == null) {
                LogUtil.e("responseBody null");
                return;
            }

            try {
                JSONObject jsonObject = new JSONObject(responseBody.string());

                ArrayList<String> fileIds = new ArrayList<String>();
                fileIds.add(jsonObject.getString("fileId"));

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onUploadFail(Throwable e) {
        }

        @Override
        public void onProgress(int progress) {
            LogUtil.d(String.valueOf(progress));
        }
    });*/
}
