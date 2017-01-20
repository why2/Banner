package bsd.banner;

import android.view.View;

/**
 * Created by ShiDa.Bian on 2017/1/20.
 */
public class AlphaPageTransformer extends BasePagerTransFormer {
    private static final float DEFAULT_MIN_ALPHA = 0.5f;

    @Override
    protected void pagerTransForm(View page, float position) {
        page.setScaleX( 0.999f);

        if (position < -1)
        { // [-Infinity,-1)
            page.setAlpha(DEFAULT_MIN_ALPHA);
        } else if (position <= 1)
        { // [-1,1]

            if (position < 0) //[0，-1]
            {           //[1,min]
                float factor = DEFAULT_MIN_ALPHA + (1 - DEFAULT_MIN_ALPHA) * (1 + position);
                page.setAlpha(factor);
            } else//[1，0]
            {
                //[min,1]
                float factor = DEFAULT_MIN_ALPHA + (1 - DEFAULT_MIN_ALPHA) * (1 - position);
                page.setAlpha(factor);
            }
        } else
        { // (1,+Infinity]
            page.setAlpha(DEFAULT_MIN_ALPHA);
        }
    }
}
