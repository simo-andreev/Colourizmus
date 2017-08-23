package bg.o.sim.colourizmus.view;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import bg.o.sim.colourizmus.R;
import bg.o.sim.colourizmus.model.ColourRepository;
import bg.o.sim.colourizmus.model.CustomColour;
import bg.o.sim.colourizmus.utils.Util;

import java.util.Random;

public class ColourCreationActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    //Can't extend LifecycleActivity for compat reasons. When the new arch comps are out of alpha, the concrete implementation of LifecycleRegistryOwner methods can be skipped
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private ViewPager mCreationMethodViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ColourRepository.init(getApplication());

        setSupportActionBar(findViewById(R.id.allahu_appbar));
		getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.main_fab_save_colour);

        mCreationMethodViewPager = (ViewPager) findViewById(R.id.main_colour_pager);
        mCreationMethodViewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        tabLayout.setupWithViewPager(mCreationMethodViewPager);

        ColourRepository.LIVE_COLOR.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                mCreationMethodViewPager.setBackgroundColor(integer);
                Log.v(Util.LOG_TAG_DEV, "LIVE_COLOUR: onChanged: " + integer);
            }
        });

        ColourRepository.saveColour(new CustomColour("TEST", 0xFF00f0ff));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                CustomColour c = new CustomColour("TEST" + rand.nextInt(), ColourRepository.LIVE_COLOR.getValue());
                ColourRepository.saveColour(c);
                Util.makeShortToast(ColourCreationActivity.this, "SAVED : " + c.getValue());
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        startActivity(new Intent(this, ColourListActivity.class));
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
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
                    return new SeekerFragment();
                case 1:
                    return new PickerFragment();
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
            //TODO - extract strings
            switch (position){
                case 0: return "coarse";
                case 1: return "precise";
                default: return "seems I've fucked up somewhere....";
            }
        }
    }
}