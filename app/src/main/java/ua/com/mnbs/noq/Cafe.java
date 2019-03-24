package ua.com.mnbs.noq;

public class Cafe {
    private String mCafeName;
    private String mCafeLocation;
    private String mCafeEmail;
    private int mDrawableId;
    private static final int NO_IMAGE_PROVIDED = -1;

    Cafe(String cafeName, String cafeLocation, int drawableId, String cafeEmail) {
        mCafeName = cafeName;
        mCafeLocation = cafeLocation;
        mDrawableId = drawableId;
        mCafeEmail = cafeEmail;
    }

    public String getCafeName() {
        return mCafeName;
    }

    public String getCafeLocation() {
        return mCafeLocation;
    }

    public String getCafeEmail() {
        return mCafeEmail;
    }

    public int getDrawableId() {
        return mDrawableId;
    }

    public boolean hasImage(){
        return mDrawableId != NO_IMAGE_PROVIDED;
    }

}
