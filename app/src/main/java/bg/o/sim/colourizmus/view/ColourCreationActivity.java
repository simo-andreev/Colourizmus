package bg.o.sim.colourizmus.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import bg.o.sim.colourizmus.R;
import bg.o.sim.colourizmus.model.CR;
import bg.o.sim.colourizmus.utils.UtilKt;

public class ColourCreationActivity extends AppCompatActivity {

    private ViewPager mCreationMethodViewPager;
    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(findViewById(R.id.allahu_appbar));

        findViewById(R.id.release_da_kamrakken).setOnClickListener(v -> {
            /*v -> CameraActivity.start(ColourCreationActivity.this)*/

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, UtilKt.REQUEST_IMAGE_PERMISSION);
            } else {
                takePhoto();
            }
        });

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

        mCreationMethodViewPager.setBackgroundColor(CR.LIVE_COLOUR.getValue());
        CR.LIVE_COLOUR.observe(this, integer -> {
            mCreationMethodViewPager.setBackgroundColor(integer);
        });

        FloatingActionButton fab = findViewById(R.id.main_fab_save_colour);
        fab.setOnClickListener(v ->
                new SaveColourDialogue().show(getSupportFragmentManager(), SaveColourDialogue.TAG)
        );

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

    public static void start(Context context) {
        Intent starter = new Intent(context, ColourCreationActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case UtilKt.REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent(this, ColourDetailsActivity.class);

                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");

                    intent.putExtra(UtilKt.EXTRA_PICTURE_URI, mImageUri);
                    intent.putExtra(UtilKt.EXTRA_PICTURE_THUMB, imageBitmap);

                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == UtilKt.REQUEST_IMAGE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                Toast.makeText(this, "Y U NO GIB PRMISHNZ? :@", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void takePhoto() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String auth = getString(R.string.file_provider_auth);

            File file;

            try {
                file = UtilKt.getImageFile(ColourCreationActivity.this);
            } catch (IOException e) {
                Log.e(UtilKt.LOG_TAG_ERR, "onCreate: ", e);
                Toast.makeText(this, "Ooops.! Somethang dun fucked up. Sorry '(8_8)", Toast.LENGTH_SHORT).show();
                return;
            }

            mImageUri = FileProvider.getUriForFile(this, auth, file);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);

            startActivityForResult(takePictureIntent, UtilKt.REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "You have no camera dummy (^.^)", Toast.LENGTH_SHORT).show();
        }

    }
}
