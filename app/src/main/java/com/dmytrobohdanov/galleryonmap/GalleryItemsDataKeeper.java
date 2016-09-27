package com.dmytrobohdanov.galleryonmap;


import android.util.Log;

import com.dmytrobohdanov.galleryonmap.Items.Item;
import com.dmytrobohdanov.galleryonmap.Items.ItemsCreator;

import java.util.ArrayList;

public class GalleryItemsDataKeeper {
    private static GalleryItemsDataKeeper instance;

    //    private ArrayList<Item> items;
    private ArrayList<Integer> itemsIds;
    private DataBaseHelper dataBase;

    private GalleryItemHolderFragmentAdapter galleryAdapter;

    /**
     * Constructor
     */
    private GalleryItemsDataKeeper() {
        dataBase = DataBaseHelper.getInstance();
        //getting array of items id in data base

        itemsIds = dataBase.getArrayOfItemsIds();
    }

    /**
     * Using to init instance
     * //todo: rewrite description
     *
     * @return instance of
     */
    public static GalleryItemsDataKeeper getInstance() {
        if (instance == null) {
            instance = new GalleryItemsDataKeeper();
        }
        return instance;
    }

    /**
     * Sets instance of adapter
     * MUST be used
     */
    public void setGalleryAdapter(GalleryItemHolderFragmentAdapter galleryAdapter) {
        this.galleryAdapter = galleryAdapter;
    }


    /**
     * Returning item of data by specified position
     *
     * @param position of item
     * @return String value of data item
     */
    public Item getItemByPosition(int position) {
        return dataBase.getItemById(itemsIds.get(position));
    }

    /**
     * @return amount of items we have
     */
    public int getItemAmount() {
        return itemsIds.size();
    }


    public void notifyChanges(){
        itemsIds = dataBase.getArrayOfItemsIds();
        galleryAdapter.updateItemsAmount();
    }

    /**
     * Adding new Item
     *
     * @param filePath path to file
     * @param isVideo is it video flag
     */
    public void addNewItem(String filePath, boolean isVideo) {
        Item item = ItemsCreator.createNewItem(filePath, isVideo);
        notifyChanges();
    }

    /**
     * deletes item on specified position
     *
     * @param position of item
     */
    public void deleteItemOnPosition (int position){
        dataBase.deleteItem(itemsIds.get(position));
        notifyChanges();
    }


    /**
     * Sets locaton to Item on the specified position
     *
     * @param position in array of Items
     * @param location string location to set
     */
    public void setLocationToItemByPosition(int position, String location){
        dataBase.addLocationToItem(getItemByPosition(position).getItemId(), location);
    }
}
