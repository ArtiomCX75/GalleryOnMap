package com.dmytrobohdanov.galleryonmap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.dmytrobohdanov.galleryonmap.temp.GalleryItemsDataKeeper;

public class MainActivity extends AppCompatActivity {

    //amount of items in gallery
    public static int GALLERY_ITEMS_AMOUNT;
    ViewPager pager;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GALLERY_ITEMS_AMOUNT = GalleryItemsDataKeeper.getInstance().getItemAmount();

        pager = (ViewPager) findViewById(R.id.galleryHolder);
        pagerAdapter = new GalleryItemHolderFragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                //todo: set id of item to menu items
//                Log.d(TAG, "onPageSelected, position = " + position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private class GalleryItemHolderFragmentAdapter extends FragmentStatePagerAdapter {

        public GalleryItemHolderFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return GalleryItemHolderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return GALLERY_ITEMS_AMOUNT;
        }

    }

}