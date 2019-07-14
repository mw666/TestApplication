package newmatch.zbmf.com.testapplication.fragments.dynamic_fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import newmatch.zbmf.com.testapplication.GMClass.LikeGMClass;
import newmatch.zbmf.com.testapplication.MainActivity;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.DynamicAdapter;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.dialogs.DialogUtils;
import newmatch.zbmf.com.testapplication.dialogs.MyDiaog;
import newmatch.zbmf.com.testapplication.interfaces.CommentArrowCallBack;
import newmatch.zbmf.com.testapplication.interfaces.LikeCallBack;
import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;
import newmatch.zbmf.com.testapplication.presenter.presenterIml.BasePresenter;
import newmatch.zbmf.com.testapplication.utils.ContainsEmojiEditText;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

/**
 * Create By Administrator
 * on 2019/7/14
 * 动态类型的fragment
 */
public class DynamicTypeFragment extends BaseFragment implements DynamicAdapter.CommentDynamic
        , MyDiaog.PositiveBtnClick, LikeCallBack, CommentArrowCallBack {

    private MainActivity activity;
    private Handler mHandler = new Handler();
    private Boolean isRefresh = false;
    private Boolean isLoad = false;

    private SmartRefreshLayout mDynamicRefreshLayout;

    public static DynamicTypeFragment newInstance(String type) {
        DynamicTypeFragment fragment = new DynamicTypeFragment();
        Bundle args = new Bundle();
        args.putString("type", type);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected Integer layoutId() {
        return R.layout.fragment_user_dynamic;
    }

    @Override
    protected void initView() {
        //获取宿主activity的对象
        activity = (MainActivity) getActivity();
        mDynamicRefreshLayout = bindView(R.id.dynamicRefreshLayout);

        RecyclerView dynamicRV = bindView(R.id.dynamicRV);

        //设置刷新控件的内容
        setRefreshContent(dynamicRV);


    }

    @Override
    protected void initData() {

    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onViewClick(View view) {

    }

    @Override
    protected Boolean setViewEnterStatuBar() {
        return false;
    }


    //模拟设置刷新控件的内容
    private void setRefreshContent(RecyclerView dynamicRV) {
        //支持嵌套滑动
        mDynamicRefreshLayout.setEnableNestedScroll(true);
        dynamicRV.setLayoutManager(new LinearLayoutManager(getActivity(),
                OrientationHelper.VERTICAL, false));
        DynamicAdapter dynamicAdapter = new DynamicAdapter(getActivity());
        dynamicAdapter.setCommentDynamic(this);
        dynamicAdapter.setLikeCallBack(this);
        dynamicAdapter.setCommentArrowCallBack(this);
        dynamicRV.setAdapter(dynamicAdapter);

        //刷新
        mDynamicRefreshLayout.setOnRefreshListener(refreshLayout -> {
            isRefresh = true;
            mHandler.postDelayed(mRunnable, 3000);
        });
        //加载更多
        mDynamicRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            isLoad = true;
            mHandler.postDelayed(mRunnable, 3000);
        });
        //监听RecyclerView的滑动
        moniteRvScroll(dynamicRV, activity);
    }

    //模拟数据加载刷新
    private Runnable mRunnable = () -> {
        if (isRefresh) {
            ToastUtils.showSingleToast(MyApplication.getInstance(), "数据刷新中....");
            mDynamicRefreshLayout.finishRefresh(true);
            isRefresh = false;
        } else if (isLoad) {
            ToastUtils.showSingleToast(MyApplication.getInstance(), "数据加载中....");
            mDynamicRefreshLayout.finishLoadMore(true);
            isLoad = false;
        }
    };

    @Override
    public void commentDynamic(int position) {
        /*new BottomEditDialog(getActivity()).getDialog().showI().setOnAddClick(comment -> {
            //comment是评论的内容,点击发送评论内容
            ToastUtils.showSingleToast(MyApplication.getInstance(), comment);
        });*/
        showBottomDialog();
    }

    @Override
    public void dialogFgPositiveBtnClick() {
        ToastUtils.showSingleToast(MyApplication.getInstance(), "点击了确定按钮");
    }

    @Override
    public void likeCallBack(int position, TextView likeTv) {
        //点赞的设置
        LikeGMClass.clickTvLike(getActivity(), getActivity(), likeTv);
    }

    @Override
    public void arrowClickCallBack(int position) {
        //下拉对话框的弹出
        showPopupDialog();
    }

    private void showPopupDialog() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_dynamic_arrow_down_view, null);
        TextView reprint = view.findViewById(R.id.reprint);
        TextView collect = view.findViewById(R.id.collect);
        TextView report = view.findViewById(R.id.report);
        DialogUtils.instance().setView(view)
                .setIsCancel(true)
                .setHasMargin(true)
                .setDialogStyle(R.style.dialog)
                .setGravity(Gravity.CENTER)
                .setDialogDecoeViewBg(R.drawable.add_friend_et_bg)
                //                .setDialogAnimStyle(R.style.dialogAnimator01)
                .gMDialog(getActivity(), getActivity());
        reprint.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //转载
                ToastUtils.showSingleToast(MyApplication.getInstance(), "转载");
            }
        });
        collect.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //收藏
                ToastUtils.showSingleToast(MyApplication.getInstance(), "收藏");
            }
        });
        report.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                //举报
                ToastUtils.showSingleToast(MyApplication.getInstance(), "举报");
            }
        });
    }

    private void showBottomDialog() {
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.bottom_edit_dailog, null);
        ContainsEmojiEditText inputRemark = dialogView.findViewById(R.id.input_remark);
        ImageView sendComment = dialogView.findViewById(R.id.sendComment);
        DialogUtils.instance()
                .setGravity(Gravity.BOTTOM)
                .setView(dialogView)
                .setDialogStyle(R.style.dialog)
                .setDialogAnimStyle(R.style.alertDialogStyle01)
                .showAlertDialog(getActivity(), getActivity());
       /* DialogUtils.instance()
//                .setDialogAnimStyle(R.style.dialogAnimator01)
                .setGravity(Gravity.BOTTOM)
                .setHasMargin(false)
                .setIsCancel(true)
                .setView(dialogView)
                .gMDialog(getActivity(),getActivity());*/
        sendComment.setOnClickListener(new OnceClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                String comment = inputRemark.getText().toString();
                //comment是评论的内容,点击发送评论内容---->调用接口，提交评论
                ToastUtils.showSingleToast(MyApplication.getInstance(), comment);

            }
        });
    }

    //监听RecyclerView的滑动
    private void moniteRvScroll(RecyclerView rv, MainActivity activity) {
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int rvState = -1;
            int correctYCount = 0;
            int bearYCount = 0;
            float alpha;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //状态有三种：0：停止  1：拖动  2：滑翔
                rvState = newState;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy < 0) {
                    //向下滑动
                    correctYCount += dy;
                    //------>不透明
                    float hh = (80 + correctYCount);
                    if (hh <= 1f) {
                        alpha = 1;
                    } else {
                        alpha = (Math.abs(hh)) / 100;
                    }
                }
                if (dy > 0) {
                    //向上滑动
                    bearYCount += dy;
                    if ((Math.abs(bearYCount)) >= 80) {
                        alpha = 1 - (float) 80 / 100;
                    } else {
                        alpha = (1 - Math.abs((float) bearYCount) / 100);
                    }
                }
                if (rvState == 0) {
                    //静止  ---》correctYCount=0;
                    correctYCount = 0;
                    bearYCount = 0;
                }
            }
        });
    }


}
