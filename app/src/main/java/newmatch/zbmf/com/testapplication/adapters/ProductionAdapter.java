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
 * Created by pq
 * on 2018/11/13.
 * 短视频作品的adapter
 */

public class ProductionAdapter extends RecyclerView.Adapter<ProductionAdapter.ProductionHolder> {
    private Context mContext;

    public ProductionAdapter(Context context){
        mContext=context;
    }

    //设置短视频的点击
    private ProductionIvClick mProductionIvClick;
    public void setProductionIvClick(ProductionIvClick ivClick){
        this.mProductionIvClick=ivClick;
    }

    @NonNull
    @Override
    public ProductionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductionHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.production_item_view, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductionHolder holder, int position) {

        holder.mShortVideoCover.setOnClickListener(v -> {
           if (mProductionIvClick!=null){
               mProductionIvClick.productionIvClick();
           }});
    }

    @Override
    public int getItemCount() {
        return 30;
    }

    static class ProductionHolder extends RecyclerView.ViewHolder {

        private final ImageView mShortVideoCover;
        private final TextView mShortVideoTv;

        ProductionHolder(View itemView) {
            super(itemView);
            mShortVideoCover = itemView.findViewById(R.id.shortVideoCover);
            mShortVideoTv = itemView.findViewById(R.id.shortVideoTv);
        }
    }

    public interface ProductionIvClick {
        void productionIvClick();
    }
}
