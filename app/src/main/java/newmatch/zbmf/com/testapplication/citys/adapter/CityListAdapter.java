package newmatch.zbmf.com.testapplication.citys.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.citys.entity.City;
import newmatch.zbmf.com.testapplication.citys.listener.CityClickCallBack;
import newmatch.zbmf.com.testapplication.citys.listener.CurrentCityListener;
import newmatch.zbmf.com.testapplication.utils.TianShareUtil;

/**
 * Created by next
 * on 2016/3/25.
 */
public class CityListAdapter extends BaseAdapter implements CurrentCityListener {
    private Context context;
    private LayoutInflater inflater;
    private List<City> allCities;
    private List<City> hotCities;
    private List<String> historyCities;
    private String[] firstLetterArray;// 存放存在的汉语拼音首字母
    private Map<String, Integer> letterIndex;
    private final int VIEW_TYPE = 6;
    private CityClickCallBack mCityClickCallBack;
    private TextView mCurrentSelectLocationV;
    private String currentSeeCity;
    //标记是否选择了其他城市
    private Boolean isSeeOther = false;

    public void setCityClickCallBack(CityClickCallBack cityClickCallBack) {
        this.mCityClickCallBack = cityClickCallBack;
    }

    public CityListAdapter(Context context, List<City> allCities, List<City> hotCities,
                           List<String> historyCities, Map<String, Integer> letterIndex) {
        this.context = context;
        this.allCities = allCities;
        this.hotCities = hotCities;
        this.historyCities = historyCities;
        this.letterIndex = letterIndex;
        inflater = LayoutInflater.from(this.context);

        setup();
    }

    private void setup() {
        firstLetterArray = new String[allCities.size()];
        for (int i = 0; i < allCities.size(); i++) {
            // 当前汉语拼音首字母
            String currentStr = getAlpha(allCities.get(i).getPinyin());
            // 上一个汉语拼音首字母，如果不存在为" "
            String previewStr = (i - 1) >= 0 ? getAlpha(allCities.get(i - 1).getPinyin()) : " ";
            if (!previewStr.equals(currentStr)) {
                String name = getAlpha(allCities.get(i).getPinyin());
                letterIndex.put(name, i);
                firstLetterArray[i] = name;
            }
        }
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE;
    }

    @Override
    public int getItemViewType(int position) {
        return position < 5 ? position : 5;
    }

    @Override
    public int getCount() {
        return allCities.size();
    }

    @Override
    public Object getItem(int position) {
        return allCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        if (viewType == 0) {//正在查看
            convertView = inflater.inflate(R.layout.current_see_location_item, null);
            mCurrentSelectLocationV = convertView.findViewById(R.id.currentSelectLocationV);
            if (TextUtils.isEmpty(TianShareUtil.getSeeCity())) {
                currentSeeCity = TianShareUtil.getCity();
            } else {
                currentSeeCity = TianShareUtil.getSeeCity();
            }
            mCurrentSelectLocationV.setText(currentSeeCity);
            if (mCityClickCallBack != null)
                mCityClickCallBack.setCurrentSeeLocation(TianShareUtil.getCity(), mCurrentSelectLocationV);
        } else if (viewType == 1) {//定位
            convertView = inflater.inflate(R.layout.item_city_location, null);
            Button tv_location = (Button) convertView.findViewById(R.id.tv_location);
            //传入当前定位的城市
            currentSeeCity = TianShareUtil.getCity();
            tv_location.setText(currentSeeCity);
            tv_location.setOnClickListener(v -> {
                if (mCityClickCallBack != null) {
                    TianShareUtil.setSeeCity(currentSeeCity);
                    mCityClickCallBack.locationCity(currentSeeCity);
                    mCurrentSelectLocationV.setText(currentSeeCity);
                }
            });
        } else if (viewType == 2) {//最近访问
            convertView = inflater.inflate(R.layout.item_city_grid, null);
            GridView recentCityView = (GridView) convertView.findViewById(R.id.grid_city);
            recentCityView.setAdapter(new RecentCityAdapter(context, this.historyCities,
                    mCityClickCallBack, mCurrentSelectLocationV, this));

            recentCityView.setOnItemClickListener((parent1, view, position1, id) ->
                    Toast.makeText(context, historyCities.get(position1), Toast.LENGTH_SHORT).show());

            TextView recentHint = (TextView) convertView.findViewById(R.id.recentHint);
            recentHint.setText("最近访问的城市");
        } else if (viewType == 3) {//热门城市
            convertView = inflater.inflate(R.layout.item_city_grid, null);
            final GridView hotCity = (GridView) convertView.findViewById(R.id.grid_city);

            hotCity.setOnItemClickListener((parent12, view, position12, id) ->
                    Toast.makeText(context, hotCities.get(position12).getName(), Toast.LENGTH_SHORT).show());

            hotCity.setAdapter(new HotCityAdapter(context, this.hotCities,
                    mCityClickCallBack, mCurrentSelectLocationV, this));
            TextView hotHint = (TextView) convertView.findViewById(R.id.recentHint);
            hotHint.setText("热门城市");
        } else if (viewType == 4) {
            convertView = inflater.inflate(R.layout.item_city_total_tag, null);
        } else {
            Holder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_city_list, null);
                holder = new Holder();
                holder.letter = (TextView) convertView.findViewById(R.id.tv_letter);
                holder.name = (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            if (position >= 1) {
                holder.name.setText(allCities.get(position).getName());
                String currentStr = getAlpha(allCities.get(position).getPinyin());
                String previewStr = (position - 1) >= 0 ? getAlpha(allCities.get(position - 1).getPinyin()) : " ";

                holder.name.setOnClickListener(v -> {
                    if (mCityClickCallBack != null) {
                        isSeeOther = true;
                        TianShareUtil.setSeeCity(allCities.get(position).getName());
                        currentSeeCity = allCities.get(position).getName();
                        mCityClickCallBack.selectAllCity(allCities.get(position).getName());
                        mCurrentSelectLocationV.setText(allCities.get(position).getName());
                    }
                });
                if (!previewStr.equals(currentStr)) {
                    holder.letter.setVisibility(View.VISIBLE);
                    holder.letter.setText(currentStr);
                } else {
                    holder.letter.setVisibility(View.GONE);
                }
            }
        }
        return convertView;
    }

    @Override
    public void currentCityCallBack(String currentSeeCity) {
        isSeeOther = true;
        TianShareUtil.setSeeCity(currentSeeCity);
        this.currentSeeCity = currentSeeCity;
    }

    class Holder {
        TextView letter, name;
    }


    // 获得汉语拼音首字母
    private String getAlpha(String str) {
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        // 正则表达式，判断首字母是否是英文字母
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else if (str.equals("0")) {
            return "查看";
        } else if (str.equals("1")) {
            return "定位";
        } else if (str.equals("2")) {
            return "最近";
        } else if (str.equals("3")) {
            return "热门";
        } else if (str.equals("4")) {
            return "全部";
        } else {
            return "#";
        }
    }
}
