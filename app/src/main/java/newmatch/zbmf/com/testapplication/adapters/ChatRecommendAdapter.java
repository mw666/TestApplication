package newmatch.zbmf.com.testapplication.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.custom_view.RoundImageView;
import newmatch.zbmf.com.testapplication.interfaces.HomeRVIvClick;
import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;

/**
 * Created by **
 * on 2018/9/21.
 */

public class ChatRecommendAdapter extends RecyclerView.Adapter<ChatRecommendAdapter.ChatRecommendHolder> {

    private HomeRVIvClick mHomeRVIvClick;

    public void setHomeRVIvClick(HomeRVIvClick homeRVIvClick){
        this.mHomeRVIvClick=homeRVIvClick;
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
        holder.mChatRecommendItemIv.setBackgroundResource(R.drawable.mn9);

        clickIv(holder.mChatRecommendItemIv,holder.getAdapterPosition());
    }

    private void clickIv(RoundImageView cIv,int position){
        cIv.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (mHomeRVIvClick!=null){
                    mHomeRVIvClick.rvIvCallBack(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    static class ChatRecommendHolder extends RecyclerView.ViewHolder {

        private final RoundImageView mChatRecommendItemIv;

        public ChatRecommendHolder(View itemView) {
            super(itemView);
            mChatRecommendItemIv = itemView.findViewById(R.id.chatRecommendItemIv);
        }
    }


}
