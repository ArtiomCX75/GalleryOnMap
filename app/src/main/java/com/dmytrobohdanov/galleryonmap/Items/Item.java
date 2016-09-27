package com.dmytrobohdanov.galleryonmap.Items;

import com.dmytrobohdanov.galleryonmap.DataBaseHelper;

/**
 * Class that keeps data of item to display in gallery
 * it could be info about photo or video file
 */
public class Item {
    //id of item
    private long itemId;

    //path to file
    private String filePath;

    //flag: true  - video file, false - photo
    private boolean isVideo;

    //properties of image/video
    private String properties;

    //geolocation coordinate (while there is no map  - will be string)
    private String location;

    /*
     * Constructors
     */
    /**
     * Creating new item
     * for ItemsCreator
     *
     * @param itemId
     * @param filePath
     * @param isVideo
     */
    protected Item(long itemId, String filePath, boolean isVideo) {
        this.itemId = itemId;
        this.filePath = filePath;
        this.isVideo = isVideo;
    }

    /**
     * Constructor
     * to create Item instance from database data
     */
    public Item(long itemId, String filePath, String isVideo, String location, String properties) {
        this.itemId = itemId;
        this.filePath = filePath;
        this.isVideo = isVideo.toLowerCase().equals("true");
        this.location = location;
        this.properties = properties;
    }

    /**
     * requires empty constructor
     */
    public Item() {
    }

    /*
     * Getters
     */

    /**
     * Get id of Item in data base
     *
     * @return id of Item in DataBase
     */
    public long getItemId() {
        return itemId;
    }

    /**
     * Getting path to file
     * can be local pass,
     * but in future could be url pass
     *
     * @return file path
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Define is it photo-item or video one
     *
     * @return true - if video-item, false - in case of photo item
     */
    public boolean isVideo() {
        return isVideo;
    }

    /**
     * Getting properties of item
     *
     * @return properties of item, could be null
     */
    public String getProperties() {
        return properties;
    }

    /**
     * Getting location of item
     *
     * @return location, could be null in case of item is not linked with any location
     */
    public String getLocation() {
        return location;
    }

    /*
     * Setters
     */

    /**
     * Setting properties of item
     *
     * @param properties of file
     */
    public void setProperties(String properties) {
        this.properties = properties;
    }

    /**
     * Setting location
     */
    public void setLocation(String location) {
        this.location = location;
    }
}
