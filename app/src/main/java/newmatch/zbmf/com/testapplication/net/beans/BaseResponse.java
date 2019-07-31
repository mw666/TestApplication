package newmatch.zbmf.com.testapplication.net.beans;

/**
 * Created By pq
 * on 2019/7/31
 */
public class BaseResponse<T> {

    /*
    wanAndroid返回的数据结构的定义
    {
        "data": ...,
        "errorCode": 0,
            "errorMsg": ""
    }*/

    private int errorCode; // 返回的code
    private T data; // 具体的数据结果
    private String errorMsg; // message 可用来返回接口的说明

    public int getCode() {
        return errorCode;
    }

    public void setCode(int code) {
        this.errorCode = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return errorMsg;
    }

    public void setMsg(String msg) {
        this.errorMsg = msg;
    }
}
