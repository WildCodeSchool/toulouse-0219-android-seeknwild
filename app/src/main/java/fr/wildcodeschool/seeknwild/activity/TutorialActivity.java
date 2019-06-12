package fr.wildcodeschool.seeknwild.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.wildcodeschool.seeknwild.R;
import fr.wildcodeschool.seeknwild.adapter.TutorialAdapter;

public class TutorialActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout layoutDot;
    private TextView[] dotstv;
    private int[] layouts;
    private Button btnPass;
    private Button btnFollowing;
    private TutorialAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_tutorial);

        viewPager = findViewById(R.id.view_pager);
        layoutDot = findViewById(R.id.dotLayout);
        btnFollowing = findViewById(R.id.btn_following);
        btnPass = findViewById(R.id.btn_pass);

        btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHomeActivity();
            }
        });

        layouts = new int[]{R.layout.tutorial_move, R.layout.tutorial_discover, R.layout.tutorial_share};
        pageAdapter = new TutorialAdapter(layouts, getApplicationContext());
        viewPager.setAdapter(pageAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if (i == layouts.length - 1) {
                    btnFollowing.setText(getString(R.string.cest_parti));
                    btnPass.setVisibility(View.GONE);
                } else {
                    btnFollowing.setText(getString(R.string.suiv_ant));
                    btnPass.setVisibility(View.VISIBLE);
                }
                setDotStatus(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        setDotStatus(0);

        btnFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPage = viewPager.getCurrentItem()+1;
                if(currentPage < layouts.length) {
                    //move to next page
                    viewPager.setCurrentItem(currentPage);
                } else {
                    startHomeActivity();
                }
            }
        });

    }


    private void setDotStatus(int page) {
        layoutDot.removeAllViews();
        dotstv = new TextView[layouts.length];
        for (int i = 0; i < dotstv.length; i++) {
            dotstv[i] = new TextView(this);
            dotstv[i].setText(Html.fromHtml("&#8226;"));
            dotstv[i].setTextSize(30);
            dotstv[i].setTextColor(Color.parseColor("#a9b4bb"));
            layoutDot.addView(dotstv[i]);
        }

        if (dotstv.length > 0) {
            dotstv[page].setTextColor(Color.parseColor("#000000"));
        }
    }

    private void startHomeActivity() {
        startActivity(new Intent(TutorialActivity.this, MainActivity.class));
        finish();
    }

}
