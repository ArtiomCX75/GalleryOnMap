package com.dmytrobohdanov.galleryonmap.temp;


import java.util.ArrayList;

/**
 *  Temporary class
 *
 *  Keeps data (photos, video, but for now - string to display) to show in gallery items
 *  until will be replaced with DataBase classes
 */
public class GalleryItemsDataKeeper {
    private static GalleryItemsDataKeeper instance;
    private ArrayList<String> data;

    private GalleryItemsDataKeeper() {
        data = new ArrayList<>();
        for(int i = 0; i <= 10; i++){
            data.add("This is item #" + i);
        }
    }

    public static GalleryItemsDataKeeper getInstance() {
        if(instance == null){
            instance = new GalleryItemsDataKeeper();
        }

        return instance;
    }

    /**
     * @return data set
     */
    public ArrayList<String> getData() {
        return data;
    }

    /**
     * Returning item of data by specified id
     *
     * @param id of item
     * @return String value of data item
     */
    public String getItemById(int id){
        return data.get(id);
    }

    /**
     * @return amount of items we have
     */
    public int getItemAmount(){
        return data.size();
    }
}
