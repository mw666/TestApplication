package newmatch.zbmf.com.testapplication.adapters.comment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import newmatch.zbmf.com.testapplication.GMClass.LikeGMClass;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.activitys.UserDetailActivity;
import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;

/**
 * Create By Administrator
 * on 2019/7/21
 * 评论列表回复的Adapter
 */
public class CommentListItemAnswerAdapter extends
        RecyclerView.Adapter<CommentListItemAnswerAdapter.CommentItemListHolder> {

    public CommentListItemAnswerAdapter(Activity activity) {
        this.activity = activity;
    }

    private Activity activity;

    @NonNull
    @Override
    public CommentItemListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CommentItemListHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.comment_answer_list_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentItemListHolder commentItemListHolder, int i) {

        commentItemListHolder.answerUserNick.setText("波妞");
        commentItemListHolder.userAnswerCommentContent.setText("波妞喜欢宗介");

        //点击用户头像，跳转用户详情页面
        commentItemListHolder.commentAnswerUserAvatar.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                /*模拟跳转用户详情页面*/
                SkipActivityUtil.skipActivity(activity, UserDetailActivity.class);
            }
        });
        //点赞评论
        commentItemListHolder.zanCommentBtn.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                /*模拟点赞*/
                LikeGMClass.clickTvLike(activity,commentItemListHolder.itemView.getContext(),false,
                        R.drawable.ic_like_heart_pink,R.drawable.ic_like_heart_gray,3
                        ,commentItemListHolder.zanCommentBtn);

                String count = commentItemListHolder.zanCommentBtn.getText().toString().trim();
                commentItemListHolder.zanCommentBtn.setText(String.valueOf(Integer.parseInt(count)+1));


            }
        });

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    class CommentItemListHolder extends RecyclerView.ViewHolder {

        private final AppCompatImageView commentAnswerUserAvatar;
        private final TextView answerUserNick;
        private final TextView userAnswerCommentContent;
        private final TextView zanCommentBtn;

        CommentItemListHolder(@NonNull View itemView) {
            super(itemView);
            commentAnswerUserAvatar = itemView.findViewById(R.id.commentAnswerUserAvatar);
            answerUserNick = itemView.findViewById(R.id.answerUserNick);
            userAnswerCommentContent = itemView.findViewById(R.id.userAnswerCommentContent);
            zanCommentBtn = itemView.findViewById(R.id.zanCommentBtn);
        }
    }
}
