package newmatch.zbmf.com.testapplication.adapters.pager_fragment_adapters;

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
//    private List<String> titles;

    public MainFragMentAdapter(FragmentManager fm, List<Fragment> fragmentList/*, List<String> mTitles*/) {
        super(fm);
        this.mFragmentList = fragmentList;
//        this.titles = mTitles;
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return titles != null ? titles.get(position) : "";
//    }
}
