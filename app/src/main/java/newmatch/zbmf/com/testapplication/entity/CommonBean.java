package newmatch.zbmf.com.testapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pq
 * on 2018/11/15.
 */

public class CommonBean implements Parcelable{
    private Integer status_code;
    private String msg;
//    private Object result_data;

    protected CommonBean(Parcel in) {
        if (in.readByte() == 0) {
            status_code = null;
        } else {
            status_code = in.readInt();
        }
        msg = in.readString();
    }

    public static final Creator<CommonBean> CREATOR = new Creator<CommonBean>() {
        @Override
        public CommonBean createFromParcel(Parcel in) {
            return new CommonBean(in);
        }

        @Override
        public CommonBean[] newArray(int size) {
            return new CommonBean[size];
        }
    };

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

//    public Object getResult_data() {
//        return result_data;
//    }
//
//    public void setResult_data(Object result_data) {
//        this.result_data = result_data;
//    }

    @Override
    public String toString() {
        return "CommonBean{" +
                "status_code=" + status_code +
                ", msg='" + msg + '\'' +
//                ", result_data=" + result_data +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (status_code == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(status_code);
        }
        dest.writeString(msg);
    }
}
