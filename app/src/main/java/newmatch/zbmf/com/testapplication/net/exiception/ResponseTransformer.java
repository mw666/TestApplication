package newmatch.zbmf.com.testapplication.net.exiception;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import newmatch.zbmf.com.testapplication.mvp.requests.ErrorCallBack;
import newmatch.zbmf.com.testapplication.net.beans.BaseResponse;

/**
 * Created By pq
 * on 2019/7/31
 */
public class ResponseTransformer {

    private static ErrorCallBack ErrorCallBack;

    public static <T> ObservableTransformer<BaseResponse<T>, T>
    handleResult(ErrorCallBack errorCallBack) {
        ErrorCallBack = errorCallBack;
        return upstream -> upstream
                .onErrorResumeNext(new ErrorResumeFunction<>())
                .flatMap(new ResponseFunction<>());
    }


    /**
     * 非服务器产生的异常，比如本地无无网络请求，Json数据解析错误等等。
     *
     * @param <T>
     */
    private static class ErrorResumeFunction<T> implements Function<Throwable,
            ObservableSource<? extends BaseResponse<T>>> {

        @Override
        public ObservableSource<? extends BaseResponse<T>> apply(Throwable throwable) throws Exception {
            return Observable.error(CustomException.handleException(throwable));
        }
    }

    /**
     * 服务其返回的数据解析
     * 正常服务器返回数据和服务器可能返回的exception
     *
     * @param <T>
     */
    private static class ResponseFunction<T> implements Function<BaseResponse<T>, ObservableSource<T>> {

        @Override
        public ObservableSource<T> apply(BaseResponse<T> tResponse) throws Exception {
            int code = tResponse.getCode();
            String message = tResponse.getMsg();
            //返回正常的数据结果
            if (code == 200) {
                return Observable.just(tResponse.getData());
            } else {
                ErrorCallBack.errorCallBack(code, message);
                return Observable.error(new ApiException(code, message));
            }
        }
    }

}