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

    private int status_code; // 返回的code
    private T result_data; // 具体的数据结果
    private String msg; // message 可用来返回接口的说明

    public int getCode() {
        return status_code;
    }

    public void setCode(int code) {
        this.status_code = code;
    }

    public T getData() {
        return result_data;
    }

    public void setData(T data) {
        this.result_data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
