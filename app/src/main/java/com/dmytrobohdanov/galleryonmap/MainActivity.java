package com.dmytrobohdanov.galleryonmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.dmytrobohdanov.galleryonmap.Items.ItemsCreator;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivityTag";

    //float action button/menu IDs
    private static final int ID_ADD_ITEM_FAB_MENU = R.id.add_item_fab_menu;
    private static final int ID_FAB_ADD_PHOTO = R.id.fab_add_photo;
    private static final int ID_FAB_ADD_VIDEO = R.id.fab_add_video;
    private static final int ID_FAB_ADD_DOWNLOAD = R.id.fab_add_download;
    private static final int ID_FAB_ADD_FROM_URL = R.id.fab_add_by_url;

    ViewPager viewPager;
    PagerAdapter pagerAdapter;

    GalleryItemsDataKeeper dataKeeper;
    DataBaseHelper dataBase;

    //float action menu items
    FloatingActionMenu fabAddItemMenu;
    FloatingActionButton fabAddPhoto;
    FloatingActionButton fabAddVideo;
    FloatingActionButton fabDownload;
    FloatingActionButton fabAddFromUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing database
        dataBase = DataBaseHelper.getInstance(this);

        //initializing ItemsCreator
        ItemsCreator itemsCreator = new ItemsCreator(dataBase);

        //creating data keeper
        dataKeeper = GalleryItemsDataKeeper.getInstance();

        //setting float action buttons
        fabAddItemMenu = (FloatingActionMenu) findViewById(ID_ADD_ITEM_FAB_MENU);
        fabAddPhoto = (FloatingActionButton) findViewById(ID_FAB_ADD_PHOTO);
        fabAddVideo = (FloatingActionButton) findViewById(ID_FAB_ADD_VIDEO);
        fabDownload = (FloatingActionButton) findViewById(ID_FAB_ADD_DOWNLOAD);
        fabAddFromUrl = (FloatingActionButton) findViewById(ID_FAB_ADD_FROM_URL);

        //setting listeners to FABs
        fabAddPhoto.setOnClickListener(clickListener);
        fabAddVideo.setOnClickListener(clickListener);
        fabDownload.setOnClickListener(clickListener);
        fabAddFromUrl.setOnClickListener(clickListener);

        //initializing ViewPager
        viewPager = (ViewPager) findViewById(R.id.galleryHolder);

        //creating adapter for ViewPager
        GalleryItemHolderFragmentAdapter adapter =
                new GalleryItemHolderFragmentAdapter(getSupportFragmentManager());

        //setting adapter to viewPager
        pagerAdapter = adapter;
        viewPager.setAdapter(pagerAdapter);

        //setting GalleryAdapter to database instance
        dataKeeper.setGalleryAdapter(adapter);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                //todo: set id of item to menu items
                //todo: on create activity - set item position 0
//                Log.d(LOG_TAG, "onPageSelected, position = " + position);
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

    //getting onClickListener
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //add photo button pressed
                case ID_FAB_ADD_PHOTO:
                    Toast.makeText(getBaseContext(), fabAddPhoto.getLabelText() + " pressed", Toast.LENGTH_SHORT).show();
                    break;

                //add video button pressed
                case ID_FAB_ADD_VIDEO:
                    Toast.makeText(getBaseContext(), fabAddVideo.getLabelText() + " pressed", Toast.LENGTH_SHORT).show();
                    break;

                //add by downloading button pressed
                case ID_FAB_ADD_DOWNLOAD:
                    Toast.makeText(getBaseContext(), fabDownload.getLabelText() + " pressed", Toast.LENGTH_SHORT).show();
                    break;

                //add from url button pressed
                case ID_FAB_ADD_FROM_URL:
                    Toast.makeText(getBaseContext(), fabAddFromUrl.getLabelText() + " pressed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    /**
     * processing data from camera
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PhotoButtonHandler.handleResult(requestCode, resultCode, data);
        }
    }
}