package com.colourizmus.view;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.Space;

import com.colourizmus.R;
import com.colourizmus.utils.Util;

import java.util.HashSet;

//TODO - not a good cross-fragment cominication system. Allows for loos-ish coupling but does A LOT of unnecessary changes :/
interface ColourComunicator {
    public void addObserver(ColourComunicee c);

    public void notifyComunicee();
}

interface ColourComunicee {
    public void observe(ColourComunicator c);

    public void react(int r, int g, int b);
}

public class MainActivity extends AppCompatActivity implements ColourComunicator, LifecycleRegistryOwner {

    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private Space newColour;

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

        newColour = (Space) findViewById(R.id.main_new_colour_preview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.allahu_appbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.mipmap.ic_launcher);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.main_colour_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        Util.LIVE_COLOR.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                viewPager.setBackgroundColor(integer);
                Log.v(Util.LOG_TAG_DEV, "LIVE_COLOUR: onChanged: " + integer);
            }
        });
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
        newColour.setBackgroundColor(Color.rgb(r, g, b));
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

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Log.w(Util.LOG_TAG_DEV, "getItem: SeekerFragment");
                    return new SeekerFragment();
                case 1:
                    Log.w(Util.LOG_TAG_DEV, "getItem: PickerFragment");
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
            return "SECTION " + position;
        }
    }
}