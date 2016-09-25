package com.dmytrobohdanov.galleryonmap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dmytrobohdanov.galleryonmap.temp.GalleryItemsDataKeeper;


public class GalleryItemHolderFragment extends Fragment {
    static final String KEY_ITEM_NUMBER = "itemsNumber";

    int pageNumber;

    public GalleryItemHolderFragment() {
        // Required empty public constructor
    }


    public static GalleryItemHolderFragment newInstance(int pageNumber) {
        GalleryItemHolderFragment pageFragment = new GalleryItemHolderFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_ITEM_NUMBER, pageNumber);
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(KEY_ITEM_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //creating view
        View view = inflater.inflate(R.layout.fragment_gallery_item_holder, null);

        //temp:
        TextView tvPage = (TextView) view.findViewById(R.id.tvPage);
        GalleryItemsDataKeeper dataKeeper = GalleryItemsDataKeeper.getInstance();

        //getting item
        tvPage.setText("Page " + dataKeeper.getItemById(pageNumber));

        return view;
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_gallery_item_holder, container, false);
    }
}
