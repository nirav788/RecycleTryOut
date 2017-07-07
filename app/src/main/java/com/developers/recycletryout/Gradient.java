package com.developers.recycletryout;

/**
 * Created by Developers on 15-06-2017.
 */
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.hariofspades.gradientartist.GradientArtistBasic;
import com.liuguangqiang.ripplelayout.Ripple;

public class Gradient extends AppCompatActivity {

    GradientArtistBasic gradient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gradient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ripple.startActivity(Gradient.this, MainActivity.class,fab);
            }
        });

        gradient=(GradientArtistBasic) findViewById(R.id.gradientImage);
//        gradient.setUrlImage("https://img1.etsystatic.com/002/0/6449624/il_fullxfull.382134677_so6e.jpg",R.drawable.weather,
//                R.drawable.weather, ImageView.ScaleType.CENTER_CROP);
        gradient.setDrawableImage(R.drawable.mainlogo,R.drawable.weather,R.drawable.weather,
                ImageView.ScaleType.CENTER_CROP);
        //Drawable myGradient = ContextCompat.getDrawable(this,R.drawable.alpha_gradient);
        //gradient.setGradient(myGradient);
    }

}