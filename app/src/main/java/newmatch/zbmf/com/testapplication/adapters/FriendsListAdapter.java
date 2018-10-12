package newmatch.zbmf.com.testapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import newmatch.zbmf.com.testapplication.R;

/**
 * Created by **
 * on 2018/10/12.
 * 好友列表的adapter (群组的adapter)
 */

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.ListViewHolder> {

    private Context mContext;
    private ClickFdArrow mClickFdArrow;
    private Boolean extend=false;
    public FriendsListAdapter(Context context) {
        mContext = context;
    }

    public void setClickFdArrow(ClickFdArrow clickFdArrow){
        mClickFdArrow=clickFdArrow;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friends_list_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
           holder.mFenZuName.setText("专属分组");
           //分组列表--->默认关闭
           holder.mMemberCount.setText("27/137");
           holder.itemView.setOnClickListener(v -> {
               if (mClickFdArrow!=null){
                   if (!extend){
                       //---->展开
                       extend=true;
                       mClickFdArrow.extendList(true);
                       holder.mFdArrow.setImageResource(R.drawable.arrow_down_purple);
                   }else {
                       //---->关闭
                       extend=false;
                       mClickFdArrow.extendList(false);
                       holder.mFdArrow.setImageResource(R.drawable.arrow_right_gray);
                   }
               }
           });
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mFdArrow;
        private final TextView mFenZuName;
        private final TextView mMemberCount;

        ListViewHolder(View itemView) {
            super(itemView);
            mFdArrow = itemView.findViewById(R.id.fdArrow);
            mFenZuName = itemView.findViewById(R.id.fenZuName);
            mMemberCount = itemView.findViewById(R.id.memberCount);
        }
    }

    public interface ClickFdArrow{

        void extendList(Boolean extendAble);
    }
}
