package newmatch.zbmf.com.testapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by **
 * on 2018/8/23.
 * 测试的实体类
 */

public class BannerService implements Parcelable{
//    {
//        "data":[{
//        "desc":"通过聊天机器人打造智能化工作流", "id":17, "imagePath":
//        "http://www.wanandroid.com/blogimgs/dd6017a9-f79b-45e3-ae1b-5719a17b0cac.png", "isVisible":
//        1, "order":0, "title":"通过聊天机器人打造智能化工作流", "type":0, "url":
//        "https://bearychat.com?utm_source=wanandroid&amp;utm_medium=banner&amp;utm_campaign=pc"
//    }],"errorCode":0, "errorMsg":""
//    }
    private ArrayList<Data> data;
    private Integer errorCode;
    private String errorMsg;

    protected BannerService(Parcel in) {
        data = in.createTypedArrayList(Data.CREATOR);
        if (in.readByte() == 0) {
            errorCode = null;
        } else {
            errorCode = in.readInt();
        }
        errorMsg = in.readString();
    }

    public static final Creator<BannerService> CREATOR = new Creator<BannerService>() {
        @Override
        public BannerService createFromParcel(Parcel in) {
            return new BannerService(in);
        }

        @Override
        public BannerService[] newArray(int size) {
            return new BannerService[size];
        }
    };

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "BannerService{" +
                "data=" + data +
                ", errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
        if (errorCode == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(errorCode);
        }
        dest.writeString(errorMsg);
    }

    public static class Data implements Parcelable{
        private String desc;
        private Integer id;
        private String imagePath;
        private Integer isVisible;
        private Integer order;
        private String title;
        private Integer type;
        private String url;

        protected Data(Parcel in) {
            desc = in.readString();
            if (in.readByte() == 0) {
                id = null;
            } else {
                id = in.readInt();
            }
            imagePath = in.readString();
            if (in.readByte() == 0) {
                isVisible = null;
            } else {
                isVisible = in.readInt();
            }
            if (in.readByte() == 0) {
                order = null;
            } else {
                order = in.readInt();
            }
            title = in.readString();
            if (in.readByte() == 0) {
                type = null;
            } else {
                type = in.readInt();
            }
            url = in.readString();
        }

        public static final Creator<Data> CREATOR = new Creator<Data>() {
            @Override
            public Data createFromParcel(Parcel in) {
                return new Data(in);
            }

            @Override
            public Data[] newArray(int size) {
                return new Data[size];
            }
        };

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public Integer getIsVisible() {
            return isVisible;
        }

        public void setIsVisible(Integer isVisible) {
            this.isVisible = isVisible;
        }

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "desc='" + desc + '\'' +
                    ", id=" + id +
                    ", imagePath='" + imagePath + '\'' +
                    ", isVisible=" + isVisible +
                    ", order=" + order +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    ", url='" + url + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(desc);
            if (id == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(id);
            }
            dest.writeString(imagePath);
            if (isVisible == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(isVisible);
            }
            if (order == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(order);
            }
            dest.writeString(title);
            if (type == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(type);
            }
            dest.writeString(url);
        }
    }
}
