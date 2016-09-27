package com.dmytrobohdanov.galleryonmap;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

public class MenuButtonsHandler {
    private int positionOfItem;
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
        //todo
    }

    /**
     * Share Item  on position positionOfItem
     */
    public void shareButtonPressed(){

    }

    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra("photo",
                new File(GalleryItemsDataKeeper.getInstance().getItemByPosition(positionOfItem).getFilePath()));
        return shareIntent;
    }
    /**
     * Opens new activity with info of Item
     */
    public void infoButtonPressed(){

    }

    /**
     * Deletes Item
     */
    public void deleteButtonPressed(){

    }
}
