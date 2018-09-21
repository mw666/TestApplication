package newmatch.zbmf.com.testapplication.activitys;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.citys.adapter.CityListAdapter;
import newmatch.zbmf.com.testapplication.citys.db.DBHelper;
import newmatch.zbmf.com.testapplication.citys.db.DatabaseHelper;
import newmatch.zbmf.com.testapplication.citys.entity.City;
import newmatch.zbmf.com.testapplication.citys.listener.CityClickCallBack;
import newmatch.zbmf.com.testapplication.citys.util.DensityUtil;
import newmatch.zbmf.com.testapplication.citys.util.PingYinUtil;
import newmatch.zbmf.com.testapplication.citys.view.LetterListView;
import newmatch.zbmf.com.testapplication.component.PLog;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;

/**
 * 城市选择Activity
 */
public class SelectCityActivity extends BaseActivity implements LetterListView.OnTouchingLetterChangedListener,
        AbsListView.OnScrollListener, CityClickCallBack {

    private List<City> allCities = new ArrayList<>();
    private List<City> hotCities = new ArrayList<>();
    private List<String> historyCities = new ArrayList<>();
    private List<City> citiesData;
    private Map<String, Integer> letterIndex = new HashMap<>();
    private CityListAdapter cityListAdapter;
    private ListView mCity_container;
    private LetterListView mLetter_container;

    private TextView letterOverlay; // 对话框首字母textview
    private OverlayThread overlayThread; // 显示首字母对话框
    private DatabaseHelper databaseHelper;

    private boolean isScroll;
    private boolean isOverlayReady;
    private Handler handler;
    private TextView mCurrentSelectLocation;

    @Override
    protected Integer layoutId() {
        return R.layout.activity_select_city;
    }

    @Override
    protected void initView() {
        TextView rightTvBtn = bindViewWithClick(R.id.rightTvBtn, true);
        rightTvBtn.setText(getString(R.string.confirm));
        mCity_container = bindView(R.id.city_container);
        mLetter_container = bindView(R.id.letter_container);

        databaseHelper = new DatabaseHelper(this);
        handler = new Handler();

        initCity();
        initHotCity();
        initHistoryCity();
        setupView();
        initOverlay();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected String initTitle() {
        return getString(R.string.select_city);
    }

    @Override
    protected Boolean showBackBtn() {
        return true;
    }

    @Override
    protected int topBarColor() {
        return MyApplication.getInstance().getResources().getColor(R.color.deepPurple);
    }

    @Override
    protected void onViewClick(View view) {

    }

    /**
     * 热门城市
     */
    public void initHotCity() {
        City city = new City("北京", "2");
        hotCities.add(city);
        city = new City("上海", "2");
        hotCities.add(city);
        city = new City("广州", "2");
        hotCities.add(city);
        city = new City("深圳", "2");
        hotCities.add(city);
        city = new City("武汉", "2");
        hotCities.add(city);
        city = new City("西安", "2");
        hotCities.add(city);
        city = new City("南京", "2");
        hotCities.add(city);
        city = new City("杭州", "2");
        hotCities.add(city);
        city = new City("成都", "2");
        hotCities.add(city);
        city = new City("重庆", "2");
        hotCities.add(city);
    }

    private void initHistoryCity() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from recent_city order by date desc limit 0, 3", null);
        while (cursor.moveToNext()) {
            historyCities.add(cursor.getString(1));
        }
        cursor.close();
        db.close();
    }

    /**
     * 添加历史选择的城市
     *
     * @param name
     */
    public void addHistoryCity(String name) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from recent_city where name = '" + name + "'", null);
        if (cursor.getCount() > 0) {
            db.delete("recent_city", "name = ?", new String[]{name});
        }
        db.execSQL("insert into recent_city(name, date) values('" + name + "', " + System.currentTimeMillis() + ")");
        db.close();
    }

    private void initCity() {
        City city = new City("查看", "0");//当前查看
        allCities.add(city);
        city = new City("定位", "1"); // 当前定位城市
        allCities.add(city);
        city = new City("最近", "2"); // 最近访问的城市
        allCities.add(city);
        city = new City("热门", "3"); // 热门城市
        allCities.add(city);
        city = new City("全部", "4"); // 全部城市
        allCities.add(city);
        citiesData = getCityList();
        allCities.addAll(citiesData);
    }

    private ArrayList<City> getCityList() {
        DBHelper dbHelper = new DBHelper(this);
        ArrayList<City> list = new ArrayList<>();
        try {
            dbHelper.createDataBase();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from city", null);
            City city;
            while (cursor.moveToNext()) {
                city = new City(cursor.getString(1), cursor.getString(2));
                list.add(city);
            }
            cursor.close();
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(list, comparator);
        return list;
    }

    /**
     * a-z排序
     */
    Comparator comparator = (Comparator<City>) (lhs, rhs) -> {
        String a = lhs.getPinyin().substring(0, 1);
        String b = rhs.getPinyin().substring(0, 1);
        int flag = a.compareTo(b);
        if (flag == 0) {
            return a.compareTo(b);
        } else {
            return flag;
        }
    };

    private void setupView() {
        mCity_container.setOnScrollListener(this);
        mLetter_container.setOnTouchingLetterChangedListener(this);

        cityListAdapter = new CityListAdapter(this, allCities, hotCities, historyCities, letterIndex);
        cityListAdapter.setCityClickCallBack(this);
        mCity_container.setAdapter(cityListAdapter);
    }

    // 初始化汉语拼音首字母弹出提示框
    private void initOverlay() {
        overlayThread = new OverlayThread();
        isOverlayReady = true;
        LayoutInflater inflater = LayoutInflater.from(this);
        letterOverlay = (TextView) inflater.inflate(R.layout.v_letter_overlay, null);
        letterOverlay.setVisibility(View.INVISIBLE);
        int width = DensityUtil.dp2px(this, 65);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                width, width,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        assert windowManager != null;
        windowManager.addView(letterOverlay, lp);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_TOUCH_SCROLL || scrollState == SCROLL_STATE_FLING) {
            isScroll = true;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!isScroll) {
            return;
        }
        if (isOverlayReady) {
            String text;
            String name = allCities.get(firstVisibleItem).getName();
            String pinyin = allCities.get(firstVisibleItem).getPinyin();
            if (firstVisibleItem < 4) {
                text = name;
            } else {
                text = PingYinUtil.converterToFirstSpell(pinyin).substring(0, 1).toUpperCase();
            }
            Pattern pattern = Pattern.compile("^[A-Za-z]+$");
            if (pattern.matcher(text).matches()) {
                letterOverlay.setTextSize(40);
            } else {
                letterOverlay.setTextSize(20);
            }
            letterOverlay.setText(text);
            letterOverlay.setVisibility(View.VISIBLE);
            handler.removeCallbacks(overlayThread);
            // 延迟一秒后执行，让overlay为不可见
            handler.postDelayed(overlayThread, 1000);
        }
    }

    @Override
    public void locationCity(String locatingCity) {
//        mCurrentSelectLocation.setText(locatingCity);
        //添加历史城市
        addHistoryCity(locatingCity);
        resultCityToUserInfo(locatingCity);
    }

    @Override
    public void selectHotCity(String hotCity) {
//        mCurrentSelectLocation.setText(hotCity);
        //添加历史城市
        addHistoryCity(hotCity);
        resultCityToUserInfo(hotCity);
    }

    @Override
    public void selectVistCity(String vistCity) {
//        mCurrentSelectLocation.setText(vistCity);
        //添加历史城市
        addHistoryCity(vistCity);
        resultCityToUserInfo(vistCity);
    }

    @Override
    public void selectAllCity(String allCity) {
        //添加历史城市
        addHistoryCity(allCity);
//        mCurrentSelectLocation.setText(allCity);
        resultCityToUserInfo(allCity);
    }

    @Override
    public void setCurrentSeeLocation(String location, TextView locationTv) {
        this.mCurrentSelectLocation = locationTv;
    }

    private void resultCityToUserInfo(String city){
        Intent intent = new Intent();
        intent.putExtra(PermissionC.USER_CITY, city);
        setResult(RESULT_OK, intent);
        PLog.LogD("--  选中的  :"+city);
        finish();
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        isScroll = false;
        if (letterIndex.get(s) != null) {
            int position = letterIndex.get(s);
            mCity_container.setSelection(position);
            Pattern pattern = Pattern.compile("^[A-Za-z]+$");
            if (pattern.matcher(s).matches()) {
                letterOverlay.setTextSize(40);
            } else {
                letterOverlay.setTextSize(20);
            }
            letterOverlay.setText(s);
            letterOverlay.setVisibility(View.VISIBLE);
            handler.removeCallbacks(overlayThread);
            handler.postDelayed(overlayThread, 1000);
        }
    }

    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            letterOverlay.setVisibility(View.GONE);
        }
    }
}
