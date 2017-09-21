package bg.o.sim.colourizmus.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import bg.o.sim.colourizmus.R;
import bg.o.sim.colourizmus.model.CR;

public class ColourCreationActivity extends AppCompatActivity {

    private ViewPager mCreationMethodViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CR.init(getApplication());

        setSupportActionBar(findViewById(R.id.allahu_appbar));

        mCreationMethodViewPager = findViewById(R.id.colour_creation_pager);
        mCreationMethodViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
                        return getString(R.string.coarse);
                    case 1:
                        return getString(R.string.precise);
                    default:
                        return getString(R.string.FUCK);
                }
            }
        });

        TabLayout tabLayout = findViewById(R.id.colour_creation_tab_layout);
        tabLayout.setupWithViewPager(mCreationMethodViewPager);

        CR.LIVE_COLOR.observe(this, integer -> {
            mCreationMethodViewPager.setBackgroundColor(integer);
        });

        FloatingActionButton fab = findViewById(R.id.main_fab_save_colour);
        fab.setOnClickListener(v -> {
            DialogFragment dialogFragment = new SaveColourDialogue();
            dialogFragment.show(getSupportFragmentManager(), SaveColourDialogue.TAG);
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
}
