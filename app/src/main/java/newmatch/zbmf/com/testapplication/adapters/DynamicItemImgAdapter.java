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

import newmatch.zbmf.com.testapplication.GMClass.GMSetViewLayoutParams;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.assist.GlideUtil;
import newmatch.zbmf.com.testapplication.interfaces.ShowClickIv;
import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;

/**
 * Created by **
 * on 2018/10/10.
 * 动态Item图片网格列表的adapter
 */

public class DynamicItemImgAdapter extends RecyclerView.Adapter<DynamicItemImgAdapter.DynamicItemImgHolder>{

    private Context mContext;
    private int imgCount=5;//模拟图片的数量
    public DynamicItemImgAdapter(Context context){
        mContext=context;
    }
    private ShowClickIv mShowClickIv;

    public void setShowClickIv(ShowClickIv showClickIv) {
        mShowClickIv = showClickIv;
    }

    @NonNull
    @Override
    public DynamicItemImgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DynamicItemImgHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dynamic_item_img_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DynamicItemImgHolder holder, int position) {
        //加载本地美女图片
        GlideUtil.loadImageWithLocation(mContext,R.drawable.j1,holder.mRvIv);
        if (imgCount<2){
           GMSetViewLayoutParams.instance().gainScreenW(mContext)
                   .setImageviewLp(mContext,holder.mRvIv,1);
        }else if (imgCount<3){
            GMSetViewLayoutParams.instance().gainScreenW(mContext)
                    .setImageviewLp(mContext,holder.mRvIv,2);
        }else {
            GMSetViewLayoutParams.instance().gainScreenW(mContext)
                    .setImageviewLp(mContext,holder.mRvIv,3);
        }
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
                    mShowClickIv.showClickIv(holder.getAdapterPosition(),holder.mRvIv,uris);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imgCount;
    }

    static class DynamicItemImgHolder extends RecyclerView.ViewHolder{

        private final ImageView mRvIv;

         DynamicItemImgHolder(View itemView) {
            super(itemView);
            mRvIv = itemView.findViewById(R.id.rvIv);
        }
    }

}
