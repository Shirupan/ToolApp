package com.slaoren;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.slaoren.imgedit.PicMainActivity;
import com.slaoren.numgame.RandomActivity;
import com.slaoren.paopai.activity.PaopaiActivity;
import com.slaoren.wallpaper.WallpaperMainActivity;
import com.slaoren.wallpaper.gif.AnimatedGifEncoder;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.to1).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, PicMainActivity.class));
        });
        findViewById(R.id.to2).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, WallpaperMainActivity.class));
        });
        findViewById(R.id.to3).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, RandomActivity.class));
        });
        findViewById(R.id.to4).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, PaopaiActivity.class));
        });

    }


//            txt.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
////                startActivity(new Intent(MainActivity.this, TestActivity.class));
//
////                ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
////                bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.crop_normal));
////                bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.back_arrow));
////                saveGif(bitmaps);
//
//            try {
//                clearWallpaper();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//    });
    public byte[] generateGIF(ArrayList<Bitmap> bitmaps) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.start(bos);
        for (Bitmap bitmap : bitmaps) {
            encoder.addFrame(bitmap);
        }
        encoder.finish();
        return bos.toByteArray();
    }

    public void saveGif(ArrayList<Bitmap> bitmaps) {
        try {
            FileOutputStream outStream = new FileOutputStream(getExternalFilesDir(null).getPath()+"/test.gif");
            outStream.write(generateGIF(bitmaps));
            outStream.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap captureView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(
                view.getWidth(),
                view.getHeight(),
                Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}
