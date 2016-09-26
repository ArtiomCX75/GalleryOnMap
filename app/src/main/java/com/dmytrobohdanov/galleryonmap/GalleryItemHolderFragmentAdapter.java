package com.dmytrobohdanov.galleryonmap;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
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

    public static void displayDataInView(ImageView imageView, int pageNumber) {
        GalleryItemsDataKeeper dataKeeper = GalleryItemsDataKeeper.getInstance();

        //getting image from base
        Item item = dataKeeper.getItemByPosition(pageNumber);

        //setting image to image view
        imageView.setImageURI(Uri.parse(item.getFilePath()));
    }
}