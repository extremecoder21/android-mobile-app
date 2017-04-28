package com.affinistechnologies.jamrocksingles.jamrocksingles.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import java.io.IOException;

/**
 * Created by clayt on 3/11/2017.
 */

public class PictureUtils {

    @SuppressLint("NewApi")
    public static Bitmap getScaledBitMap(String path, Activity activity){
        Point size = null;

        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){

            size = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(size);
        }
        else{
            size = new Point(display.getWidth(),display.getHeight());
        }

        return getScaledBitMap(path, size.x,size.y);
    }
    public static Bitmap getScaledBitMap(String path,int destWidth,int destHeight){
        //Read In deimensions of disk on file
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds =true;

        BitmapFactory.decodeFile(path,options);

        //Figure out how much we want to scale by.
        int inSample = 1;

        int srcHeight = options.outHeight;
        int srcWidth = options.outWidth;
        if(srcHeight>destHeight || srcWidth > destWidth){
            float heightScale = srcHeight/destHeight;
            float widthScale = srcWidth/destWidth;

            inSample = Math.round( heightScale > widthScale ? heightScale:widthScale);
        }

        options = new BitmapFactory.Options();

        options.inSampleSize = inSample;
        //Read in and Create final BitMap

        return BitmapFactory.decodeFile(path,options);
    }
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // Raw height and width of image
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth)
        {

            // Calculate ratios of height and width to requested height and width
            int heightRatio = java.lang.Math.round((float)height / (float)reqHeight);
            int widthRatio = java.lang.Math.round((float)width / (float)reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down further
            float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap)
            {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }
    public static Bitmap rotateImageIfRequired(Bitmap img,Uri selectedImageUri) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds =true;

        BitmapFactory.decodeFile(selectedImageUri.getPath(),options);

        ExifInterface ei =  new ExifInterface(selectedImageUri.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);

        switch (orientation)
        {
            case 6:
                return rotateImage(img, 90);
            case 3:
                return rotateImage(img, 180);
            case 8:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }
    public static Bitmap rotateImage(Bitmap img, int degree){
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }
}
