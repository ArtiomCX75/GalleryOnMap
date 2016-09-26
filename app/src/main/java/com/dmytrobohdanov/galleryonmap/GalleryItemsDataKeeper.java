package com.dmytrobohdanov.galleryonmap;


import com.dmytrobohdanov.galleryonmap.Items.Item;

import java.util.ArrayList;

/**
 * Temporary class
 */
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

    //    /**
//     * @return data set
//     */
//    public ArrayList<Item> getData() {
//        return items;
//    }

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

    public void addNewItem(Item item) {
//        items.add(item);
        galleryAdapter.notifyDataSetChanged();
    }

    public void notifyChanges(){
        itemsIds = dataBase.getArrayOfItemsIds();
        galleryAdapter.updateItemsAmount();
//        galleryAdapter.notifyDataSetChanged();
    }
}
