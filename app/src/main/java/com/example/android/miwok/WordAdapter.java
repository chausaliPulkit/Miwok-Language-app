package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
//    resource id for background color of this list of word
    private int mColorResource;
    public WordAdapter(Activity context, ArrayList<Word> androidFlavors, int mColorResource) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, androidFlavors);
        this.mColorResource = mColorResource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
//        get the (Word) object present at given position
        final Word currentWord = getItem(position);

//           Find the TextView in the list_item.xml layout with the ID miwok_text_view
        TextView miwokTranslation = (TextView) listItemView.findViewById(R.id.miwok_text_view);
//        get the miwok translation and set it in miwokTranslation TextView
        miwokTranslation.setText(currentWord.getMiwokTranslation());

//        Find the TextView in the list_item.xml layout with the ID  english_text_view
        TextView englishTranslation = (TextView) listItemView.findViewById(R.id.english_text_view);
//        get English translation and set it in englishTranslation TextView
        englishTranslation.setText(currentWord.getDefaultTranslation());

//        Find the ImageView in the list_item.xml layout with thd id image_view
        ImageView image = (ImageView) listItemView.findViewById(R.id.image_view);

//     this if/else check whether the Image resource is valid or not
        if (currentWord.hasImage()) {
            //        get required image and set it in imageView
            image.setImageResource(currentWord.getImageResourceId());
            image.setVisibility(View.VISIBLE);
        } else
//            hide the image when there is no valid image Resource
            image.setVisibility(View.GONE);


//        set theme color for list Item
        View textContainer = listItemView.findViewById(R.id.text_container);
//        Find the color that resource id map to
        int color = ContextCompat.getColor(getContext(), mColorResource);
//        set background color to text container View
        textContainer.setBackgroundColor(color);

//        return the whole list item layout so that it can be shown in ListView
        return listItemView;
    }
}
