package newmatch.zbmf.com.testapplication.net.loader;

import com.facebook.stetho.common.LogUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by pq
 * on 2018/8/30.
 *
 * 上一步定义好了上传的接口，我们最终是要去构造MultipartBody，
 * 这一块就需要跟后台同学进行沟通了，根据接口定义来实现，这里是我们的实现：
 *
 * 意思是根据具体的接口进行具体的配置
 *
 */

public class MultipartBuilder {

    /**
     * 单文件上传构造.
     *
     * @param file 文件
     * @param requestBody 请求体
     * @return MultipartBody
     */
    public static MultipartBody fileToMultipartBody(File file, RequestBody requestBody) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        //构造一个JsonObject对象--->根据接口的实际情况
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("fileName", file.getName());
//        jsonObject.addProperty("fileSha", Utils.getFileSha1(file));
//        jsonObject.addProperty("appId", "test0002");

        builder.addFormDataPart("file", file.getName(), requestBody);

        builder.addFormDataPart("params", jsonObject.toString());
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }
    /**
     * 多文件上传构造.
     *
     * @param files 文件列表
     * @param fileUploadObserver 文件上传回调
     * @return MultipartBody
     */
    public static MultipartBody filesToMultipartBody(List<File> files,
                                                     FileUploadObserver<ResponseBody> fileUploadObserver) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        JsonArray jsonArray = new JsonArray();

        Gson gson = new Gson();
        for (File file : files) {
            UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(file, fileUploadObserver);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("fileName", file.getName());
//            jsonObject.addProperty("fileSha", Utils.getFileSha1(file));
//            jsonObject.addProperty("appId", "test0002");

            jsonArray.add(jsonObject);
            LogUtil.d(jsonObject.toString());
            builder.addFormDataPart("file", file.getName(), uploadFileRequestBody);
        }

        builder.addFormDataPart("params", gson.toJson(jsonArray));

        LogUtil.d(gson.toJson(jsonArray));
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }
}
