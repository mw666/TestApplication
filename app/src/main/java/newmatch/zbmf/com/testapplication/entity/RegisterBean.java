package newmatch.zbmf.com.testapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pq
 * on 2018/11/15.
 * 注册返回的数据实体类
 */

public class RegisterBean extends CommonBean implements Parcelable {

    //    {
//        "status_code":200, "msg":"success", "result_data":{
//        "userId":1, "token":"c50e65cea1934afeb504fac84ace77ec", "sex":null, "age":null, "truename":
//        null, "username":"22", "nickname":"33", "avatar":null, "phone":"15623525696", "email":
//        null, "position":null, "longitude":null, "latitude":null, "rank":0, "created":
//        null, "vipEndAt":null
//    }
//    }
    private UserBean result_data;

    protected RegisterBean(Parcel in) {
        super(in);
    }

    public UserBean getResult_data() {
        return result_data;
    }

    public void setResult_data(UserBean result_data) {
        this.result_data = result_data;
    }

    @Override
    public String toString() {
        return "RegisterBean{" +
                "result_data=" + result_data +
                '}';
    }
}
