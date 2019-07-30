package newmatch.zbmf.com.testapplication.GMClass;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;

/**
 * Created By pq
 * on 2019/7/30
 * 时间，地点，条件，选择器
 */
public class GMSelector {

//    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();

    private static OptionsPickerView<Object> pvOptions;

    public static void showPickerView(Context context) {// 弹出选择器
        //返回的分别是三个级别的选中位置
//                 String tx = options1Items.get(options1).getPickerViewText()
//                         + options2Items.get(options1).get(option2)
//                         + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
//                 tvOptions.setText(tx);
//确定按钮文字
//取消按钮文字
//标题
//确定和取消文字大小
//标题文字大小
//标题文字颜色
//确定按钮文字颜色
//取消按钮文字颜色
//标题背景颜色 Night mode
//滚轮背景颜色 Night mode
//滚轮文字大小
//                .setLinkage(false)//设置是否联动，默认true
//设置选择的三级单位
//是否只显示中间选中项的label文字，false则每项item全部都带有label。
//循环与否
//设置默认选中项
//点击外部dismiss default true
//是否显示为对话框样式
//切换时是否还原，设置默认选中第一项。
        pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int i, int i1, int i2, View view) {
                //返回的分别是三个级别的选中位置
//                 String tx = options1Items.get(options1).getPickerViewText()
//                         + options2Items.get(options1).get(option2)
//                         + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
//                 tvOptions.setText(tx);
            }
        }).setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
            @Override
            public void onOptionsSelectChanged(int options1, int options2, int options3) {
                String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("城市选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
                .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
//                .setLinkage(false)//设置是否联动，默认true
                .setLabels("省", "市", "区")//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(1, 1, 1)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(true)//是否显示为对话框样式
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();

//        pvOptions.setPicker(options1Items, options2Items, options3Items);//添加数据源
    }

    public static void initOptionPicker(Context context) {//条件选择器初始化

        /**
         * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
         */

        pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
//                String tx = options1Items.get(options1).getPickerViewText()
//                        + options2Items.get(options1).get(options2)
//                        /* + options3Items.get(options1).get(options2).get(options3).getPickerViewText()*/;
//                btn_Options.setText(tx);
            }
        })
                .setTitleText("城市选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.BLACK)
                .setTitleBgColor(Color.DKGRAY)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.YELLOW)
                .setSubmitColor(Color.YELLOW)
                .setTextColorCenter(Color.LTGRAY)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("省", "市", "区")
                .setOutSideColor(0x00000000) //设置外部遮罩颜色
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
                        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
                    }
                })
                .build();

//        pvOptions.setSelectOptions(1,1);
        /*pvOptions.setPicker(options1Items);//一级选择器*/
      //  pvOptions.setPicker(options1Items, options2Items);//二级选择器
        /*pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
    }

}
