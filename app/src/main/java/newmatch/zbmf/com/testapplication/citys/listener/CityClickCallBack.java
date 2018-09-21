package newmatch.zbmf.com.testapplication.citys.listener;

import android.widget.TextView;

/**
 * Created by **
 * on 2018/9/12.
 */

public interface CityClickCallBack {

    //传入当前定位的城市
    void locationCity(String locatingCity);

    //传入选中的当前访问的热门城市
    void selectHotCity(String hotCity);

    //传入选中的最近访问的城市
    void selectVistCity(String vistCity);

    //传入当前选中的全部城市
    void selectAllCity(String allCity);

    //传出正在查看的城市TV
    void setCurrentSeeLocation(String loaction,TextView locationTv);
}
