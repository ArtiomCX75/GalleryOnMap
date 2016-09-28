package com.dmytrobohdanov.galleryonmap;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddPhotoHandler extends Activity {
//    static final int REQUEST_TAKE_PHOTO = 1;

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
                activity.startActivityForResult(takePictureIntent, MainActivity.REQUEST_TAKE_PHOTO);
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
    }

    public static void handleDownloadFile(Uri data, ContentResolver contentResolver, Activity activity) {
        //getting bitmap
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //creating file
        File file = null;
        try {
            file = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //making file from bitmap
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert bitmap != null;
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        try {
            assert os != null;
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GalleryItemsDataKeeper.getInstance().addNewItem(file.getPath(), false);
    }

    public static void downloadPhoto(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, MainActivity.REQUEST_DOWNLOAD_FILE);
    }
}



