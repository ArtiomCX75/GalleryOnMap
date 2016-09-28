package com.dmytrobohdanov.galleryonmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dmytrobohdanov.galleryonmap.MapActivity.MapActivity;
import com.dmytrobohdanov.galleryonmap.Items.ItemsCreator;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class MainActivity extends AppCompatActivity {
    //request flags for startActivityForResult
    public static final int REQUEST_TAKE_PHOTO = 1;
    public static final int REQUEST_CODE_LOCATION = 2;

    //float action button/menu IDs
    private static final int ID_ADD_ITEM_FAB_MENU = R.id.add_item_fab_menu;
    private static final int ID_FAB_ADD_PHOTO = R.id.fab_add_photo;
    private static final int ID_FAB_ADD_VIDEO = R.id.fab_add_video;
    private static final int ID_FAB_ADD_DOWNLOAD = R.id.fab_add_download;
    private static final int ID_FAB_ADD_FROM_URL = R.id.fab_add_by_url;

    //view pager and adapter
    ViewPager viewPager;
    PagerAdapter pagerAdapter;

    //data keeper
    GalleryItemsDataKeeper dataKeeper;

    //data base
    DataBaseHelper dataBase;

    //creator of Items
    ItemsCreator itemsCreator;

    //menu
    MenuButtonsHandler menuButtonsHandler;

    //current position of Item displayed
    static Integer itemPosition;

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
        itemsCreator = new ItemsCreator(dataBase);

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

        //init position of items
        if(itemPosition == null && GalleryItemsDataKeeper.getInstance().getItemAmount() > 0){
            itemPosition = 0;
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                updateCurrentPosition(position);
                menuButtonsHandler.setPositionOfItemWorkingWith(position);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_manu, menu);
        this.menuButtonsHandler = new MenuButtonsHandler(menu);
        //set position of first element to menu buttons handler, in case if there are elements
        if (dataKeeper.getItemAmount() != 0) {
            menuButtonsHandler.setPositionOfItemWorkingWith(0);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_set_location:
                this.menuButtonsHandler.setLocationPressed(this);
                break;

            case R.id.menu_share:
                Toast.makeText(getBaseContext(), "Share button pressed", Toast.LENGTH_SHORT).show();
                this.menuButtonsHandler.shareButtonPressed();
                break;

            case R.id.menu_info:
                this.menuButtonsHandler.infoButtonPressed(this);
                break;

            case R.id.menu_full_map:
                this.menuButtonsHandler.fullMapButtonPressed(this);
                break;

            case R.id.menu_delete:
                this.menuButtonsHandler.deleteButtonPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //getting onClickListener
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                //add photo button pressed
                case ID_FAB_ADD_PHOTO:
                    AddPhotoHandler.dispatchTakePictureIntent(MainActivity.this);
                    fabAddItemMenu.close(false);
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
     *
     *
     * @param position
     */
    public void updateCurrentPosition(int position){
        this.itemPosition = position;
    }

    /**
     * processing data received from other activities
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //request for location setting
        if (requestCode == REQUEST_CODE_LOCATION && resultCode == RESULT_OK) {
            String str = data.getStringExtra(MapActivity.KEY_LOCATION);
            GalleryItemsDataKeeper.getInstance().setLocationToItemByPosition(itemPosition, str);
//            Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();
        }

        //request for photo adding
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            AddPhotoHandler.handleResult(requestCode, resultCode, data);
        }
    }


    public static int getDesplaingItemPosition(){
        return itemPosition;
    }
}