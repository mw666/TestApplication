package newmatch.zbmf.com.testapplication.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import newmatch.zbmf.com.testapplication.R;

/**
 * Created by **
 * on 2018/10/10.
 * 动态的评论的adapter
 */

public class DynamicCommentAdapter extends RecyclerView.Adapter<DynamicCommentAdapter.DynamicCommentHolder>{

    @NonNull
    @Override
    public DynamicCommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DynamicCommentHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.dynamic_comment_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DynamicCommentHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    static class DynamicCommentHolder extends RecyclerView.ViewHolder{

        private final TextView mCommentUserNick,mRevert,mUserRevertNick,mComment;

        DynamicCommentHolder(View itemView) {
            super(itemView);
            mCommentUserNick = itemView.findViewById(R.id.commentUserNick);
            mRevert = itemView.findViewById(R.id.revert);
            mUserRevertNick = itemView.findViewById(R.id.userRevertNick);
            mComment = itemView.findViewById(R.id.comment);
        }
    }
}
