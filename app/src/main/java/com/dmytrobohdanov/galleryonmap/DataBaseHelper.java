package com.dmytrobohdanov.galleryonmap;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dmytrobohdanov.galleryonmap.Items.Item;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    //flags to define photo or video item during adding new one
    public static final int FLAG_VIDEO = 1;
    public static final int FLAG_PHOTO = 0;

    // name of DB
    private static final String DATABASE_NAME = "galleryDataBase.db";

    // version of DB
    private static final int DATABASE_VERSION = 1;

    // name of table
    public static final String DATABASE_TABLE_GALLERY = "gallery";

    //  columns
    // id
    public static final String ITEM_ID_COLUMN = "itemId";
    //file path
    public static final String ITEM_FILE_PATH_COLUMN = "filePath";

    // is this item Video
    public static final String ITEM_IS_VIDEO_COLUMN = "isVideo";

    // properties
    public static final String ITEM_PROPERTIES_COLUMN = "properties";

    // location
    public static final String ITEM_LOCATION_COLUMN = "location";

    //scripts
    public static final String DATABASE_CREATE_SCRIPT = "create table "
            + DATABASE_TABLE_GALLERY
            + " ("
            + ITEM_ID_COLUMN         + " integer primary key autoincrement,"
            + ITEM_FILE_PATH_COLUMN  + " text,"
            + ITEM_IS_VIDEO_COLUMN   + " text,"
            + ITEM_PROPERTIES_COLUMN + " text,"
            + ITEM_LOCATION_COLUMN   + " text"
            + ");";

    //instance for singleton
    private static DataBaseHelper instance;

    /**
     * Constructor
     *
     * @param context
     */
    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DataBaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseHelper(context);
        }
        return instance;
    }

    /**
     * not for first initialization!
     * just for get instance
     * //todo: refactor this
     *
     * @return
     */
    public static DataBaseHelper getInstance() {
        return instance;
    }


    /**
     * Creation of DB
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE_SCRIPT);
    }

    /**
     * Updating DB
     *
     * @param sqLiteDatabase database
     * @param oldVersion     of DB
     * @param newVersion     of DB
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //delete existing table
        sqLiteDatabase.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE_GALLERY);

        // create new one
        onCreate(sqLiteDatabase);
    }


    /**
     * Adding new item to database
     *
     * @param filePath path to file
     * @param itemType could be FLAG_VIDEO or FLAG_PHOTO
     * @return id of item added to  database or
     * -1 in case of adding to DB error or
     * -2 in case of wrong flag passed
     */
    public long addNewItem(String filePath, int itemType) {
        //checking is flag value is valid
        if (itemType != FLAG_PHOTO && itemType != FLAG_VIDEO) {
            //wrong flag value - return -2
            return -2;
        }
        ContentValues values = new ContentValues();
        values.put(ITEM_FILE_PATH_COLUMN, filePath);
        values.put(ITEM_IS_VIDEO_COLUMN, String.valueOf(itemType));

        return getWritableDatabase().insert(DATABASE_TABLE_GALLERY, null, values);
    }

    /**
     * Adding location to DB row with specified id
     *
     * @param itemId   id of item to add location
     * @param location location to add to Item
     */
    public void addLocationToItem(int itemId, String location) {
        ContentValues values = new ContentValues();
        values.put(ITEM_LOCATION_COLUMN, location);

        //updating db entry with new location
        getWritableDatabase().update(DATABASE_TABLE_GALLERY,
                values,
                ITEM_ID_COLUMN + "= ?",
                new String[]{String.valueOf(itemId)});
    }

    /**
     * Adding property to DB's row with specified id
     *
     * @param itemId     id of item to add properties
     * @param properties properties to add to Item
     */
    public void addPropertiesToItem(int itemId, String properties) {
        ContentValues values = new ContentValues();
        values.put(ITEM_PROPERTIES_COLUMN, properties);

        //updating db entry with new location
        getWritableDatabase().update(DATABASE_TABLE_GALLERY,
                values,
                ITEM_ID_COLUMN + "= ?",
                new String[]{String.valueOf(itemId)});
    }

    /**
     * Deleting Item with specified ID from DB
     *
     * @param itemsId have to be deleted
     */
    public void deleteItem(int itemsId) {
        getWritableDatabase().delete(DATABASE_TABLE_GALLERY,
                ITEM_ID_COLUMN + "= ?",
                new String[]{String.valueOf(itemsId)});
    }

    /**
     * Get instance of Item from Database by item's id
     *
     * @param searchingId id of item we are looking for
     * @return instance of Item
     */
    public Item getItemById(int searchingId) {
        String query = "select * from "
                + DATABASE_TABLE_GALLERY
                + " where "
                + ITEM_ID_COLUMN
                + " = "
                + searchingId;

        Cursor cursor = getWritableDatabase().rawQuery(query, null);
        cursor.moveToFirst();

        long itemId = cursor.getInt(cursor.getColumnIndex(ITEM_ID_COLUMN));
        String filePath = cursor.getString(cursor.getColumnIndex(ITEM_FILE_PATH_COLUMN));
        String isVideo = cursor.getString(cursor.getColumnIndex(ITEM_IS_VIDEO_COLUMN));
        String location = cursor.getString(cursor.getColumnIndex(ITEM_LOCATION_COLUMN));
        String properties = cursor.getString(cursor.getColumnIndex(ITEM_PROPERTIES_COLUMN));

        cursor.close();
        return new Item(itemId, filePath, isVideo, location, properties);
    }

    /**
     * Getting number of items in database
     *
     * @return number of items in database
     */
    public long getItemsNumber() {
        //todo
        return 0;
    }

    /**
     * Getting array of all Item's IDs in database
     *
     * @return array of ID of Items in database
     */
    public ArrayList<Integer> getArrayOfItemsIds() {
        ArrayList<Integer> itemsIds = new ArrayList<>();
        //todo write it
        return itemsIds;
    }
}
