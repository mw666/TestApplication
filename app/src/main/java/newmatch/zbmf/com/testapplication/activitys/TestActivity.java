package newmatch.zbmf.com.testapplication.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import newmatch.zbmf.com.testapplication.R;
import newmatch.zbmf.com.testapplication.adapters.StaggerAdapter;
import newmatch.zbmf.com.testapplication.utils.ToastUtils;

public class TestActivity extends AppCompatActivity implements StaggerAdapter.ClickCardView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        RecyclerView rv = findViewById(R.id.rv);

        rv.setHasFixedSize(true);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);

        StaggerAdapter testAdapter = new StaggerAdapter(this);
        rv.setAdapter(testAdapter);
        testAdapter.setClickCardView(this);

        List<Integer> imgs = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            imgs.add(R.drawable.mn1);
            imgs.add(R.drawable.mn2);
            imgs.add(R.drawable.mn3);
            imgs.add(R.drawable.mn4);
            imgs.add(R.drawable.mn5);
            imgs.add(R.drawable.mn6);
            imgs.add(R.drawable.mn7);
            imgs.add(R.drawable.mn8);
            imgs.add(R.drawable.mn9);
            imgs.add(R.drawable.mn10);
            imgs.add(R.drawable.mn11);
            imgs.add(R.drawable.mn12);
            imgs.add(R.drawable.mn13);
            imgs.add(R.drawable.mn14);
            imgs.add(R.drawable.mn15);
            imgs.add(R.drawable.mn16);
            imgs.add(R.drawable.mn17);
            imgs.add(R.drawable.mn18);
            imgs.add(R.drawable.mn19);
            imgs.add(R.drawable.mn20);
            imgs.add(R.drawable.mn21);
            imgs.add(R.drawable.mn22);
            imgs.add(R.drawable.mn23);
            imgs.add(R.drawable.mn24);
            imgs.add(R.drawable.mn25);
            imgs.add(R.drawable.mn26);
            imgs.add(R.drawable.mn27);
            imgs.add(R.drawable.mn28);
            imgs.add(R.drawable.mn29);
            imgs.add(R.drawable.mn30);
            imgs.add(R.drawable.mn31);
            imgs.add(R.drawable.mn32);
            imgs.add(R.drawable.mn33);
            imgs.add(R.drawable.mn34);
            imgs.add(R.drawable.mn35);
            imgs.add(R.drawable.mn36);
            imgs.add(R.drawable.mn37);
        }
        testAdapter.addData(imgs);


    }

    @Override
    public void clickCardView(int position) {
        ToastUtils.showSquareTvToast(this, "  position:" + position);
    }
}
