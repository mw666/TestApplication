package newmatch.zbmf.com.testapplication.views.customViewPager;

/**
 * Create By Administrator
 * on 2019/7/7
 */
public interface MZHolderCreator<VH extends MZViewHolder> {
    /**
     * 创建ViewHolder
     * @return
     */
    public VH createViewHolder();
}