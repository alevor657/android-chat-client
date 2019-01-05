package com.example.yooo.ultimatechat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import okhttp3.Response;

public class SettingsActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment(), "settingsTag")
                .commit();
        mImageView = new ImageView(this);
    }

    public void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            AvatarController.UploadAvatar upl = new AvatarController.UploadAvatar();

            Response res = null;

            try {
                res = upl.execute(imageBitmap).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (res != null) {
                if (!res.isSuccessful()) {
                    Toast.makeText(this, "Could not upload your image", Toast.LENGTH_LONG);
                } else {
                    Toast.makeText(this, "Image uploaded", Toast.LENGTH_SHORT);
                }
            }

            mImageView.setImageBitmap(imageBitmap);
        }
    }
}
