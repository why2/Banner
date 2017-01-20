package bsd.banner;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private ListView mListView;
    private int[] a = new int[]{
            R.drawable.d,
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.a,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(R.id.lv);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        mViewPager = (ViewPager) findViewById(R.id.banner_vp);
        mViewPager.setPageMargin(100);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new PagerAdapter() {
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

        mViewPager.setPageTransformer(true, new AlphaPageTransformer());
        mViewPager.setCurrentItem(1);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                if (position==0){
//                    mViewPager.setCurrentItem(a.length-2,false);
//                }
//                if (position==a.length-2){
//                    mViewPager.setCurrentItem(1,false);
//                }
            }

            @Override
            public void onPageSelected(int position) {
                int pageIndex = position;

                if (position == 0) {
                    // 当视图在第一个时，将页面号设置为图片的最后一张。
                    pageIndex = a.length - 2;
                } else if (position == a.length - 1) {
                    // 当视图在最后一个是,将页面号设置为图片的第一张。
                    pageIndex = 1;
                }
                if (position != pageIndex) {
                    mViewPager.setCurrentItem(pageIndex, false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
