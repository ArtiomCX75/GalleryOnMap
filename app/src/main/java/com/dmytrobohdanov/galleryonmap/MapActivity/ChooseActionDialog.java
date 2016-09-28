package com.dmytrobohdanov.galleryonmap.MapActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.dmytrobohdanov.galleryonmap.GalleryItemsDataKeeper;
import com.dmytrobohdanov.galleryonmap.Items.Item;
import com.dmytrobohdanov.galleryonmap.R;
import com.google.android.gms.maps.model.Marker;

import java.io.File;

/**
 * Dialog fragment on map activity with all items locations
 * to choose action to do with marker
 */
public class ChooseActionDialog extends DialogFragment {

    private Item item;
    private Marker marker;

    /**
     * Setting Item working with
     * have to be called
     *
     * @param item working with
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * Marker working with
     *
     * @param marker working with
     */
    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setItems(R.array.marker_actions, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //0 - is for showing image
                if (which == 0) {
                    //show image
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(
                            new File(item.getFilePath())), "image/*");
                    startActivity(intent);
                } else if (which == 1) {
                    //1 - is for deleting
                    //hide marker
                    marker.setVisible(false);

                    //delete location from this item
                    GalleryItemsDataKeeper.getInstance().updateLocation(item.getItemId(), null);
                }

                dialog.dismiss();
            }
        });

        return builder.create();
    }


}