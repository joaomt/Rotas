package com.pharmamobi.joao.rotas.Domain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by joao on 25/10/2017.
 */

public class ConverterImage {

    public String bitMapToBase64(Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    public Bitmap base64TobitMap(String b64)
    {
        byte[]arrayImage = Base64.decode(b64,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(arrayImage,0,arrayImage.length);
    }
}
