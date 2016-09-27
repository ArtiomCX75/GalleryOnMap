package com.dmytrobohdanov.galleryonmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
        Log.d("addingPhotoPr", "updateItemsAmount() start");
        GALLERY_ITEMS_AMOUNT = GalleryItemsDataKeeper.getInstance().getItemAmount();
        Log.d("addingPhotoPr", "updateItemsAmount() amount updated");
        notifyDataSetChanged();
        Log.d("addingPhotoPr", "updateItemsAmount() had notified");
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

        //get preview from URI
        Bitmap bitmap = ThumbnailUtils.extractThumbnail(
//                BitmapFactory.decodeFile(item.getFilePath()), imageView.getWidth(), imageView.getHeight());
                BitmapFactory.decodeFile(item.getFilePath()), 1024, 1280); //todo: rewrite

        //set image to ImageView
        imageView.setImageBitmap(bitmap);

//        //set parameters to view
//        imageNote.setPadding(0, 10, 0, 1);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        layoutParams.setMargins(0, 10, 0, 10);

//        imageNote.setLayoutParams(layoutParams);

        //setting image to image view
//        imageView.setImageURI(Uri.parse(item.getFilePath()));
    }
}