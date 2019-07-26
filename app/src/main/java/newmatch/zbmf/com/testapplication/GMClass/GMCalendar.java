package newmatch.zbmf.com.testapplication.GMClass;

import android.app.DatePickerDialog;
import android.content.Context;

import java.util.Calendar;

import newmatch.zbmf.com.testapplication.callback.DateCallBack;
import newmatch.zbmf.com.testapplication.utils.TimeUtil;

/**
 * Created by **
 * on 2018/10/10.
 * 日期时间选择通用类
 */

public class GMCalendar {

    public GMCalendar showCalemdar(Context context, DateCallBack dateCallBack){
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(context,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String ymd = TimeUtil.getYMD(calendar.getTime());
                    if (dateCallBack!=null){
                        dateCallBack.dateCallBack(ymd);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
        return this;
    }
}
