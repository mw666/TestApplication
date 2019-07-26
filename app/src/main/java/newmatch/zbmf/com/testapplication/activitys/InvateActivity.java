package newmatch.zbmf.com.testapplication.activitys;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

import newmatch.zbmf.com.testapplication.GMClass.GMCopy;
import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.utils.DrawableByteBitmapExUtils;
import newmatch.zbmf.com.testapplication.utils.fileUtils.FileUtils;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

/**
 * 邀请注册页面
 */
public class InvateActivity extends BaseActivity {

    @Override
    protected Integer layoutId() {
        return R.layout.activity_invate;
    }

    @Override
    protected void initView() {
        //全屏，内容延伸至状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Toolbar toolBar = bindView(R.id.toolbar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        bindViewWithClick(R.id.shareInviteBtn, true);
        bindViewWithClick(R.id.downLoadCode, true);
        TextView inviteCode = bindViewWithClick(R.id.inviteCode, true);
        LinearLayout myInviteCodeParent = bindView(R.id.myInviteCodeParent);

        GMCopy.instance().copyGetXY(inviteCode, this, myInviteCodeParent);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected String initTitle() {
        return "邀请有奖";
    }

    @Override
    protected Boolean showBackBtn() {
        return true;
    }

    @Override
    protected int topBarColor() {
        return 0;
    }

    @Override
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.downLoadCode:
                //点击保存下载码
                String savePath = FileUtils.getYehiImgAssistPath();
                String fileName = savePath + File.separator + "code.jpeg";
                byte[] bytes = DrawableByteBitmapExUtils.drawable2Byte(ContextCompat.getDrawable(this,
                        R.drawable.ic_2_code));
                boolean save = FileUtils.writFile(bytes, savePath, fileName);
                if (save) ToastUtils.showSingleToast(this, "已保存到手机文件夹aYeHai下");
                break;
            case R.id.shareInviteBtn:
                //点击分享邀请新用户注册
                ToastUtils.showSingleToast(this, "分享注册");
                break;
        }
    }


}
