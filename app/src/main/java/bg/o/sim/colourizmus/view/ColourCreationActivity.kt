package bg.o.sim.colourizmus.view

import android.Manifest.permission.CAMERA
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import bg.o.sim.colourizmus.R
import bg.o.sim.colourizmus.databinding.ActivityMainBinding
import bg.o.sim.colourizmus.model.CustomColour
import bg.o.sim.colourizmus.model.LIVE_COLOUR
import bg.o.sim.colourizmus.model.LiveColour
import bg.o.sim.colourizmus.utils.*

class ColourCreationActivity : AppCompatActivity() {

    private var mImageUri: Uri? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.setSupportActionBar(binding.allahuAppbar.allahuAppbar)

        binding.mainFabHexadecInput.setOnClickListener {
            MainScreenHexInput().show(fragmentManager, "HexadecInputDialog")
        }

        binding.mainFabReleaseDaKamrakken.setOnClickListener {
            if (this.havePermission(CAMERA))
                takePhoto()
            else
                ActivityCompat.requestPermissions(this, arrayOf(CAMERA), REQUEST_PERMISSION_IMAGE_CAPTURE)
        }

        binding.mainFabSaveColour.setOnClickListener {
            SaveColourDialogue().show(fragmentManager, SaveColourDialogue.TAG)
        }

        binding.colourCreationPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment = when (position) {
                0 -> SeekerFragment.newInstance()
                1 -> PickerFragment.newInstance()
                else -> throw IllegalArgumentException()
            }

            override fun getCount() = 2

            override fun getPageTitle(position: Int): CharSequence = when (position) {
                0 -> getString(R.string.coarse)
                1 -> getString(R.string.precise)
                else -> getString(R.string.FUCK)
            }
        }

        LIVE_COLOUR.observe(this, Observer<Int> {
            binding.colourCreationPager.setBackgroundColor(it!!)
            binding.colourCreationHexadecPreview.text = "#${Integer.toHexString(it)}"
            binding.colourCreationHexadecPreview.setTextColor(getComplimentaryColour(CustomColour(it, "")).value)
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION_IMAGE_CAPTURE ->
                if (PERMISSION_GRANTED in grantResults) takePhoto()
                else this.toastLong("Y U NO GIB PRMISHNZ? :@")
        // other cases can go here at a later point
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> if (resultCode == RESULT_OK) {
                val showPicResult = Intent(this, ColourDetailsActivity::class.java)
                showPicResult.putExtra(EXTRA_PICTURE_URI, mImageUri)
                showPicResult.putExtra(EXTRA_PICTURE_THUMB, data!!.extras!!["data"] as Bitmap)
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