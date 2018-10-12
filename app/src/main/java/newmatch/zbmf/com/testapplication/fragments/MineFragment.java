package newmatch.zbmf.com.testapplication.fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhihu.matisse.Matisse;

import java.util.List;

import newmatch.zbmf.com.testapplication.GMClass.GMCopy;
import newmatch.zbmf.com.testapplication.GMClass.GMPermissions;
import newmatch.zbmf.com.testapplication.GMClass.GMSelectImg;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.MineViewAdapter;
import newmatch.zbmf.com.testapplication.assist.GlideUtil;
import newmatch.zbmf.com.testapplication.base.BaseFragment;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.custom_view.RoundImageView;
import newmatch.zbmf.com.testapplication.dialogs.DialogUtils;
import newmatch.zbmf.com.testapplication.interfaces.MineViewClick;
import newmatch.zbmf.com.testapplication.listeners.OnceClickListener;
import newmatch.zbmf.com.testapplication.permissions.PermissionC;
import newmatch.zbmf.com.testapplication.presenter.BasePresenter;
import newmatch.zbmf.com.testapplication.utils.ContainsEmojiEditText;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 * 我的Fragment
 */
public class MineFragment extends BaseFragment implements MineViewClick,GMPermissions.PermissionCallBackExcute{

    private RoundImageView mAvatarIv;

    /*private String[] titles = {getActivity().getString(R.string.open_vip),
                    getActivity().getString(R.string.update_pass_word),
                    getActivity().getString(R.string.options_back),
                    getActivity().getString(R.string.version_update),
                    getActivity().getString(R.string.login_out)
            };
        */
    public MineFragment() {
        // Required empty public constructor
    }

    private GMPermissions mGmPermissions;
    public static MineFragment mineInstance() {
        MineFragment mineFragment = new MineFragment();
        Bundle bundle = new Bundle();
        mineFragment.setArguments(bundle);
        return mineFragment;
    }

    @Override
    protected Integer layoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        TextView toolbar_title = bindView(R.id.toolbar_title);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText(getString(R.string.mine));
        TextView userName = bindViewWithClick(R.id.userName,true);
        TextView userSexAndAge = bindView(R.id.userSexAndAge);
        TextView userAccount = bindView(R.id.userAccount);
        mAvatarIv = bindViewWithClick(R.id.avatarIv, true);

        userName.setVisibility(View.VISIBLE);
        userSexAndAge.setVisibility(View.VISIBLE);
        userAccount.setVisibility(View.VISIBLE);

        userName.setText("二狗子");
        userSexAndAge.setText("女 23岁");
        userAccount.setText("wind_143u9145u91");

        //申请权限
        //申请权所需要的对象
        mGmPermissions = GMPermissions.instance().setParameter(getActivity(), getActivity(), PermissionC.WR_FILE_CODE);
        mGmPermissions.setPermissionCallBackExcute(this);


        String[] titles = {getActivity().getString(R.string.open_vip),
                getActivity().getString(R.string.update_pass_word),
                getActivity().getString(R.string.options_back),
                getActivity().getString(R.string.version_update),
                getActivity().getString(R.string.login_out)
        };

        RecyclerView userMineRV = bindView(R.id.userMineRV);
        userMineRV.setLayoutManager(new LinearLayoutManager(getActivity(),
                OrientationHelper.VERTICAL, false));
        MineViewAdapter mineViewAdapter = new MineViewAdapter(titles, getActivity());
        mineViewAdapter.setMineViewClick(this);
        userMineRV.setAdapter(mineViewAdapter);
        //设置textView的复制操作
        String copyUserName = new GMCopy<TextView>().copyContent(userName, getActivity());
        String copyUserAccount = new GMCopy<TextView>().copyContent(userAccount, getActivity());

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
    switch (view.getId()){
        case R.id.avatarIv:
            //对应的是Build.Version_code  16
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                GMPermissions.skipPermissionActivity(getActivity(),
                        PermissionC.WR_FILES_PERMISSION,PermissionC.PIC_IMG_VIDEO_CODE,
                        getString(R.string.get_img_tip));
            }else {
                //选择图片
                new GMSelectImg().picImgsOrVideo(getActivity(), PermissionC.PIC_IMG_VIDEO_CODE,1);
            }
            break;
        case R.id.userName:
            View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.update_et_view, null);
            ContainsEmojiEditText nickEt = inflate.findViewById(R.id.nickEt);
            ImageView sendNick = inflate.findViewById(R.id.sendNick);
            DialogUtils.instance()
                    .setDialogAnimStyle(R.style.dialogAnimator01)
                    .setDialogStyle(R.style.dialog)
                    .setGravity(Gravity.BOTTOM)
                    .setView(inflate);
            sendNick.setOnClickListener(new OnceClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    String nick = nickEt.getText().toString();
                    ToastUtils.showSingleToast(MyApplication.getInstance(),"获取到的昵称："+nick);


                }
            });
            break;
    }
    }

    @Override
    protected Boolean setViewEnterStatuBar() {
        return true;
    }


    @Override
    public void clickVip() {

    }

    @Override
    public void clickUpdatePassWord() {

    }

    @Override
    public void clickOptionsUp() {

    }

    @Override
    public void clickVertionUpdate() {

    }

    @Override
    public void clickLoginOut() {

    }

    @Override
    public void excutePermissionCodes() {
        //选择图片
        new GMSelectImg().picImgsOrVideo(getActivity(), PermissionC.PIC_IMG_VIDEO_CODE,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取结果
        switch (requestCode) {
            case PermissionC.PIC_IMG_VIDEO_CODE:
                //选择图片的结果
                if (resultCode== Activity.RESULT_OK){
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    //设置选择的图片
                    GlideUtil.loadCircleImage(getActivity(),
                            R.drawable.touxiang_icon, mSelected.get(0), mAvatarIv);
                    // TODO: 2018/10/9 上传选择的图片 --->用户图片

                }
                break;
        }
    }
}
