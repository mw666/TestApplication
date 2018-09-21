package newmatch.zbmf.com.testapplication.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by **
 * on 2018/9/21.
 */

public class MsgTabAdapter extends FragmentStatePagerAdapter {
    private String[] mTabtitles;
    private List<Fragment> mFragmentList;

    public MsgTabAdapter(FragmentManager fm, String[] tabtitles, List<Fragment> fragmentList) {
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
        return mTabtitles[position];
    }
}
