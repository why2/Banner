package bsd.banner;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by ShiDa.Bian on 2017/1/20.
 * Viewpager 转换基类
 */
public abstract class BasePagerTransFormer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        pagerTransForm(page, position);
    }

    /**
     * @param page     当前选中view
     * @param position 当前position
     */
    protected abstract void pagerTransForm(View page, float position);
}
