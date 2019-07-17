package newmatch.zbmf.com.testapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.assist.GlideUtil;
import newmatch.zbmf.com.testapplication.interfaces.RecommendUser;
import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;

/**
 * Created by **
 * on 2018/9/21.
 */

public class ChatRecommendAdapter extends RecyclerView.Adapter<ChatRecommendAdapter.ChatRecommendHolder> {

    private RecommendUser mRecommendUser;
    private Context mContext;

    public ChatRecommendAdapter(Context context) {
        mContext = context;
    }

    public void setMsgChatItemClick(RecommendUser recommendUser){
        this.mRecommendUser=recommendUser;
    }

    @NonNull
    @Override
    public ChatRecommendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatRecommendHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_recommend_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRecommendHolder holder, int position) {
        //模拟展示
        GlideUtil.loadCircleImage(mContext, R.drawable.place_holder_img, mContext.getResources().
                getDrawable(R.drawable.j1), holder.mChatRecommendItemIv);

        clickIv(holder.mChatRecommendItemIv,holder.getAdapterPosition());
    }

    private void clickIv(ImageView cIv,int position){
        cIv.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (mRecommendUser!=null){
                    mRecommendUser.recommendUser(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    static class ChatRecommendHolder extends RecyclerView.ViewHolder {

        private final ImageView mChatRecommendItemIv;

        public ChatRecommendHolder(View itemView) {
            super(itemView);
            mChatRecommendItemIv = itemView.findViewById(R.id.chatRecommendItemIv);
        }
    }


}
