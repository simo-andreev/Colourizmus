package bg.o.sim.colourizmus.view

import android.Manifest.permission.CAMERA
import android.arch.lifecycle.Observer
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import bg.o.sim.colourizmus.R
import bg.o.sim.colourizmus.model.CustomColour
import bg.o.sim.colourizmus.model.LIVE_COLOUR
import bg.o.sim.colourizmus.model.LiveColour
import bg.o.sim.colourizmus.utils.*
import kotlinx.android.synthetic.main.activity_main.*

class ColourCreationActivity : AppCompatActivity() {

    private var mImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.setSupportActionBar(allahu_appbar as Toolbar)

        main_fab_hexadec_input.setOnClickListener {
            MainScreenHexInput().show(fragmentManager, "HexadecInputDialog")
        }

        main_fab_release_da_kamrakken.setOnClickListener {
            if (this.havePermission(CAMERA))
                takePhoto()
            else
                ActivityCompat.requestPermissions(this, arrayOf(CAMERA), REQUEST_PERMISSION_IMAGE_CAPTURE)
        }

        main_fab_save_colour.setOnClickListener {
            SaveColourDialogue().show(fragmentManager, SaveColourDialogue.TAG)
        }

        colour_creation_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment? = when (position) {
                0 -> SeekerFragment.newInstance()
                1 -> PickerFragment.newInstance()
                else -> null
            }

            override fun getCount() = 2

            override fun getPageTitle(position: Int): CharSequence = when (position) {
                0 -> getString(R.string.coarse)
                1 -> getString(R.string.precise)
                else -> getString(R.string.FUCK)
            }
        }

        LIVE_COLOUR.observe(this, Observer<Int> {
            colour_creation_pager.setBackgroundColor(it!!)
            colour_creation_hexadec_preview.text = "#${Integer.toHexString(it)}"
            colour_creation_hexadec_preview.setTextColor(getComplimentaryColour(CustomColour(it, "")).value)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> startActivity(Intent(this, ColourListActivity::class.java))
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard, menu)
        return true
    }

    private fun takePhoto() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (cameraIntent.resolveActivity(packageManager) != null) {
            val authority = getString(R.string.file_provider_auth)
            mImageUri = FileProvider.getUriForFile(this, authority, getImageFile())
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri)

            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
        } else {
            this.toastLong("You have no camera dummy (^.^)")
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSION_IMAGE_CAPTURE ->
                if (PERMISSION_GRANTED in grantResults) takePhoto()
                else this.toastLong("Y U NO GIB PRMISHNZ? :@")
        // other cases can go here at a later point
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> if (resultCode == RESULT_OK) {
                val showPicResult = Intent(this, ColourDetailsActivity::class.java)
                showPicResult.putExtra(EXTRA_PICTURE_URI, mImageUri)
                showPicResult.putExtra(EXTRA_PICTURE_THUMB, data.extras["data"] as Bitmap)
                startActivity(showPicResult)
            }
        // other cases can go here at a later point
        }
    }
}

class MainScreenHexInput : HexadecInputDialog() {
    override fun onSave(dialogue: HexadecInputDialog, colour: LiveColour) {
        LIVE_COLOUR.set(colour)
        dialogue.dismiss()
    }
}