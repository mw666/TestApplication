package newmatch.zbmf.com.testapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.assist.GlideUtil;
import newmatch.zbmf.com.testapplication.interfaces.DianZanClickListener;
import newmatch.zbmf.com.testapplication.interfaces.HomeRVIvClick;
import newmatch.zbmf.com.testapplication.utils.GetUIDimens;

/**
 * Create By Administrator
 * on 2019/7/7
 */
public class StaggerAdapter extends RecyclerView.Adapter<StaggerAdapter.Holder> {

    private final int w;
    private Context context;
    private HomeRVIvClick mHomeRVIvClick;
    private DianZanClickListener mDianZanClickListener;
    public void setDianZan(DianZanClickListener dianZan) {
        this.mDianZanClickListener = dianZan;
    }

    public void setHomeRVIvClick(HomeRVIvClick homeRVIvClick) {
        this.mHomeRVIvClick = homeRVIvClick;
    }
    private enum ITEM_TYPE {
        BIG_LEFT,
        BIG_RIGHT,
        SMALL_LEFT,
        SMALL_RIGHT,
        ALONE_BIG
    }

    private List<Integer> imgs;

    public StaggerAdapter(Context context) {
        this.context = context;
        w = GetUIDimens.getWindowW(context);
        imgs = new ArrayList<>();
    }

    public void addData(List<Integer> newImgs) {
        if (newImgs != null && newImgs.size() > 0) {
            this.imgs.addAll(newImgs);
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        if (this.imgs.size() > 0) {
            this.imgs.clear();
        }
    }

    //    @Override
    public int getItemViewType(int position) {
//        int ad = position % 15;
//        if (position != 0 && ad == 0) {
//            return ITEM_TYPE.ALONE_BIG.ordinal();
//        } else {
            int res = position % 3;
            int n = position & 1;
            if ((res == 0 && n == 0)) {
                return ITEM_TYPE.BIG_LEFT.ordinal();
            } else if ((res == 1 && n == 0)) {
                return ITEM_TYPE.BIG_RIGHT.ordinal();
            } else if ((res == 1 && n == 1) || (res == 2 && n == 0)) {
                return ITEM_TYPE.SMALL_RIGHT.ordinal();
            } else if ((res == 0 && n == 1) || (res == 2 && n == 1)) {
                return ITEM_TYPE.SMALL_LEFT.ordinal();
            } else {
                return ITEM_TYPE.ALONE_BIG.ordinal();
            }
//        }
    }

    @NonNull
    @Override
    public StaggerAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == ITEM_TYPE.BIG_LEFT.ordinal()) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.holder_item_view, viewGroup, false);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams((w - 16) * 2 / 3,
                    (w - 16) * 2 / 3);
            lp.bottomMargin = 10;
            lp.topMargin = 10;
            lp.rightMargin = 10;
            lp.leftMargin = 10;
            view.setLayoutParams(lp);
            view.setX(0);
            return new Holder(view);
        } else if (i == ITEM_TYPE.BIG_RIGHT.ordinal()) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.holder_item_view1, viewGroup, false);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams((w - 16) * 2 / 3,
                    (w - 16) * 2 / 3);
            lp.bottomMargin = 10;
            lp.topMargin = 10;
            lp.rightMargin = 10;
            lp.leftMargin = 10;
            view.setLayoutParams(lp);
            view.setX(-w / 6);
            return new Holder(view);
        } else if (i == ITEM_TYPE.SMALL_LEFT.ordinal()) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.holder_item_view, viewGroup, false);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams((w - 16) / 3,
                    (w - 16) / 3);
            lp.bottomMargin = 10;
            lp.topMargin = 10;
            lp.rightMargin = 10;
            lp.leftMargin = 10;
            view.setLayoutParams(lp);
            view.setX(0);
            return new Holder(view);
        } else if (i == ITEM_TYPE.SMALL_RIGHT.ordinal()) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.holder_item_view1, viewGroup, false);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams((w - 16) / 3,
                    (w - 16) / 3);
            lp.bottomMargin = 10;
            lp.topMargin = 10;
            lp.rightMargin = 10;
            lp.leftMargin = 10;
            view.setLayoutParams(lp);
            view.setX(w / 6);
            return new Holder(view);
        } else if (i == ITEM_TYPE.ALONE_BIG.ordinal()) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.holder_item_view2, viewGroup, false);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams((w - 16) / 3,
                    w/3);
            lp.bottomMargin = 10;
            lp.topMargin = 10;
            lp.rightMargin = 10;
            lp.leftMargin = 10;
            view.setLayoutParams(lp);
            view.setX(0);
            return new Holder(view);
        } else {
            return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull StaggerAdapter.Holder holder, int i) {
        //这里计算好，理论上是可以显示一个横向的item，暂时注销
//        int ad = i % 15;
        ImageView iv = holder.iv;
        CardView cardIv = holder.cardIv;
        ViewGroup.LayoutParams layoutParams = cardIv.getLayoutParams();
        ViewGroup.LayoutParams lp = iv.getLayoutParams();
//        if (i != 0 && ad == 0) {
//            lp.width = w;
//            lp.height = w;
//            layoutParams.width = w;
//            layoutParams.height = w/3;
//        } else {
            int res = i % 3;
            int n = i & 1;

            if ((res == 0 && n == 0)) {
                lp.width = w * 2 / 3;
                lp.height = w * 2 / 3;
                layoutParams.width = w * 2 / 3;
                layoutParams.height = w * 2 / 3;
            } else if ((res == 1 && n == 0)) {
                lp.width = w * 2 / 3;
                lp.height = w * 2 / 3;
                layoutParams.width = w * 2 / 3;
                layoutParams.height = w * 2 / 3;
            } else if ((res == 1 && n == 1) || (res == 2 && n == 0)) {
                lp.width = w / 3;
                lp.height = w / 3;
                layoutParams.width = w / 3;
                layoutParams.height = w / 3;
            } else if ((res == 0 && n == 1) || (res == 2 && n == 1)) {
                lp.width = w / 3;
                lp.height = w / 3;
                layoutParams.width = w / 3;
                layoutParams.height = w / 3;
            }
//        }
        iv.setLayoutParams(lp);
        cardIv.setLayoutParams(layoutParams);
        GlideUtil.loadImage(context, R.drawable.place_holder_img, imgs.get(i), iv);
        holder.iv.setOnClickListener(v -> {
            if (mHomeRVIvClick != null)
                mHomeRVIvClick.rvIvCallBack(i);
        });

    }

    @Override
    public int getItemCount() {
        return imgs.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        private final ImageView iv;
        private final CardView cardIv;
        //        private final LinearLayout ll;

        Holder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            cardIv = itemView.findViewById(R.id.cardIv);
            //            ll = itemView.findViewById(R.id.ll);

        }
    }

}
