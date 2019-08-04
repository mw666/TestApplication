package newmatch.zbmf.com.testapplication.mvp;

/**
 * MVP  根Model
 * MvpModel创建之后全局静态持有，因此不能持有短生命周期的对象，避免内存泄漏
 *
 * @author ZhongDaFeng
 */
public interface IMvpModel {
    //取消请求
    void cancel();
}
