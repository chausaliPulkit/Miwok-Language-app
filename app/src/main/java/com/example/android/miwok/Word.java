package com.example.android.miwok;

/*
This class represents a vocabulary word that the user wants to learn.
It contains a default translation and a Miwok translation for that word.
 */
public class Word {
    //    default translation of word
    private final String mDefaultTranslation;

    //    Miwok translation of word
    private final String mMiwokTranslation;

    //    contains image resource id
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED = -1;

    private final int mAudioResourcesId;


    public Word(String mDefaultTranslation, String mMiwokTranslation, int mImageResource, int mAudioResourcesId) {
        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mImageResourceId = mImageResource;
        this.mAudioResourcesId = mAudioResourcesId;

    }

    public Word(String mDefaultTranslation, String mMiwokTranslation, int mAudioResourcesId) {
        this.mDefaultTranslation = mDefaultTranslation;
        this.mMiwokTranslation = mMiwokTranslation;
        this.mAudioResourcesId = mAudioResourcesId;
    }

    //    get default translation of word
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    //    get Miwok translation of word
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    //    get image Resource id
    public int getImageResourceId() {
        return mImageResourceId;
    }

    //    returns if there is valid image resource or not
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    //    get Audio resources Id
    public int getAudioResourcesId() {
        return mAudioResourcesId;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", mAudioResourcesId=" + mAudioResourcesId +
                '}';
    }
}
