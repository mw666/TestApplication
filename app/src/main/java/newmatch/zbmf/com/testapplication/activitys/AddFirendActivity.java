package newmatch.zbmf.com.testapplication.activitys;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.base.BaseActivity;
import newmatch.zbmf.com.testapplication.base.MyApplication;
import newmatch.zbmf.com.testapplication.utils.TextContentUtil;
import newmatch.zbmf.com.testapplication.views.circleImageView.RoundImageView;

public class AddFirendActivity extends BaseActivity {

    @Override
    protected Integer layoutId() {
        return R.layout.activity_add_firend;
    }

    @Override
    protected void initView() {
        //全屏，内容顶入状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        RoundImageView userAvatarIv = bindViewWithClick(R.id.userAvatarIv,true);
        TextView userName = bindView(R.id.userName);
        TextView userSexAge = bindView(R.id.userSexAge);
        Button sendAddFriendMsgBtn = bindView(R.id.sendAddFriendMsgBtn);
        EditText addFriendMsgEt = bindView(R.id.addFriendMsgEt);
        EditText addFriendRemarkEt = bindView(R.id.addFriendRemarkEt);
        ImageView clearRemarkIv = bindViewWithClick(R.id.clearRemarkEt, true);
        TextView  fenZuTitle = bindViewWithClick(R.id.fenZuTitle, true);
        ImageView fenZuIvBtn = bindViewWithClick(R.id.fenZuIvBtn, true);

        TextContentUtil.showOrHideClearIv(addFriendRemarkEt,clearRemarkIv);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected String initTitle() {
        return getString(R.string.add_user);
    }

    @Override
    protected Boolean showBackBtn() {
        return true;
    }

    @Override
    protected int topBarColor() {
        return MyApplication.getInstance().getResources().getColor(R.color.deepPurple);
    }

    @Override
    protected void onViewClick(View view) {

    }



}
