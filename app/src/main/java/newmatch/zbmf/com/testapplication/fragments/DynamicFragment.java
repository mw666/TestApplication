package newmatch.zbmf.com.testapplication.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import newmatch.zbmf.com.testapplication.GMClass.LikeGMClass;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.DynamicAdapter;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.dialogs.BottomEditDialog;
import newmatch.zbmf.com.testapplication.dialogs.DialogUtils;
import newmatch.zbmf.com.testapplication.dialogs.MyDiaog;
import newmatch.zbmf.com.testapplication.interfaces.CommentArrowCallBack;
import newmatch.zbmf.com.testapplication.interfaces.LikeCallBack;
import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;
import newmatch.zbmf.com.testapplication.presenter.BasePresenter;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 * 动态Fragment
 * <p>
 * <p>
 * 文件上传：https://blog.csdn.net/a992036795/article/details/74738474
 */
public class DynamicFragment extends BaseFragment implements DynamicAdapter.CommentDynamic
        , MyDiaog.PositiveBtnClick, LikeCallBack, CommentArrowCallBack {


    private SmartRefreshLayout mDynamicRefreshLayout;

    public DynamicFragment() {

    }

    private Handler mHandler = new Handler();
    private Boolean isRefresh = false;
    private Boolean isLoad = false;

    public static DynamicFragment dynamicInstance() {
        DynamicFragment dynamicFragment = new DynamicFragment();
        Bundle bundle = new Bundle();
        dynamicFragment.setArguments(bundle);
        return dynamicFragment;
    }

    @Override
    protected Integer layoutId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    protected void initView() {
        TextView topbarTitle = bindView(R.id.topbar_title);
        topbarTitle.setText(getString(R.string.qy_dynamic));

        mDynamicRefreshLayout = bindView(R.id.dynamicRefreshLayout);
        RecyclerView dynamicRV = bindView(R.id.dynamicRV);
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

          // TODO: 2018/10/10 监听视图的滑动
//        mDynamicRefreshLayout.

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
        return true;
    }

    @Override
    public void commentDynamic(int position) {
        new BottomEditDialog(getActivity()).getDialog().showI().setOnAddClick(comment -> {
            //comment是评论的内容,点击发送评论内容
            ToastUtils.showSingleToast(MyApplication.getInstance(), comment);
        });
    }

    public void showDialog() {
//        MyDiaog myDiaog = new TestDialog01();
//        myDiaog.setPositiveBtnClick(this);
//        myDiaog.show(getFragmentManager(),"dialog");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_dynamic_arrow_down_view, null);
        TextView reprint = view.findViewById(R.id.reprint);
        TextView collect = view.findViewById(R.id.collect);
        TextView report = view.findViewById(R.id.report);
        DialogUtils.instance().setView(view)
                .setIsCancel(true)
//                .setDialogStyle(R.style.Theme_Light_Dialog1)
                .setGravity(Gravity.CENTER)
                .setDialogAnimStyle(R.style.dialogAnimator01)
                .gMDialog(getActivity());
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
        showDialog();
    }
}

