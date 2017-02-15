package bsd.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by ShiDa.Bian on 2017/1/22.
 * 无限循环banner
 * MarginLayoutParams可以设置margin的布局参数
 */
public class UnlimitedBanner extends ViewPager {
    //页面之间间距
    private int pagerItemMargin;
    //左右页面显示的宽度
    private int nearWidth;
    //设置给viewpager的间距
    private int pagerMargin;
    //自动滑动事件间隔(秒)
    private int slideTime;
    //控制自动滑动
    private Handler mHandler;
    private Runnable mRunnable;
    //当前index,1才是真正的第一个，0不是
    private int index = 1;
    //手动滚动标记
    private boolean isFinger;


    public UnlimitedBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UnlimitedBanner);
        pagerItemMargin = (int) typedArray.getDimension(R.styleable.UnlimitedBanner_pagerMargin, 40);
        nearWidth = (int) typedArray.getDimension(R.styleable.UnlimitedBanner_nearWidth, 40);
        slideTime = typedArray.getInteger(R.styleable.UnlimitedBanner_slideTime, 2) * 1000;
        pagerMargin = pagerItemMargin + nearWidth;
        typedArray.recycle();

        setPageMargin(pagerItemMargin);
        //因为当前可见为3个
        setOffscreenPageLimit(3);
    }


    /**
     * 设置适配器之类
     */
    public void setDate(final int[] a) {
        setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return a.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(container.getContext());
                imageView.setImageResource(a[position]);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        //设置页面转换
        setPageTransformer(true, new AlphaPageTransformer());
        //真正的第一个
        setCurrentItem(1);

        addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int pageIndex = position;
                index = position;

                if (position == 0) {
                    // 当视图在第一个时，将页面号设置为图片的最后一张。
                    pageIndex = a.length - 2;
                } else if (position == a.length - 1) {
                    // 当视图在最后一个是,将页面号设置为图片的第一张。
                    pageIndex = 1;
                }
                //视图是第一个或者最后一个时的操作
                if (position != pageIndex) {
                    setCurrentItem(pageIndex, false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        isFinger = true;
                        mHandler.removeCallbacks(mRunnable);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (isFinger) {
                            mHandler.postDelayed(mRunnable,slideTime);
                            isFinger = false;
                        }
                        break;
                }
            }
        });

        autoSlideing();

        //设置左右页面可见宽度
        ViewGroup.MarginLayoutParams params = (MarginLayoutParams) getLayoutParams();
        if (params != null) {
            params.setMargins(pagerMargin, 0, pagerMargin, 0);
            setLayoutParams(params);
        }
    }

    /**
     * 自动滚动
     */
    private void autoSlideing() {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                index++;
                setCurrentItem(index);
                mHandler.postDelayed(mRunnable,slideTime);
            }
        };
        mHandler.postDelayed(mRunnable,slideTime);
    }
}
