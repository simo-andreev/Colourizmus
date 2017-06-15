package com.colourizmus.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.colourizmus.R;

import java.util.HashSet;

//TODO - not a good cross-fragment cominication system. Allows for loos-ish coupleing but does A LOT of uneccessary chnages :/
interface ColourComunicator {
    public void addObserver(ColourComunicee c);

    public void notifyComunicee();
}

interface ColourComunicee {
    public void observe(ColourComunicator c);

    public void react(int r, int g, int b);
}

public class MainActivity extends AppCompatActivity implements ColourComunicator {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private View colorBox;

    private HashSet<ColourComunicee> observers;

    private int r, g, b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        r = 0;
        g = 0;
        b = 0;

        observers = new HashSet<>();

        colorBox = findViewById(R.id.main_container);

        Toolbar toolbar = (Toolbar) findViewById(R.id.allahu_appbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.mipmap.ic_launcher);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.main_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    protected void setColour(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        colorBox.setBackgroundColor(Color.rgb(r, g, b));
        notifyComunicee();
    }

    @Override
    public void addObserver(ColourComunicee c) {
        if (c == null) return;
        this.observers.add(c);
    }

    @Override
    public void notifyComunicee() {
        for (ColourComunicee c : observers)
            c.react(r, g, b);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return SeekerFragment.newInstance();
                case 1:
                    return PickerFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
            }
            return null;
        }
    }
}