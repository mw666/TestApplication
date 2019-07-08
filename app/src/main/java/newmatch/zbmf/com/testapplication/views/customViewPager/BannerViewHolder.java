package newmatch.zbmf.com.testapplication.views.customViewPager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.assist.GlideUtil;
import newmatch.zbmf.com.testapplication.component.BannerViewHolderType;


/**
 * Created by **
 * on 2018/3/20.
 */

public class BannerViewHolder implements MZViewHolder<Integer> {
    private ImageView mImageView;
    //    private TextView mMTv;
    private int imgs;
    private BannerPageClickListener mBannerPageClickListener;
    private BannerViewHolderType bannerViewHolderType;

    public BannerViewHolder(int imgs, BannerViewHolderType bannerViewHolderType) {
        this.imgs = imgs;
        this.bannerViewHolderType=bannerViewHolderType;
    }

    public void setBannerClickListener(BannerPageClickListener bannerClickListener) {
        this.mBannerPageClickListener = bannerClickListener;
    }

    @Override
    public View createView(Context context) {
        // 返回页面布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.chat_recommend_item_view, null);
        mImageView = view.findViewById(R.id.chatRecommendItemIv);
//        mMTv = (TextView) view.findViewById(R.id.banner_tv);
        return view;
    }

    @Override
    public void onBind(Context context, int position, Integer data) {
        // 数据绑定
        if (bannerViewHolderType==BannerViewHolderType.CircleViewHolder){
            GlideUtil.loadCircleImage(context, R.drawable.loading1, data, mImageView);
        }else if (bannerViewHolderType==BannerViewHolderType.ConnerViewHolder){
            GlideUtil.loadCornerdImg(context, data,R.drawable.loading1,
                    mImageView,true,true,true,true);
        }else if (bannerViewHolderType==BannerViewHolderType.RectViewHolder){
            GlideUtil.loadImage(context,R.drawable.loading1,data,mImageView);
        }
        mImageView.setOnClickListener(v -> {
            int i = position % imgs;
            if (mBannerPageClickListener != null) {
                mBannerPageClickListener.onPageClick(v, i);
            }
        });
    }

}