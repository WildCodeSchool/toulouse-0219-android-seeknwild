package fr.wildcodeschool.seeknwild;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class TakePicMoment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_pic_moment);

        ImageView ivLogo = findViewById(R.id.ivTreasure);
        String url = "https://i.goopics.net/5DbkX.jpg";
        Glide.with(this).load(url).into(ivLogo);
    }
}
