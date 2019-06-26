package newmatch.zbmf.com.testapplication.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Create By Administrator
 * on 2019/6/26
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> mFragments) {
        super(fm);
        this.fragments = mFragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments == null ? null : fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }
}
