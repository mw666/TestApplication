package newmatch.zbmf.com.testapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;
import newmatch.zbmf.com.testapplication.utils.GetUIDimens;

/**
 * Created By pq
 * on 2019/7/23
 * 图片或短视频的adapter
 */
public class PvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ADD = 0x1;
    private static final int TYPE_ALBUM = 0x2;
    private final int windowW;
    private CreateAlbumCallBack createAlbumCallBack;
    private SkipAlbumCallBack skipAlbumCallBack;

    public void setCreateAlbumCallBack(CreateAlbumCallBack createAlbumCallBack) {
        this.createAlbumCallBack = createAlbumCallBack;
    }

    public void setSkipAlbumCallBack(SkipAlbumCallBack s) {
        this.skipAlbumCallBack = s;
    }

    public PvAdapter(Context context, int albumCount) {
        this.albumCount = albumCount;
        windowW = GetUIDimens.getWindowW(context);
    }

    /*模拟传入相册的数量*/
    private int albumCount;

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ADD;
        } else {
            return TYPE_ALBUM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == TYPE_ADD) {
            return new PvHolder1(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.pv_rv_create_item_view, viewGroup, false));
        } else {
            return new PvHolder2(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.pv_rv_item_view, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof PvHolder1) {
            ViewGroup.LayoutParams cardLp = ((PvHolder1) viewHolder).cardIv.getLayoutParams();
            cardLp.width = (windowW - 100) / 2;
            cardLp.height = (windowW - 100) / 2;
            ((PvHolder1) viewHolder).cardIv.setLayoutParams(cardLp);
            ((PvHolder1) viewHolder).cardIv.setOnClickListener(new OnceClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (createAlbumCallBack != null)
                        createAlbumCallBack.createAlbumCallBack();
                }
            });
            ((PvHolder1) viewHolder).createNewAlbum.setOnClickListener(new OnceClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (createAlbumCallBack != null)
                        createAlbumCallBack.createAlbumCallBack();
                }
            });
        } else if (viewHolder instanceof PvHolder2) {
            ViewGroup.LayoutParams cardLp = ((PvHolder2) viewHolder).cardIv.getLayoutParams();
            cardLp.width = (windowW - 100) / 2;
            cardLp.height = (windowW - 100) / 2;
            ((PvHolder2) viewHolder).cardIv.setLayoutParams(cardLp);
            ((PvHolder2) viewHolder).cardIv.setOnClickListener(new OnceClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (skipAlbumCallBack != null)
                        skipAlbumCallBack.skipAlbumCallBack();
                }
            });
            ViewGroup.LayoutParams lp = ((PvHolder2) viewHolder).album_bg_view.getLayoutParams();
            lp.width = (windowW - 100) / 2;
            ((PvHolder2) viewHolder).album_bg_view.setLayoutParams(lp);
            ViewGroup.LayoutParams albumCoverViewLp = ((PvHolder2) viewHolder).album_cover.getLayoutParams();
            albumCoverViewLp.width = (windowW - 100) / 2;
            albumCoverViewLp.height = (windowW - 100) / 2;
            ((PvHolder2) viewHolder).album_cover.setLayoutParams(albumCoverViewLp);
            ((PvHolder2) viewHolder).album_cover.setBackgroundResource(R.drawable.m5);
            ((PvHolder2) viewHolder).albumName.setText("丘比特的眼泪");
            ((PvHolder2) viewHolder).albumNumber.setText("[234张]");
        }

    }

    @Override
    public int getItemCount() {
        return albumCount + 1;
    }

    static class PvHolder1 extends RecyclerView.ViewHolder {

        private final TextView createNewAlbum;
        private final MaterialCardView cardIv;

        PvHolder1(@NonNull View itemView) {
            super(itemView);
            cardIv = itemView.findViewById(R.id.cardIv);
            createNewAlbum = itemView.findViewById(R.id.createNewAlbum);

        }
    }

    static class PvHolder2 extends RecyclerView.ViewHolder {

        private final MaterialCardView cardIv;
        private final AppCompatImageView album_cover;
        private final TextView albumName;
        private final TextView albumNumber;
        private final View album_bg_view;

        PvHolder2(@NonNull View itemView) {
            super(itemView);
            cardIv = itemView.findViewById(R.id.cardIv);
            album_cover = itemView.findViewById(R.id.album_cover);
            albumName = itemView.findViewById(R.id.albumName);
            albumNumber = itemView.findViewById(R.id.albumNumber);
            album_bg_view = itemView.findViewById(R.id.album_bg_view);

        }
    }

    public interface CreateAlbumCallBack {
        void createAlbumCallBack();
    }

    public interface SkipAlbumCallBack {
        void skipAlbumCallBack();
    }
}
