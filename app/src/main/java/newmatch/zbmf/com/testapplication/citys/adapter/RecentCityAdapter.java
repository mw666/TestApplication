package newmatch.zbmf.com.testapplication.citys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.citys.listener.CityClickCallBack;
import newmatch.zbmf.com.testapplication.citys.listener.CurrentCityListener;

/**
 * Created by next on 2016/3/25.
 */
public class RecentCityAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<String> hotCities;
    private CityClickCallBack mCityClickCallBack;
    private CurrentCityListener mCurrentCityListener;
    private TextView mCityTv;

    public void setCityClickCallBack(CityClickCallBack cityClickCallBack){
        this.mCityClickCallBack=cityClickCallBack;
    }

    public RecentCityAdapter(Context context, List<String> hotCities,
                             CityClickCallBack cityClickCallBack,TextView cityTv,
                             CurrentCityListener currentCityListener) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.hotCities = hotCities;
        this.mCityClickCallBack=cityClickCallBack;
        this.mCityTv=cityTv;
        this.mCurrentCityListener=currentCityListener;
    }

    @Override
    public int getCount() {
        return hotCities.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_city_cell, null);
        TextView city = (TextView) convertView.findViewById(R.id.city);
        city.setText(hotCities.get(position));
        city.setOnClickListener(v -> {
             if (mCityClickCallBack!=null){
                 mCurrentCityListener.currentCityCallBack(hotCities.get(position));
                 mCityClickCallBack.selectVistCity(hotCities.get(position));
                 mCityTv.setText(hotCities.get(position));
             }
        });
        return convertView;
    }
}