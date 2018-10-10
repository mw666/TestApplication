package newmatch.zbmf.com.testapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import newmatch.zbmf.com.testapplication.GMClass.GMRvSetLayoutManager;
import newmatch.zbmf.com.testapplication.GMClass.GMTextSetIcon;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.assist.GlideUtil;
import newmatch.zbmf.com.testapplication.interfaces.CommentArrowCallBack;
import newmatch.zbmf.com.testapplication.interfaces.LikeCallBack;
import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;
import newmatch.zbmf.com.testapplication.utils.ShowImgUtils;

/**
 * Created by **
 * on 2018/9/25.
 */

public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.DyViewHolder> {

    private Context mContext;
    private CommentDynamic mCommentDynamic;
    private LikeCallBack mLikeCallBack;
    private CommentArrowCallBack mCommentArrowCallBack;

    private Integer spanCount = 1;//模拟标记动态的网格数量---默认值为1

    public void setCommentDynamic(CommentDynamic commentDynamic) {
        mCommentDynamic = commentDynamic;
    }

    public void setLikeCallBack(LikeCallBack likeCallBack) {
        mLikeCallBack = likeCallBack;
    }

    public void setCommentArrowCallBack(CommentArrowCallBack commentArrowCallBack){
        mCommentArrowCallBack=commentArrowCallBack;
    }

    public DynamicAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public DyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DyViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.dynamic_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DyViewHolder holder, int position) {
        //设置动态的用户的图片
        //模拟-----先加载本地
        GlideUtil.loadCircleImage(mContext, R.drawable.touxiang_icon, R.drawable.mn9, holder.mDyUserAvatar);
        //设置发布动态用户的性别
        //模拟设置为女
        GlideUtil.loadImageWithLocation(mContext, R.drawable.woman_sex_icon, holder.mDySexIv);
        //设置发布动态用户的昵称
        holder.mDyUserName.setText("仙女儿");
        //设置用户的动态文字内容
        holder.mDyUserContentTv.setText("我是最美的小仙女儿。。。");
        //设置定位的tx
        holder.mDyUserLocationTv.setText("浙江省杭州市小和山水沟沟螃蟹扎");
        //设置定位图标
        GMTextSetIcon.setTvLeftIcon(mContext, R.drawable.location_icon, holder.mDyUserLocationTv);
        //设置该条动态发布距离当前的时间
        holder.mDyContentTime.setText("3小时前");
        //设置动态发布者距离当前用户的位置距离
        holder.mDyDistanceTv.setText("17.9Km");
        //设置评论的数量
        holder.mCommentCountIcon.setText("5");
        //设置评论的Icon
        GMTextSetIcon.setTvLeftIcon(mContext, R.drawable.comment_icon, holder.mCommentCountIcon);
        //设置浏览的数量
        holder.mSeeCountTfv.setText("234");
        //设置浏览的Icon
        GMTextSetIcon.setTvLeftIcon(mContext, R.drawable.see_icon, holder.mSeeCountTfv);
        //设置点赞的数量
        holder.mDyLikeContentTv.setText("234");
        //发布的动态的图片列表
        holder.mDyUserRV.setNestedScrollingEnabled(false);
        GMRvSetLayoutManager.setGridLayoutManager(mContext, holder.mDyUserRV, 3/*spanCount*/);
        //给动态图片列表设置Adapter
        DynamicItemImgAdapter imgAdapter = new DynamicItemImgAdapter(mContext/*,图片数据*/);
        imgAdapter.setShowClickIv((pos, imageView, dataList) -> {
            //展示图片
            ShowImgUtils.showImgs(imageView, dataList);
        });
        holder.mDyUserRV.setAdapter(imgAdapter);
        //设置评论
        GMRvSetLayoutManager.setLinearLayoutManager(mContext, holder.mCommentRV);
        holder.mCommentRV.setNestedScrollingEnabled(false);
        holder.mCommentRV.setAdapter(new DynamicCommentAdapter());
        //设置点赞的监听
        holder.mDyLikeContentTv.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (mLikeCallBack != null) {
                    mLikeCallBack.likeCallBack(holder.getAdapterPosition(), holder.mDyLikeContentTv);
                }
            }
        });
        holder.mCommentBtnRL.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (mCommentDynamic != null) {
                    mCommentDynamic.commentDynamic(holder.getAdapterPosition());
                }
            }
        });
        //设置下拉按钮点击
        holder.mDyDropTopArrow.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (mCommentArrowCallBack!=null){
                    mCommentArrowCallBack.arrowClickCallBack(holder.getAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    static class DyViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout mCommentBtnRL;
        private final ImageView mDyUserAvatar, mDyDropTopArrow, mDySexIv;
        private final TextView mDyUserName, mDyUserContentTv, mDyUserLocationTv, mDyContentTime,
                mDyDistanceTv, mCommentCountIcon, mSeeCountTfv, mDyLikeContentTv;
        private final RecyclerView mDyUserRV, mCommentRV;

        public DyViewHolder(View itemView) {
            super(itemView);
            mCommentBtnRL = itemView.findViewById(R.id.commentBtnRL);
            //用户的动态头像
            mDyUserAvatar = itemView.findViewById(R.id.dyUserAvatar);
            //动态用户的昵称
            mDyUserName = itemView.findViewById(R.id.dyUserName);
            //动态用户的性别图标
            mDySexIv = itemView.findViewById(R.id.dySexIv);
            //动态的下拉对话框
            mDyDropTopArrow = itemView.findViewById(R.id.dyDropTopArrow);
            //动态的文字
            mDyUserContentTv = itemView.findViewById(R.id.dyUserContentTv);
            //动态的RecyclerView
            mDyUserRV = itemView.findViewById(R.id.dyUserRV);
            //发动态时候的所在地
            mDyUserLocationTv = itemView.findViewById(R.id.dyUserLocationTv);
            //发动态时候距离当前已过的时间
            mDyContentTime = itemView.findViewById(R.id.dyContentTime);
            //发动态的用户距离当前用户的位置距离
            mDyDistanceTv = itemView.findViewById(R.id.dyDistanceTv);
            //评论的数量
            mCommentCountIcon = itemView.findViewById(R.id.commentCountIcon);
            //被浏览的数量
            mSeeCountTfv = itemView.findViewById(R.id.seeCountTv);
            //被点赞的数量
            mDyLikeContentTv = itemView.findViewById(R.id.dyLikeContentTv);
            //评论的列表
            mCommentRV = itemView.findViewById(R.id.commentRV);

        }
    }

    public interface CommentDynamic {

        void commentDynamic(int position);

    }

}
