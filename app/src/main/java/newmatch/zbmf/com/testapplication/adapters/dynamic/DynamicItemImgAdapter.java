package newmatch.zbmf.com.testapplication.adapters.dynamic;

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

import newmatch.zbmf.com.testapplication.GMClass.GMSetViewLayoutParams;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.assist.GlideUtil;
import newmatch.zbmf.com.testapplication.callback.ShowClickIv;
import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;

/**
 * Created by **
 * on 2018/10/10.
 * 动态Item图片网格列表的adapter
 */

public class DynamicItemImgAdapter extends RecyclerView.Adapter<DynamicItemImgAdapter.DynamicItemImgHolder> {

    private Context mContext;
    private int type;//标记RV的类型
    /*模拟的图片数据*/
    private List<Integer> imgs;

    public DynamicItemImgAdapter(Context context, int type, List<Integer> myImgs) {
        this.mContext = context;
        this.type = type;
        this.imgs = myImgs;
    }

    /*设置点击RV的图片的点击事件*/
    private ShowClickIv mShowClickIv;

    public void setShowClickIv(ShowClickIv showClickIv) {
        mShowClickIv = showClickIv;
    }

    @NonNull
    @Override
    public DynamicItemImgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DynamicItemImgHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dynamic_item_img_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DynamicItemImgHolder holder, int position) {
        if (type == 1) {
            GMSetViewLayoutParams.instance().setImageviewLp(mContext, holder.mRvIv, 1);
        } else if (type == 2) {
            GMSetViewLayoutParams.instance().setImageviewLp(mContext, holder.mRvIv, 2);
        } else if (type == 3) {
            GMSetViewLayoutParams.instance().setImageviewLp(mContext, holder.mRvIv, 3);
        }
        //加载本地美女图片
        GlideUtil.loadImageWithLocation(mContext, imgs.get(position), holder.mRvIv);

        //设置点击查看图片
        holder.mRvIv.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (mShowClickIv != null) {
                    List<Uri> uris = new ArrayList<>();
                    //以下是添加图片的uri
                   /* for (int i = 0; i < mData.size(); i++) {
                        uris.add(Uri.parse(mData.get(i).getImagePath()));
                    }*/
                    mShowClickIv.showClickIv(holder.getAdapterPosition(), holder.mRvIv, uris);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imgs == null ? 0 : imgs.size();
    }

    static class DynamicItemImgHolder extends RecyclerView.ViewHolder {

        private final ImageView mRvIv;

        DynamicItemImgHolder(View itemView) {
            super(itemView);
            mRvIv = itemView.findViewById(R.id.rvIv);
        }
    }

}
