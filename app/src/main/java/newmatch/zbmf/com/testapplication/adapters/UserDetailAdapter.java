package newmatch.zbmf.com.testapplication.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.utils.glidUtils.GlideUtil;
import newmatch.zbmf.com.testapplication.entity.BannerService;
import newmatch.zbmf.com.testapplication.callback.ShowClickIv;
import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;

/**
 * Created by **
 * on 2018/9/20.
 */

public class UserDetailAdapter extends RecyclerView.Adapter<UserDetailAdapter.UserDetailHolder> {
    private Context mContext;
    private List<BannerService.Data> mData;
    private ShowClickIv mShowClickIv;

    public void setShowClickIv(ShowClickIv showClickIv) {
        mShowClickIv = showClickIv;
    }

    //模拟数据
    public UserDetailAdapter(Context context) {
        this.mContext = context;
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
    public UserDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserDetailHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.user_detail_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserDetailHolder holder, int position) {
        GlideUtil.loadImage(mContext, R.drawable.place_holder_img, mData.get(position).getImagePath()
                , holder.mUser_rv_iv);
        holder.mUser_rv_iv.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (mShowClickIv != null) {
                    List<Uri> uris = new ArrayList<>();
                    for (int i = 0; i < mData.size(); i++) {
                        uris.add(Uri.parse(mData.get(i).getImagePath()));
                    }
                    mShowClickIv.showClickIv(holder.getAdapterPosition(), holder.mUser_rv_iv, uris);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class UserDetailHolder extends RecyclerView.ViewHolder {

        private final ImageView mUser_rv_iv;

        public UserDetailHolder(View itemView) {
            super(itemView);
            mUser_rv_iv = itemView.findViewById(R.id.user_rv_iv);

        }
    }

}
