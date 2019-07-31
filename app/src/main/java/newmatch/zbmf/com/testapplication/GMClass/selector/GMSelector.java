package newmatch.zbmf.com.testapplication.GMClass.selector;

import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.R;

/**
 * Created By pq
 * on 2019/7/30
 * 时间，地点，条件，选择器
 */
public class GMSelector {

    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private static GMSelector gmSelector = null;

    public static GMSelector instance(Context context) {
        if (gmSelector == null) {
            synchronized (GMSelector.class) {
                if (gmSelector == null) {
                    gmSelector = new GMSelector(context);
                }
            }
        }
        return gmSelector;
    }

    private Context context;

    public GMSelector(Context context) {
        this.context = context;
        if (this.options1Items == null || options1Items.size() == 0) {
            initJsonData(context);
        }
    }

    /**
     * 解析省市的Json数据
     */
    private void initJsonData(Context context) {
        /*
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         * */
        //获取assets目录下的json文件数据
        String JsonData = new GetJsonDataUtil().getJson(context, "province.json");
        //用Gson 转成实体
        ArrayList<JsonBean> jsonBean = parseData(JsonData);
        /*
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        this.options1Items = jsonBean;
        //遍历省份
        for (int i = 0; i < jsonBean.size(); i++) {
            //该省的城市列表（第二级）
            ArrayList<String> cityList = new ArrayList<>();
            //该省的所有地区列表（第三极）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();
            //遍历该省份的所有城市
            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                //添加城市
                cityList.add(cityName);
                //该城市的所有地区列表
                ArrayList<String> city_AreaList = new ArrayList<>();
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                //添加该省所有地区数据
                province_AreaList.add(city_AreaList);
            }
            //添加城市数据
            options2Items.add(cityList);
            //添加地区数据
            options3Items.add(province_AreaList);
        }
    }

    //Gson 解析
    private ArrayList<JsonBean> parseData(String result) {
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    // 弹出选择器
    public void showPickerView(SelectorCallBack selectorCallBack) {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";
                if (selectorCallBack != null)
                    selectorCallBack.selectorResult(opt1tx, opt2tx, opt3tx);
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("城市选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(ActivityCompat.getColor(context,R.color.deepPurple))//标题文字颜色
                .setSubmitColor(ActivityCompat.getColor(context,R.color.pinkColor))//确定按钮文字颜色
                .setCancelColor(ActivityCompat.getColor(context,R.color.pinkColor))//取消按钮文字颜色
                .setTitleBgColor(ActivityCompat.getColor(context, R.color.white))//标题背景颜色 Night mode
                .setBgColor(ActivityCompat.getColor(context,R.color.white_grey_1))//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
//                .setLinkage(false)//设置是否联动，默认true
//                .setLabels("省", "市", "区")//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
//                .setSelectOptions(1, 1, 1)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
//                .isDialog(true)//是否显示为对话框样式
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();
        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    //选择器自定义设置样式
//    public void setShow() {
//        Calendar selectedDate = Calendar.getInstance();
//        Calendar startDate = Calendar.getInstance();
//        //startDate.set(2013,1,1);
//        Calendar endDate = Calendar.getInstance();
//        //endDate.set(2020,1,1);
//
//        //正确设置方式 原因：注意事项有说明
//        startDate.set(2013, 0, 1);
//        endDate.set(2020, 11, 31);
//
//        new TimePickerBuilder(context, new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date, View v) {//选中事件回调
//
//            }
//        })
//                .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
//                .setCancelText("Cancel")//取消按钮文字
//                .setSubmitText("Sure")//确认按钮文字
//                .setContentSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
//                .setTitleText("Title")//标题文字
//                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
//                .isCyclic(true)//是否循环滚动
//                .setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                .setRangDate(startDate, endDate)//起始终止年月日设定
//                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .isDialog(true)//是否显示为对话框样式
//                .build();
//        new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
//            @Override
//            public void onOptionsSelect(int i, int i1, int i2, View view) {
//                //返回的分别是三个级别的选中位置
////                String tx = options1Items.get(options1).getPickerViewText()
////                        + options2Items.get(options1).get(option2)
////                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
//
//            }
//        }).setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
//            @Override
//            public void onOptionsSelectChanged(int options1, int options2, int options3) {
//                String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
//
//            }
//        })
//                .setSubmitText("确定")//确定按钮文字
//                .setCancelText("取消")//取消按钮文字
//                .setTitleText("城市选择")//标题
//                .setSubCalSize(18)//确定和取消文字大小
//                .setTitleSize(20)//标题文字大小
//                .setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
//                .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
//                .setContentTextSize(18)//滚轮文字大小
//                .setLinkage(false)//设置是否联动，默认true
//                .setLabels("省", "市", "区")//设置选择的三级单位
//                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .setCyclic(false, false, false)//循环与否
//                .setSelectOptions(1, 1, 1)  //设置默认选中项
//                .setOutSideCancelable(false)//点击外部dismiss default true
//                .isDialog(true)//是否显示为对话框样式
//                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
//                .build();
//
//        pvOptions.setPicker(options1Items, options2Items, options3Items);//添加数据源
//    }

}
