package com.dmytrobohdanov.galleryonmap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.dmytrobohdanov.galleryonmap.Items.Item;
import com.dmytrobohdanov.galleryonmap.Items.ItemsCreator;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddPhotoHandler extends Activity {
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    //todo: refactor this to avoid photoFile variable
    private static File photoFile;

    /**
     * Creating file for image
     *
     * @return file for image
     * @throws IOException
     */
    private static File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }


    /**
     * starts camera activity
     * prepares file ready to save image
     */
    public static void dispatchTakePictureIntent(Activity activity) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                //do something if needed
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
//                activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        }
    }

    /**
     * onActivityResult() from MainActivity uses this method
     * when camera made image
     *
     * @param requestCode
     * @param resultCode
     * @param data        - intent keeping photo file
     */
    public static void handleResult(int requestCode, int resultCode, Intent data) {
        //temporary - photoFile is placed as var in this class, not passing
        //so photoFile is defined above
        GalleryItemsDataKeeper.getInstance().addNewItem(photoFile.getPath(), false);
//        Item item = ItemsCreator.createNewItem(photoFile.getPath(), false);
        GalleryItemsDataKeeper.getInstance().notifyChanges();
    }
}



