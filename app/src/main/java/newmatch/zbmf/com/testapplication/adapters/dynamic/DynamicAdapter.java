package newmatch.zbmf.com.testapplication.adapters.dynamic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import newmatch.zbmf.com.testapplication.GMClass.GMRvSetLayoutManager;
import newmatch.zbmf.com.testapplication.GMClass.GMTextSetIcon;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.utils.glidUtils.GlideUtil;
import newmatch.zbmf.com.testapplication.callback.CommentArrowCallBack;
import newmatch.zbmf.com.testapplication.callback.CommentListCallBack;
import newmatch.zbmf.com.testapplication.callback.LikeCallBack;
import newmatch.zbmf.com.testapplication.callback.SkipUserDetailCallBack;
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
    private CommentListCallBack commentListCallBack;
    private SkipUserDetailCallBack skipUserDetailCallBack;


    /*模拟图片数据*/
    private List<Integer> img1 = Arrays.asList(new Integer[]{R.drawable.m1});
    private List<Integer> img2 = Arrays.asList(new Integer[]{R.drawable.m2, R.drawable.m4});
    private List<Integer> img3 = Arrays.asList(new Integer[]{R.drawable.m4, R.drawable.m3, R.drawable.m5});
    private List<Integer> img4 = Arrays.asList(new Integer[]{R.drawable.m7, R.drawable.m1,
            R.drawable.m6, R.drawable.m7, R.drawable.m3, R.drawable.m1, R.drawable.m3, R.drawable.m2});
    private List<Integer> img;

    public void setCommentDynamic(CommentDynamic commentDynamic) {
        mCommentDynamic = commentDynamic;
        /*模拟图片数据*/
        img = new ArrayList<>();
        img.add(img1.size());
        img.add(img2.size());
        img.add(img3.size());
        img.add(img4.size());

    }

    public void setLikeCallBack(LikeCallBack likeCallBack) {
        mLikeCallBack = likeCallBack;
    }

    public void setCommentArrowCallBack(CommentArrowCallBack commentArrowCallBack) {
        mCommentArrowCallBack = commentArrowCallBack;
    }

    public void setCommentListCallBack(CommentListCallBack commentListCallBack) {
        this.commentListCallBack = commentListCallBack;
    }

    public void setSkipUserDetailCallBack(SkipUserDetailCallBack skipUserDetailCallBack) {
        this.skipUserDetailCallBack = skipUserDetailCallBack;
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
        GlideUtil.loadCircleImage(mContext, R.drawable.touxiang_icon, R.drawable.m1, holder.mDyUserAvatar);
        //设置发布动态用户的性别
        //模拟设置为女
        GlideUtil.loadImageWithLocation(mContext, R.drawable.woman_sex_icon, holder.mDySexIv);
        //设置发布动态用户的昵称
        holder.mDyUserName.setText("维纳斯的忧伤");
        //设置用户的动态文字内容
        holder.mDyUserContentTv.setText("当魁拔之歌再次响起的时候，战神必将再次归来，我们的魁拔！");
        //设置定位的tx
        holder.mDyUserLocationTv.setText("浙江省台州市温岭");
        //设置定位图标
        GMTextSetIcon.setTvLeftIcon(mContext, R.drawable.location_icon,
                2, holder.mDyUserLocationTv);
        //设置该条动态发布距离当前的时间
        holder.mDyContentTime.setText("3小时前");
        //设置动态发布者距离当前用户的位置距离
        holder.mDyDistanceTv.setText("17.9Km");
        //设置评论的数量
        holder.mCommentCountIcon.setText("5");
        //设置评论的Icon
        GMTextSetIcon.setTvLeftIcon(mContext, R.drawable.comment_icon,
                3, holder.mCommentCountIcon);
        //设置浏览的数量
        holder.mSeeCountTfv.setText("234");
        //设置浏览的Icon
        GMTextSetIcon.setTvLeftIcon(mContext, R.drawable.see_icon,
                3, holder.mSeeCountTfv);
        //设置点赞的数量
        holder.mDyLikeContentTv.setText("2264");

        /*设置动态图片的recyclerView的数据*/
        //发布的动态的图片列表
        holder.mDyUserRV.setNestedScrollingEnabled(false);
        int type = 0;
        DynamicItemImgAdapter imgAdapter;

        if (img.get(position) <= 1) {
            type = 1;
            GMRvSetLayoutManager.setGridLayoutManager(mContext, holder.mDyUserRV, type);
            //给动态图片列表设置Adapter
            imgAdapter = new DynamicItemImgAdapter(mContext, type, img1);
        } else if (img.get(position) <= 4) {
            type = 2;
            GMRvSetLayoutManager.setGridLayoutManager(mContext, holder.mDyUserRV, type);
            imgAdapter = new DynamicItemImgAdapter(mContext, type, img2);
        } else {
            type = 3;
            GMRvSetLayoutManager.setGridLayoutManager(mContext, holder.mDyUserRV, type);
            if (position == 2) {
                imgAdapter = new DynamicItemImgAdapter(mContext, type, img3);
            } else {
                imgAdapter = new DynamicItemImgAdapter(mContext, type, img4);
            }
        }
        holder.mDyUserRV.setAdapter(imgAdapter);
        //展示图片
        imgAdapter.setShowClickIv(ShowImgUtils::showImgs);

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
        //点击弹出评论输入框
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
                if (mCommentArrowCallBack != null) {
                    mCommentArrowCallBack.arrowClickCallBack(holder.getAdapterPosition());
                }
            }
        });
        //点击展示评论列表-->由下方弹出评论的对话框
        holder.mCommentCountIcon.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (commentListCallBack != null)
                    commentListCallBack.commentListCallback();
            }
        });
        //点击该用户头像跳转该用户个人中心页面
        holder.mDyUserAvatar.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (skipUserDetailCallBack != null)
                    skipUserDetailCallBack.skipUserDetailCallBack(0);
            }
        });

    }

    @Override
    public int getItemCount() {
        return img.size();
    }

    static class DyViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout mCommentBtnRL;
        private final ImageView mDyUserAvatar, mDyDropTopArrow, mDySexIv;
        private final TextView mDyUserName, mDyUserContentTv, mDyUserLocationTv, mDyContentTime,
                mDyDistanceTv, mCommentCountIcon, mSeeCountTfv, mDyLikeContentTv;
        private final RecyclerView mDyUserRV, mCommentRV;

        DyViewHolder(View itemView) {
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
