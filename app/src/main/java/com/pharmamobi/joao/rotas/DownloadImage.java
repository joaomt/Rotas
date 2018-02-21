package com.pharmamobi.joao.rotas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by joao on 27/03/2017.
 */

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView fbImagem;

    public DownloadImage (ImageView fbImagem){
        this.fbImagem = fbImagem;
    }
    protected Bitmap doInBackground(String... urls){
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try{
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        }catch (Exception e){
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }


    protected void onPostExecute(Bitmap result) {
        fbImagem.setImageBitmap(result);
    }
}
