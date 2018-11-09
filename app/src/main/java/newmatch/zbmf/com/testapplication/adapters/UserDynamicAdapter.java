package newmatch.zbmf.com.testapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import newmatch.zbmf.com.testapplication.R;

/**
 * Created by pq
 * on 2018/11/7.
 */

public class UserDynamicAdapter extends RecyclerView.Adapter<UserDynamicAdapter.UserDynamicHolder> {

    private Context mContext;

    public UserDynamicAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public UserDynamicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserDynamicHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_dynamic_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserDynamicHolder holder, int position) {
                 holder.mUserRvIv.setBackgroundResource(R.drawable.header_background);
    }

    @Override
    public int getItemCount() {
        return 27;
    }

    static class UserDynamicHolder extends RecyclerView.ViewHolder{

        private final ImageView mUserRvIv;

        UserDynamicHolder(View itemView) {
            super(itemView);
            mUserRvIv = itemView.findViewById(R.id.userRvIv);
        }
    }
}
