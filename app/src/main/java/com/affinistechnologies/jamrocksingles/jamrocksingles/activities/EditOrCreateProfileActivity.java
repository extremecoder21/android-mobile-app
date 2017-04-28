package com.affinistechnologies.jamrocksingles.jamrocksingles.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.affinistechnologies.jamrocksingles.R;
import com.affinistechnologies.jamrocksingles.jamrocksingles.utility.PictureUtils;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class EditOrCreateProfileActivity extends BaseActivity implements  ImageButton.OnClickListener{

    private File _photoFile;
    private File _croppedFile;
    private Intent _captureImage;
    private de.hdodenhof.circleimageview.CircleImageView _photoView;
    private static final int REQUEST_PHOTO =2;

    private ImageButton _editProfilePhoto;
    private EditText _txtFirstName;
    private EditText _txtLastName;
    private EditText _txtUserName;
    private EditText _txtEmail;
    private EditText _txtPassword;
    private final String FILE_PROVIDER_ID ="com.affinistechnologies.jamrocksingles.fileprovider";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_or_create_profile);
        _txtFirstName =(EditText) findViewById(R.id.txtFirstNameEditView);
        _txtLastName = (EditText) findViewById(R.id.txtLastNameEditView);
        _txtUserName = (EditText) findViewById(R.id.txtUserNameEditView);
        _txtEmail = (EditText) findViewById(R.id.txtEmailAddressEditView);
        _txtPassword = (EditText) findViewById(R.id.txtPasswordEditView);
        _editProfilePhoto = (ImageButton)findViewById(R.id.btnTakePicture);
        _photoFile = getPhotoFile();
        _croppedFile = getPhotoFile();
        _captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = _photoFile!=null && _captureImage.resolveActivity(getPackageManager())!=null;
        _editProfilePhoto.setEnabled(canTakePhoto);
        _editProfilePhoto = (ImageButton)findViewById(R.id.btnTakePicture);
        _editProfilePhoto.setOnClickListener(this);
        _photoView = (de.hdodenhof.circleimageview.CircleImageView)findViewById(R.id.profile_image);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnTakePicture) {
            LaunchCamera();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //getApplicationContext().revokeUriPermission(getImageUri(), Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_PHOTO) {
            Uri outPutUri = getImageUri();
            BitmapFactory.Options factoryOptions = new BitmapFactory.Options();
            factoryOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(outPutUri.getPath(), factoryOptions);
            factoryOptions.inJustDecodeBounds = false;
            factoryOptions.inSampleSize = PictureUtils.calculateInSampleSize(factoryOptions, 1024, 1024);
            Bitmap bitmap = BitmapFactory.decodeFile(outPutUri.getPath(), factoryOptions);
            Bitmap img = null;
            try {
                img = PictureUtils.rotateImageIfRequired(bitmap, outPutUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            _photoView.setImageBitmap(img);
            Crop.of(getImageUri(), getCroppedImageUri()).asSquare().start(EditOrCreateProfileActivity.this, Crop.REQUEST_CROP);
        }
        if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }

    public Uri getImageUri(){
        return FileProvider.getUriForFile(EditOrCreateProfileActivity.this,FILE_PROVIDER_ID,_photoFile);
    }

    public Uri getCroppedImageUri(){
        return FileProvider.getUriForFile(EditOrCreateProfileActivity.this,FILE_PROVIDER_ID,_croppedFile);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Uri  uri = Crop.getOutput(result);
            Uri cUri = getCroppedImageUri();
            _photoView.setImageURI(uri);
  
            BitmapFactory.Options factoryOptions = new BitmapFactory.Options();
            factoryOptions.inJustDecodeBounds = true;
            Bitmap bitmap =  BitmapFactory.decodeFile(cUri.getPath(), factoryOptions);
            factoryOptions.inJustDecodeBounds = false;
            factoryOptions.inSampleSize = PictureUtils.calculateInSampleSize(factoryOptions, 1024, 1024);

            Bitmap img = null;
            try {
                img = PictureUtils.rotateImageIfRequired(bitmap, cUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(EditOrCreateProfileActivity.this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private String getPhotoFileName(){
        return "IMG_" + UUID.randomUUID().toString() + ".jpg";
    }

    private File getPhotoFile(){
        File fileDirectory = new File(this.getFilesDir(), "images");
        return new File(fileDirectory, getPhotoFileName());
    }

    private void LaunchCamera(){
        Uri uri = FileProvider.getUriForFile(EditOrCreateProfileActivity.this,FILE_PROVIDER_ID,_photoFile);
        _captureImage.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        List<ResolveInfo> cameraActivities = getPackageManager().queryIntentActivities(_captureImage, PackageManager.MATCH_DEFAULT_ONLY);
        for(ResolveInfo activity: cameraActivities){
            grantUriPermission(activity.activityInfo.packageName,uri,Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivityForResult(_captureImage,REQUEST_PHOTO);
    }
}
