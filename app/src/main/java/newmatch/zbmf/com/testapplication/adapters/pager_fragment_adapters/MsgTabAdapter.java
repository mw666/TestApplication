package newmatch.zbmf.com.testapplication.adapters.pager_fragment_adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by **
 * on 2018/9/21.
 */

public class MsgTabAdapter extends FragmentPagerAdapter {
    private List<String> mTabtitles;
    private List<Fragment> mFragmentList;

    public MsgTabAdapter(FragmentManager fm, List<String> tabtitles,
                         List<Fragment> fragmentList) {
        super(fm);
        this.mTabtitles = tabtitles;
        this.mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabtitles.size() == 0 ? null : mTabtitles.get(position);
    }
}
