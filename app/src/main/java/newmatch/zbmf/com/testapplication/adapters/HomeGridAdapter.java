package newmatch.zbmf.com.testapplication.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.assist.GlideUtil;
import newmatch.zbmf.com.testapplication.base.BaseViewHolder;
import newmatch.zbmf.com.testapplication.entity.BannerService;
import newmatch.zbmf.com.testapplication.interfaces.DianZanClickListener;
import newmatch.zbmf.com.testapplication.interfaces.HomeRVIvClick;
import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;

/**
 * Created by **
 * on 2018/9/13.
 */

public class HomeGridAdapter extends RecyclerView.Adapter<HomeGridAdapter.HomeGDHolder> {

    private Context mContext;
    private Activity mActivity;
    private List<BannerService.Data> mData;
    private HomeRVIvClick mHomeRVIvClick;
    private DianZanClickListener mDianZanClickListener;

    public void setDianZan(DianZanClickListener dianZan) {
        this.mDianZanClickListener = dianZan;
    }

    public void setHomeRVIvClick(HomeRVIvClick homeRVIvClick) {
        this.mHomeRVIvClick = homeRVIvClick;
    }

    public HomeGridAdapter(Context context, Activity activity) {
        this.mContext = context;
        this.mActivity = activity;
        mData = new ArrayList<>();
    }

    public void addImgList(List<BannerService.Data> imgs) {
        if (imgs != null && imgs.size() > 0) {
            mData.addAll(imgs);
            notifyDataSetChanged();
        }
    }

    //清空所有的数据
    public void clearImg() {
        mData.clear();
//        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeGDHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeGDHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.home_rv_iv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeGDHolder holder, @SuppressLint("RecyclerView") int position) {
        int i = position % mData.size();
        GlideUtil.loadImage(mContext, R.drawable.place_holder_img, mData.get(i).getImagePath(), holder.mRv_iv);
//        holder.mRv_iv.setImageResource(R.drawable.mn9);
        holder.mRv_iv.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (mHomeRVIvClick != null) {
                    mHomeRVIvClick.rvIvCallBack(position);
                }
            }
        });
        holder.mDianZanIv.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (mDianZanClickListener != null) {
                    mDianZanClickListener.dianZanClick(holder.mDianZanIv,holder.mMoodsValue);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size() * 2;
    }

    static class HomeGDHolder extends BaseViewHolder {

        private final ImageView mRv_iv, mDianZanIv;
        private final TextView mUserNick, mLineStatus, mDistance, mMoodsValue;

        HomeGDHolder(View itemView) {
            super(itemView);
            mRv_iv = bindView(R.id.rv_iv);
            mUserNick = bindView(R.id.userNick);
            mLineStatus = bindView(R.id.lineStatus);
            mDistance = bindView(R.id.distance);
            mDianZanIv = bindView(R.id.dianZanIv);
            mMoodsValue = bindView(R.id.moodsValue);


        }
    }
}
