package newmatch.zbmf.com.testapplication.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.custom_view.RoundImageView;
import newmatch.zbmf.com.testapplication.interfaces.MsgChatItemClick;
import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;

/**
 * Created by **
 * on 2018/9/21.
 * 聊天消息列表的item
 */

public class ChatRecordListAdapter extends RecyclerView.Adapter<ChatRecordListAdapter.ChatRecordListHolder> {

    private MsgChatItemClick mMsgChatItemClick;

    public void setMsgChatItemClick(MsgChatItemClick msgChatItemClick){
        this.mMsgChatItemClick=msgChatItemClick;
    }

    @NonNull
    @Override
    public ChatRecordListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatRecordListHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.chat_record_msg_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRecordListHolder holder, int position) {
         holder.mChatItemNick.setText("二狗子");
         holder.mChatItemUserIv.setBackgroundResource(R.drawable.mn9);
         holder.mChatLastTime.setText("上午  8:23");
         holder.mChatLastChatMsg.setText("你个坏东西，快还我的辣条");
         holder.mChatItemRL.setOnClickListener(new OnceClickListener() {
             @Override
             public void onNoDoubleClick(View v) {
                 if (mMsgChatItemClick!=null){
                     mMsgChatItemClick.chatItemClick(holder.getAdapterPosition());
                 }
             }
         });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    static class ChatRecordListHolder extends RecyclerView.ViewHolder{

        private final RoundImageView mChatItemUserIv;
        private final TextView mChatItemNick;
        private final TextView mChatLastChatMsg;
        private final TextView mChatLastTime;
        private final RelativeLayout mChatItemRL;

        public ChatRecordListHolder(View itemView) {
            super(itemView);
            mChatItemUserIv = itemView.findViewById(R.id.chatItemUserIv);
            mChatItemNick = itemView.findViewById(R.id.chatItemNick);
            mChatLastChatMsg = itemView.findViewById(R.id.chatLastChatMsg);
            mChatLastTime = itemView.findViewById(R.id.chatLastTime);
            mChatItemRL = itemView.findViewById(R.id.chatItemRL);
        }
    }
}
