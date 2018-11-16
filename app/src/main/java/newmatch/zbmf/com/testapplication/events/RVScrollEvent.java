package newmatch.zbmf.com.testapplication.events;

/**
 * Created by pq
 * on 2018/11/16.
 * 监听RecyclerView滑动的事件总线
 * 这个是专门监听RecyclerView的滑动是否到顶
 */

public class RVScrollEvent {
     //  1   表示不能往上滑，已经到底部   2   表示还可以上滑，还未到底部
    //   -1  表示不能往下滑，已经到顶部   -2   表示还可以下滑，未到顶部
    public static final Integer UP_REACH_BOTTOM = 1;
    public static final Integer UP_NO_REACH_BOTTOM = 2;
    public static final Integer DOWN_REACH_TOP = -1;
    public static final Integer DOWN_NO_REACH_TOP = -2;
    private Integer RVState;

    public RVScrollEvent() {}

    public RVScrollEvent(Integer RVState) {
        this.RVState = RVState;
    }

    public Integer getRVState() {
        return RVState;
    }

    public void setRVState(Integer RVState) {
        this.RVState = RVState;
    }
}
