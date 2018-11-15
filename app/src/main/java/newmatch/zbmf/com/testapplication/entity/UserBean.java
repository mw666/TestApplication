package newmatch.zbmf.com.testapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pq
 * on 2018/11/15.
 * 用户信息的实体类
 */

public class UserBean implements Parcelable {

    private Long userId;
    private String token;
    private String sex;
    private Integer age;
    private String truename;
    private String username;
    private String nickname;
    private String avatar;
    private String phone;
    private String email;
    private String position;
    private String longitude;
    private String latitude;
    private Integer rank;
    private String created;
    private String vipEndAt;

    protected UserBean(Parcel in) {
        userId = in.readLong();
        token = in.readString();
        sex = in.readString();
        if (in.readByte() == 0) {
            age = null;
        } else {
            age = in.readInt();
        }
        truename = in.readString();
        username = in.readString();
        nickname = in.readString();
        avatar = in.readString();
        phone = in.readString();
        email = in.readString();
        position = in.readString();
        longitude = in.readString();
        latitude = in.readString();
        if (in.readByte() == 0) {
            rank = null;
        } else {
            rank = in.readInt();
        }
        created = in.readString();
        vipEndAt = in.readString();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel in) {
            return new UserBean(in);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(userId);
        dest.writeString(token);
        dest.writeString(sex);
        if (age == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(age);
        }
        dest.writeString(truename);
        dest.writeString(username);
        dest.writeString(nickname);
        dest.writeString(avatar);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(position);
        dest.writeString(longitude);
        dest.writeString(latitude);
        if (rank == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rank);
        }
        dest.writeString(created);
        dest.writeString(vipEndAt);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getVipEndAt() {
        return vipEndAt;
    }

    public void setVipEndAt(String vipEndAt) {
        this.vipEndAt = vipEndAt;
    }

    public static Creator<UserBean> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userId='" + userId + '\'' +
                ", token='" + token + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", truename='" + truename + '\'' +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", position='" + position + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", rank=" + rank +
                ", created='" + created + '\'' +
                ", vipEndAt='" + vipEndAt + '\'' +
                '}';
    }
}
