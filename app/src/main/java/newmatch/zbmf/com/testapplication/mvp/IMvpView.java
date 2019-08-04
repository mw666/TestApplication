package newmatch.zbmf.com.testapplication.mvp;

/**
 * MVP  根视图
 *
 * @author ZhongDaFeng
 */
public interface IMvpView {
    //用于接收失败的结果
    void showFail(int Code, String failMsg);
}
