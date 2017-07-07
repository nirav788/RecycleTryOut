package com.developers.recycletryout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

/**
 * Created by Developers on 03-06-2017.
 */

public class DisplayMovies extends AppCompatActivity {

    TextView content, title, LANGUAGE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.details);


        title = (TextView) findViewById(R.id.title_view);
        LANGUAGE = (TextView) findViewById(R.id.LANGUAGE);
        content = (TextView) findViewById(R.id.html_text);


        Intent i = getIntent();
        String Title = i.getStringExtra("title");
        String Name = i.getStringExtra("name");
        String Tips = i.getStringExtra("tips");

        title.setText(Title);
        LANGUAGE.setText(Name);
        content.setText(Html.fromHtml(Tips));
      //  content.setMovementMethod(new ScrollingMovementMethod());


    }
}
