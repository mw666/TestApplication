package newmatch.zbmf.com.testapplication.adapters.comment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import newmatch.zbmf.com.testapplication.GMClass.GMTextSetIcon;
import newmatch.zbmf.com.testapplication.GMClass.LikeGMClass;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.activitys.UserDetailActivity;
import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;
import newmatch.zbmf.com.testapplication.utils.SkipActivityUtil;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

/**
 * Create By Administrator
 * on 2019/7/20
 */
public class CommenListAdapter extends RecyclerView.Adapter<CommenListAdapter.CommentListHolder> {
    private int flag;
    private Activity activity;

    public CommenListAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public CommentListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CommentListHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.comment_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentListHolder commentListHolder, int i) {
        //设置评论里边的RV
        commentListHolder.answerCommentList.setLayoutManager(new LinearLayoutManager(
                commentListHolder.itemView.getContext(), OrientationHelper.VERTICAL, false
        ));
        CommentListItemAnswerAdapter commentListItemAnswerAdapter = new CommentListItemAnswerAdapter(activity);
        commentListHolder.answerCommentList.setAdapter(commentListItemAnswerAdapter);

        commentListHolder.userNick.setText("雅典娜的背叛");
        commentListHolder.userCommentContent.setText("纳爱斯坐上浮云飘到了神秘的东方国度，没想到被一只顽劣的猴子给从浮云上一觉踢到了花果山做了压寨夫人");

        commentListHolder.expandCommentBtn.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if (flag == 0) {
                    commentListHolder.expandCommentBtn.setText("收起");
                    GMTextSetIcon.setTvLeftIcon(commentListHolder.itemView.getContext()
                            ,R.drawable.line_small,4
                            ,commentListHolder.expandCommentBtn);
                    GMTextSetIcon.setTvRightIcon(commentListHolder.itemView.getContext()
                            ,R.drawable.ic_arrow_drop_down_gray,4
                            ,commentListHolder.expandCommentBtn);
                    commentListHolder.answerCommentList.setVisibility(View.VISIBLE);
                    flag = 1;
                } else if (flag == 1) {
                    commentListHolder.expandCommentBtn.setText("展开2条回复");
                    GMTextSetIcon.setTvLeftIcon(commentListHolder.itemView.getContext()
                            ,R.drawable.line_small,4
                            ,commentListHolder.expandCommentBtn);
                    GMTextSetIcon.setTvRightIcon(commentListHolder.itemView.getContext()
                            ,R.drawable.ic_arrow_down_black4,4
                            ,commentListHolder.expandCommentBtn);
                    commentListHolder.answerCommentList.setVisibility(View.GONE);
                    flag = 0;
                }
                ToastUtils.showSquareTvToast(commentListHolder.itemView.getContext(), "展开或收起评论");
            }
        });

        //点击用户头像，跳转用户详情页面
        commentListHolder.commentUserAvatar.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                /*模拟跳转用户详情页面*/
                SkipActivityUtil.skipActivity(activity, UserDetailActivity.class);
            }
        });

        //点赞评论
        commentListHolder.zanCommentBtn.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                /*模拟点赞*/
                LikeGMClass.clickTvLike(activity,commentListHolder.itemView.getContext(),false,
                        R.drawable.ic_like_heart_pink,R.drawable.ic_like_heart_gray,3
                        ,commentListHolder.zanCommentBtn);

                String count = commentListHolder.zanCommentBtn.getText().toString().trim();
                commentListHolder.zanCommentBtn.setText(String.valueOf(Integer.parseInt(count)+1));

            }
        });
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    static class CommentListHolder extends RecyclerView.ViewHolder {

        private final AppCompatImageView commentUserAvatar;
        private final TextView userNick;
        private final TextView userCommentContent;
        private final RecyclerView answerCommentList;
        private final TextView expandCommentBtn;
        private final TextView zanCommentBtn;

        CommentListHolder(@NonNull View itemView) {
            super(itemView);
            commentUserAvatar = itemView.findViewById(R.id.commentUserAvatar);
            userNick = itemView.findViewById(R.id.userNick);
            userCommentContent = itemView.findViewById(R.id.userCommentContent);
            answerCommentList = itemView.findViewById(R.id.answerCommentList);
            expandCommentBtn = itemView.findViewById(R.id.expandCommentBtn);
            zanCommentBtn = itemView.findViewById(R.id.zanCommentBtn);

        }
    }
}
