package newmatch.zbmf.com.testapplication.adapters.myPagerAdapters;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.utils.glidUtils.GlideUtil;
import newmatch.zbmf.com.testapplication.views.circleImageView.CustomImageView;


/**
 * Created By pq
 * on 2019/5/9
 */
public class MenuAdapter extends PagerAdapter {

    private List<Integer> mList;
    private List<Integer> payImgs;
    private int viewRes;

    public MenuAdapter(List<Integer> mList, List<Integer> mPayList, int viewRes) {
        this.mList = mList;
        this.payImgs = mPayList;
        this.viewRes = viewRes;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(viewRes, container, false);
        CustomImageView menuIv = view.findViewById(R.id.menuIv);
        CustomImageView pay_type = view.findViewById(R.id.pay_type);
        //        TextView title = view.findViewById(R.id.title);
        //        TextView sum = view.findViewById(R.id.sum);
        //        TextView sumDeclare = view.findViewById(R.id.sumDeclare);


        GlideUtil.loadImage(container.getContext(), R.drawable.place_holder_img
                , mList.get(position), menuIv);
        if (payImgs != null && payImgs.size() > 0) {
            pay_type.setBackgroundResource(payImgs.get(position));
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
