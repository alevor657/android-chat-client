package com.example.yooo.ultimatechat;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AvatarController {
    public static final String AVATAR_UPLOAD_URL = "http://192.168.1.42:1338/avatars/upload";
    public static final String AVATARS_FOLDER_URL = "http://192.168.1.42:1338/uploads";

    public AvatarController() {

    }

    public static class UploadAvatar extends AsyncTask<Bitmap, Void, Response> {
        @Override
        protected Response doInBackground(Bitmap... bitmaps) {
            Bitmap image = bitmaps[0];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] bitmapData = bos.toByteArray();

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .addHeader("Authorization", UserCredentials.getInstance().getToken())
                    .addHeader("Username", UserCredentials.getInstance().getUsername())
                    .url(AvatarController.AVATAR_UPLOAD_URL)
                    .post(RequestBody.create(MediaType.parse("image/jpeg; charset=utf-8"), bitmapData))
                    .build();

            Response res = null;

            try {
                res = client
                        .newCall(request)
                        .execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return res;
        }
    }
}
