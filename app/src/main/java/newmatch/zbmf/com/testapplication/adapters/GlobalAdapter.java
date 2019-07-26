package newmatch.zbmf.com.testapplication.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.utils.glidUtils.GlideUtil;

/**
 * Created By pq
 * on 2019/7/24
 * 普通通用的Adapter
 */
public class GlobalAdapter extends RecyclerView.Adapter<GlobalAdapter.GlobalHolder> {

    @NonNull
    @Override
    public GlobalHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new GlobalHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fans_itemm_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GlobalHolder globalHolder, int i) {

        GlideUtil.loadCircleImage(globalHolder.itemView.getContext(), R.drawable.place_holder_img,
                R.drawable.j2, globalHolder.fansAvatar);
        globalHolder.fansName.setText("秦始皇的马甲   ");
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    static class GlobalHolder extends RecyclerView.ViewHolder {

        private final AppCompatImageView fansAvatar;
        private final TextView fansName;
        private final TextView fansLabel1;
        private final TextView fansLabel2;
        private final TextView likeBtn;

        GlobalHolder(@NonNull View itemView) {
            super(itemView);
            fansAvatar = itemView.findViewById(R.id.fansAvatar);
            fansName = itemView.findViewById(R.id.fansName);
            fansLabel1 = itemView.findViewById(R.id.fansLabel1);
            fansLabel2 = itemView.findViewById(R.id.fansLabel2);
            likeBtn = itemView.findViewById(R.id.likeBtn);
        }
    }
}
