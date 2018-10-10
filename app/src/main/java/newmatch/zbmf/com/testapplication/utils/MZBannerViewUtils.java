package newmatch.zbmf.com.testapplication.utils;

import java.util.List;

import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.listeners.BannerViewHolder;
import newmatch.zbmf.com.testapplication.views.MZBannerView;

/**
 * Created by **
 * on 2018/9/26.
 */

public class MZBannerViewUtils {

    public static void clickMZBannerView(MZBannerView bannerView) {
        bannerView.setBannerPageClickListener((view, position) -> {
            ToastUtils.showSingleToast(MyApplication.getInstance(), "点击了MZBannerView   哟哟" + position);
        });
    }

    /**
     * 传入图片的资源集合
     * @param isShowIndicator  是否显示指示器
     * @param bannerView   bannerView
     * @param bannerList   图片集合
     */
    public static void bannerPageClick(Boolean isShowIndicator,MZBannerView bannerView, List<Integer> bannerList) {
        bannerView.setIndicatorVisible(isShowIndicator);
        //传进参赛单位的图片数量
        bannerView.setPages(bannerList, imgs -> {
            BannerViewHolder bannerViewHolder = new BannerViewHolder(imgs);
            //设置Banner的点击事件
            bannerViewHolder.setBannerClickListener((view, position) -> {
                ToastUtils.showSingleToast(MyApplication.getInstance(), "点击了MZBannerView " + position);
            });
            return bannerViewHolder;
        });
    }

}
