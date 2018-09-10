package newmatch.zbmf.com.testapplication.net.loader;

import io.reactivex.observers.DefaultObserver;

/**
 * Created by **
 * on 2018/8/30.
 * <p>
 * * @param <T> 模板类
 */

public abstract class FileUploadObserver<T> extends DefaultObserver<T> {
    @Override
    public void onNext(T t) {
        //上传的进度
        onUploadSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        //上传发生的错误
    }

    @Override
    public void onComplete() {
        //上传完成
    }

    // 上传成功的回调
    public abstract void onUploadSuccess(T t);

    // 上传失败回调
    public abstract void onUploadFail(Throwable e);

    // 上传进度回调
    public abstract void onProgress(int progress);

    // 监听进度的改变
    public void onProgressChange(long bytesWritten, long contentLength) {
        onProgress((int) (bytesWritten * 100 / contentLength));
    }
}
