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

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.assist.GlideUtil;
import newmatch.zbmf.com.testapplication.callback.MineViewClick;
import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;

/**
 * Created by **
 * on 2018/9/25.
 */

public class MineViewAdapter extends RecyclerView.Adapter<MineViewAdapter.MineHolder> {

    private String[] mTitles;
    private Context mContext;
    private MineViewClick mMineViewClick;

    public void setMineViewClick(MineViewClick mineViewClick) {
        mMineViewClick = mineViewClick;
    }

    public MineViewAdapter(String[] titles, Context context) {
        mTitles = titles;
        mContext = context;
    }

    @NonNull
    @Override
    public MineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MineHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mine_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MineHolder holder, int position) {
        switch (position) {
            case 0:
                GlideUtil.loadImage(mContext, R.drawable.place_holder_img,
                        R.drawable.vip_icon, holder.mMineItemIV);
                holder.mMineItemTv.setText(mTitles[position]);
                holder.mMineItemRL.setOnClickListener(new OnceClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        if (mMineViewClick != null) {
                            mMineViewClick.clickVip();
                        }
                    }
                });
                break;
            case 1:
                GlideUtil.loadImage(mContext, R.drawable.place_holder_img,
                        R.drawable.update_pass_word_icon, holder.mMineItemIV);
                holder.mMineItemTv.setText(mTitles[position]);
                holder.mMineItemRL.setOnClickListener(new OnceClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        if (mMineViewClick != null) {
                            mMineViewClick.clickUpdatePassWord();
                        }
                    }
                });
                break;
            case 2:
                GlideUtil.loadImage(mContext, R.drawable.place_holder_img,
                        R.drawable.options_back_icon, holder.mMineItemIV);
                holder.mMineItemTv.setText(mTitles[position]);
                holder.mMineItemRL.setOnClickListener(new OnceClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        if (mMineViewClick != null) {
                            mMineViewClick.clickOptionsUp();
                        }
                    }
                });
                break;
            case 3:
                GlideUtil.loadImage(mContext, R.drawable.place_holder_img,
                        R.drawable.version_update_icon, holder.mMineItemIV);
                holder.mMineItemTv.setText(mTitles[position]);
                holder.mMineItemRL.setOnClickListener(new OnceClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        if (mMineViewClick != null) {
                            mMineViewClick.clickVertionUpdate();
                        }
                    }
                });
                break;
            case 4:
                GlideUtil.loadImage(mContext, R.drawable.place_holder_img,
                        R.drawable.login_out_icon, holder.mMineItemIV);
                holder.mMineItemTv.setText(mTitles[position]);
                holder.mMineItemRL.setOnClickListener(new OnceClickListener() {
                    @Override
                    public void onNoDoubleClick(View v) {
                        if (mMineViewClick != null) {
                            mMineViewClick.clickLoginOut();
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mTitles.length;
    }

    static class MineHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout mMineItemRL;
        private final ImageView mMineItemIV;
        private final TextView mMineItemTv;

        public MineHolder(View itemView) {
            super(itemView);
            mMineItemRL = itemView.findViewById(R.id.mine_item_RL);
            mMineItemIV = itemView.findViewById(R.id.mine_item_iv);
            mMineItemTv = itemView.findViewById(R.id.mine_item_tv);
        }
    }
}
