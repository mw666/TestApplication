package newmatch.zbmf.com.testapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.SparseArray;
import android.widget.ImageView;

import com.github.ielse.imagewatcher.ImageWatcher;
import com.github.ielse.imagewatcher.ImageWatcherHelper;

import java.util.List;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.assist.SimpleLoader;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.component.PLog;
import newmatch.zbmf.com.testapplication.dialogs.SheetDialog;

/**
 * Created by **
 * on 2018/9/26.
 * 图片查看的工具类
 */

public class ShowImgUtils {

    private static ImageWatcherHelper sIwHelper;

    public static ImageWatcherHelper init(Activity activity) {
        sIwHelper = ImageWatcherHelper.with(activity, new SimpleLoader());
        // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
        sIwHelper.setErrorImageRes(R.mipmap.error_picture);
        return sIwHelper;
    }

    public static void showImgs(ImageView clickedImage, List<Uri> dataList) {
        SparseArray<ImageView> mapping = new SparseArray<>(); // 这个请自行理解，
        mapping.put(0, clickedImage);
        sIwHelper.show(clickedImage, mapping, dataList);
    }

    //长按图片
    public static void picLongPressClick(Context context, ImageWatcherHelper iwHelper) {
        iwHelper.setOnPictureLongPressListener((imageView, uri, i) -> new SheetDialog.Builder(context)
                //dialog 点击后还差具体操作的逻辑--->使用回调方法，外调出去
                .addSheet("发送给好友", (dialog, which) -> {
                    ToastUtils.showSingleToast(MyApplication.getInstance(), "具体操作");
                    dialog.dismiss();
                })
                .addSheet("转载到空间相册", (dialog, which) -> dialog.dismiss())
                .addSheet("保存到手机", (dialog, which) -> dialog.dismiss())
                .addSheet("收藏", (dialog, which) -> dialog.dismiss()).create().show());
    }

    //监听具体的改变
    public static void iwHelperStateChange(ImageWatcherHelper iwHelper) {
        iwHelper.setOnStateChangedListener(new ImageWatcher.OnStateChangedListener() {
            @Override
            public void onStateChangeUpdate(ImageWatcher imageWatcher, ImageView clicked,
                                            int position, Uri uri, float animatedValue,
                                            int actionTag) {
                PLog.e("IW", "onStateChangeUpdate [" + position + "][" + uri
                        + "][" + animatedValue + "][" + actionTag + "]");
            }

            @Override
            public void onStateChanged(ImageWatcher imageWatcher, int position, Uri uri, int actionTag) {
                if (actionTag == ImageWatcher.STATE_ENTER_DISPLAYING) {
                    ToastUtils.showSingleToast(MyApplication.getInstance(), "点击了图片 ["
                            + position + "]" + uri + "");
                } else if (actionTag == ImageWatcher.STATE_EXIT_HIDING) {
                    ToastUtils.showSingleToast(MyApplication.getInstance(), "退出了查看大图");
                }
            }
        });
    }

}
