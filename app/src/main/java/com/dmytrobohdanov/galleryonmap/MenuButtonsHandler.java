package com.dmytrobohdanov.galleryonmap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.Menu;

import java.io.File;

public class MenuButtonsHandler {
    private Integer positionOfItem;
    private Menu menu;

    /**
     * Constructor
     *
     * @param menu to handle
     */
    public MenuButtonsHandler(Menu menu){
        this.menu = menu;
    }

    /**
     * Setting position of item working with
     * should use after displayed another element in View
     */
    public void setPositionOfItemWorkingWith(int positionOfItem){
        this.positionOfItem = positionOfItem;
    }

    /**
     * Opens new Activity with map
     * to set location of Item on position positionOfItem
     */
    public void setLocationPressed(){
        if(positionOfItem == null){
            return;
        }
        //todo
    }

    /**
     * Share Item  on position positionOfItem
     */
    public void shareButtonPressed(){
        if(positionOfItem == null){
            return;
        }

    }

    /**
     * Opens new activity with info of Item
     */
    public void infoButtonPressed(Activity activity){
        if(positionOfItem == null){
            return;
        }

        //temporary realization :)
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(
                new File(GalleryItemsDataKeeper.getInstance().getItemByPosition(positionOfItem).getFilePath())), "image/*");
        activity.startActivity(intent);
    }

    /**
     * Deletes Item
     */
    public void deleteButtonPressed(){
        if(positionOfItem == null){
            return;
        }
        GalleryItemsDataKeeper.getInstance().deleteItemOnPosition(positionOfItem);
    }


}
