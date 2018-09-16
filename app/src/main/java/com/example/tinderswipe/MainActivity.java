package com.example.tinderswipe;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarDrawerToggle;


import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

public class MainActivity extends AppCompatActivity {
    //Constants
    static final int HOME = 0;
    static final int SETTINGS = 1;
    static final int ABOUT = 2;

    private SwipePlaceHolderView mSwipeView;
    private Context mContext;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Card Swipe
        mSwipeView = (SwipePlaceHolderView) findViewById(R.id.swipeView);
        mContext = getApplicationContext();

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));


        for (Profile profile : Utils.loadProfiles(this.getApplicationContext())) {
            mSwipeView.addView(new TinderCard(mContext, profile, mSwipeView));
        }

        findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });

        findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });

        //Navigation Drawer
        mDrawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.openDrawer, R.string.closeDrawer);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        // set item as selected to persist highlight
                        //item.setChecked(true);

                        // close drawer when item is tapped
                        //mDrawerLayout.closeDrawers();
                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        int id = item.getItemId();
                        switch (id) {
                            case R.id.nav_home:
                                openActivity(HOME);
                            case R.id.nav_settings:
                                openActivity(SETTINGS);
                            case R.id.nav_about:
                                openActivity(ABOUT);
                            default:
                                return true;
                        }
                    }
                }
        );


    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            if(toggle.onOptionsItemSelected(item)) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }





    public void openActivity(int i) {
        Intent intent ;

        if (i == ABOUT) {
            intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
        else if (i == SETTINGS) {
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        else if (i == HOME) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
