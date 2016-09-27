package com.dmytrobohdanov.galleryonmap;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
    public void infoButtonPressed(){
        if(positionOfItem == null){
            return;
        }

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
