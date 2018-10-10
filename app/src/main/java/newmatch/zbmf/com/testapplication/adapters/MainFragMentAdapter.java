package newmatch.zbmf.com.testapplication.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by **
 * on 2018/9/13.
 */

public class MainFragMentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;

    public MainFragMentAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.mFragmentList=fragmentList;
    }

    @Override
    public int getCount() {
        return mFragmentList==null?0:mFragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

}
