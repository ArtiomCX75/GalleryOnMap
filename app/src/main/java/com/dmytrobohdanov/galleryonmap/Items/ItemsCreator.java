package com.dmytrobohdanov.galleryonmap.Items;


import android.util.Log;
import android.widget.Toast;

import com.dmytrobohdanov.galleryonmap.DataBaseHelper;
import com.dmytrobohdanov.galleryonmap.MainActivity;

/**
 * Creator of Items
 * using to create new Item
 */
public class ItemsCreator {
    static DataBaseHelper dataBase;

    /**
     * Costructor
     *
     * @param dataBase data base with Items
     */
    public ItemsCreator(DataBaseHelper dataBase) {
        ItemsCreator.dataBase = dataBase;
    }

    /**
     * Create new Items
     *
     * @param filePath path of file of item
     * @param isVideo  flag to define photo or video item
     * @return instance of Item
     */
    public static Item createNewItem(String filePath, boolean isVideo) {
        int isVideoIntVal = (isVideo) ? 1 : 0;
        Log.d("nullpointerHunting", "ping 1");
        //getting id of Item from DB
        long itemId = dataBase.addNewItem(filePath, isVideoIntVal);
        Log.d("nullpointerHunting", String.valueOf(itemId));
        return new Item(itemId, filePath, isVideo);
    }
}
