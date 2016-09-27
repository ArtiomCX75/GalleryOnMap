package com.dmytrobohdanov.galleryonmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.widget.ImageView;

import com.dmytrobohdanov.galleryonmap.Items.Item;

class GalleryItemHolderFragmentAdapter extends FragmentStatePagerAdapter {
    //amount of items in gallery
    public static int GALLERY_ITEMS_AMOUNT;

    public GalleryItemHolderFragmentAdapter(FragmentManager fm) {
        super(fm);
        updateItemsAmount();
    }

    /**
     * Setting amount of items to display
     *
     * @param itemsAmount amount of items to display
     */
    public void setItemsAmount(int itemsAmount) {
        GALLERY_ITEMS_AMOUNT = itemsAmount;
        notifyDataSetChanged();
    }

    /**
     * Updating amount of items to display
     * getting info from GalleryItemsDataKeeper
     */
    public void updateItemsAmount() {
        GALLERY_ITEMS_AMOUNT = GalleryItemsDataKeeper.getInstance().getItemAmount();
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return GalleryItemHolderFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return GALLERY_ITEMS_AMOUNT;
    }

    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }

    public static void displayDataInView(ImageView imageView, int pageNumber) {
        GalleryItemsDataKeeper dataKeeper = GalleryItemsDataKeeper.getInstance();

        //getting image from base
        Item item = dataKeeper.getItemByPosition(pageNumber);

        //get preview from URI
        Bitmap bitmap = ThumbnailUtils.extractThumbnail(
//                BitmapFactory.decodeFile(item.getFilePath()), imageView.getWidth(), imageView.getHeight());
                BitmapFactory.decodeFile(item.getFilePath()), 1024, 1280); //todo: rewrite

        //set image to ImageView
        imageView.setImageBitmap(bitmap);
    }
}