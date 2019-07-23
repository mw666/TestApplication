package newmatch.zbmf.com.testapplication.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import newmatch.zbmf.com.testapplication.R;

/**
 * Created By pq
 * on 2019/7/23
 * 图片或短视频的adapter
 */
public class PvAdapter extends RecyclerView.Adapter<PvAdapter.PvHolder> {

    @NonNull
    @Override
    public PvHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PvHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.pv_rv_item_view,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PvHolder pvHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    static class PvHolder extends RecyclerView.ViewHolder{

       PvHolder(@NonNull View itemView) {
           super(itemView);

       }
   }
}
