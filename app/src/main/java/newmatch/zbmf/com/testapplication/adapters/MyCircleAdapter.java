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
import newmatch.zbmf.com.testapplication.utils.GetUIDimens;

/**
 * Created By pq
 * on 2019/5/25
 * GridView的Adapter，3列
 */
public class MyCircleAdapter extends RecyclerView.Adapter<MyCircleAdapter.CircleHolder> {

    private List<Uri> imgUriList;
    private Context mContext;

    //最多只显示9张图

    public MyCircleAdapter(Context context) {
        this.mContext = context;
        imgUriList = new ArrayList<>();
    }

    public void clearData() {
        if (imgUriList != null && imgUriList.size() > 0)
            imgUriList.clear();
//        notifyDataSetChanged();
    }

    public void addData(List<Uri> newData) {
//        if (imgUriList != null && newData != null && newData.size() > 0) {
        imgUriList.addAll(newData);
//        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CircleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CircleHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_left_item_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CircleHolder circleHolder, int i) {
        int windowW = GetUIDimens.getWindowW(mContext);
        ViewGroup.LayoutParams lp = circleHolder.iv.getLayoutParams();
        lp.width = (windowW - 180) / 3;
        lp.height = (windowW - 180) / 3;
        circleHolder.iv.setLayoutParams(lp);
        GlideUtil.loadImage(mContext,R.drawable.loading1,imgUriList.get(i),circleHolder.iv);

    }

    @Override
    public int getItemCount() {
        if (imgUriList.size() >= 9) {
            return 9;
        } else {
            return imgUriList.size();
        }
    }

    static class CircleHolder extends RecyclerView.ViewHolder {

        private final ImageView iv;

        CircleHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
        }
    }
}
