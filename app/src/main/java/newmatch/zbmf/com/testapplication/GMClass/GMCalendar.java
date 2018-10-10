package newmatch.zbmf.com.testapplication.GMClass;

import android.app.DatePickerDialog;
import android.content.Context;

import java.util.Calendar;

import newmatch.zbmf.com.testapplication.component.PLog;
import newmatch.zbmf.com.testapplication.utils.TimeUtil;

/**
 * Created by **
 * on 2018/10/10.
 * 日期时间选择通用类
 */

public class GMCalendar {

    private String mCalendar;
    private void setTime(String calendar){
        mCalendar=calendar;
    }
    public String getCalendar(){
        return mCalendar;
    }

    public GMCalendar showCalemdar(Context context){
        final Calendar calendar = Calendar.getInstance();
//        mTvDate.setText(DateUtils.date2String(calendar.getTime(), DateUtils.YMD_FORMAT));
        DatePickerDialog dialog = new DatePickerDialog(context,
                (view, year, month, dayOfMonth) -> {
                    PLog.LogD("onDateSet: year: " + year + ", month: " + month + ", dayOfMonth: " + dayOfMonth);
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    setTime(TimeUtil.getYMD(calendar.getTime()/*, DateUtils.YMD_FORMAT*/));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
        return this;
    }
}
