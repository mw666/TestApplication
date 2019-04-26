package newmatch.zbmf.com.testapplication.permissions;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.callback.DialogActCallBack;
import newmatch.zbmf.com.testapplication.dialogs.DialogUtils;
import newmatch.zbmf.com.testapplication.dialogs.MyDialogUtil;
import newmatch.zbmf.com.testapplication.utils.MyActivityManager;

public class PermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        Intent intent = getIntent();
        String[] permissions = intent.getStringArrayExtra(PermissionC.init_permis);
        showPerDialog(permissions);
//        requestPermissions(permissions);


    }

    //申请权限
    private void requestPermissions(String[] permissions) {
        ActivityCompat.requestPermissions(PermissionActivity.this
                , permissions, PermissionC.init_permis_code);
    }

    //存储违背同意的权限
    private List<String> deniedPermissions = null;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionC.init_permis_code) {
            if (deniedPermissions == null) {
                deniedPermissions = new ArrayList<>();
            } else {
                if (deniedPermissions.size() > 0) {
                    deniedPermissions.clear();
                }
            }
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    //改权限未被同意
                    deniedPermissions.add(permissions[i]);
                }
            }
            if (deniedPermissions.size() > 0) {
                //展示请同意以下权限的对话框子
                int size = deniedPermissions.size();
                showPerDialog(deniedPermissions.toArray(new String[size]));
            } else {
                setResult(0xf13);
                PermissionActivity.this.finish();
            }
        }
    }

    //展示申请权限的dialog
    private void showPerDialog(String[] pers) {
        View view = LayoutInflater.from(this).inflate(R.layout.permission_dialog_bg, null);
        MyDialogUtil.showPermissionAlert(PermissionActivity.this,
                PermissionActivity.this, "", view, R.drawable.dialog_bg,
                false, new DialogActCallBack() {
                    @Override
                    public void cancelActCallBack(AlertDialog alertDialog) {
                        alertDialog.dismiss();
                        MyActivityManager.getMyActivityManager().removeAllAct();
                    }

                    @Override
                    public void positionActCallBack(AlertDialog alertDialog) {
                        //再次申请权限
                        requestPermissions(pers);
                        alertDialog.dismiss();
                    }
                });
    }

}
