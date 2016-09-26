package com.dmytrobohdanov.galleryonmap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.TextView;

import com.dmytrobohdanov.galleryonmap.temp.GalleryItemsDataKeeper;

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
    public void setItemsAmount(int itemsAmount){
        GALLERY_ITEMS_AMOUNT = itemsAmount;
        notifyDataSetChanged();
    }

    /**
     * Updating amount of items to display
     * getting info from GalleryItemsDataKeeper
     */
    public void updateItemsAmount(){
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

    public static void displayDataInFragment(GalleryItemHolderFragment itemHolder, int pageNumber){
        //temp:
        TextView tvPage = (TextView) itemHolder.getView().findViewById(R.id.tvPage);
        GalleryItemsDataKeeper dataKeeper = GalleryItemsDataKeeper.getInstance();

        //getting item
        tvPage.setText("Page " + dataKeeper.getItemById(pageNumber));
    }

    public static void displayDataInView(TextView tvPage, int pageNumber){
        //temp:
        GalleryItemsDataKeeper dataKeeper = GalleryItemsDataKeeper.getInstance();

        //getting item
        tvPage.setText("Page " + dataKeeper.getItemById(pageNumber));
    }
}