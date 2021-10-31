package bg.o.sim.colourizmus.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import bg.o.sim.colourizmus.R
import bg.o.sim.colourizmus.databinding.ActivityColourListBinding
import bg.o.sim.colourizmus.model.*
import bg.o.sim.colourizmus.utils.EXTRA_COLOUR
import bg.o.sim.colourizmus.utils.toastLong
import java.io.Serializable
import java.util.*

class ColourListActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {

    private lateinit var binding: ActivityColourListBinding
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        setColourFavorite(buttonView!!.tag as CustomColour, isChecked)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityColourListBinding.inflate(layoutInflater);
        setContentView(binding.root)

        binding.listRecycler.adapter = RecyclerAdapter(this, layoutInflater)

        binding.TESTDATAGENBUTTON.setOnClickListener {
            val rand = Random()

            for (i in 0..99) {
                val r = rand.nextInt(256)
                val g = rand.nextInt(256)
                val b = rand.nextInt(256)
                saveColour(CustomColour(Color.rgb(r, g, b), "Test " + rand.nextGaussian()))
            }

            toastLong("DONE!")
        }

        findViewById<View>(R.id.TEST_DATA_REM_BUTTON).setOnClickListener {
            deleteAllColours()
            toastLong("DONE!")
        }
    }
}


internal class RecyclerAdapter(lifecycleOwner: LifecycleOwner, private val mInflater: LayoutInflater) : RecyclerView.Adapter<RecyclerAdapter.ColourViewHolder>() {

    init {
        sCachedColours!!.observe(lifecycleOwner, Observer { this.notifyDataSetChanged() })
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColourViewHolder {
        return ColourViewHolder(mInflater.inflate(R.layout.card_colour_row, parent, false))
    }

    override fun getItemId(position: Int) = sCachedColours!!.value!![position].value.toLong()

    override fun onBindViewHolder(holder: ColourViewHolder, position: Int) {
        val colour = sCachedColours!!.value!![position]

        holder.rootView.tag = colour
        holder.mName.text = colour.name
        holder.mIsFavourite.isChecked = colour.isFavourite
        holder.mPreview.setBackgroundColor(colour.value)
    }

    override fun getItemCount(): Int = sCachedColours?.value?.size ?: 0

    class ColourViewHolder(internal val rootView: View) : RecyclerView.ViewHolder(rootView) {

        internal val mPreview: ImageView = rootView.findViewById(R.id.cardview_colour_preview)
        internal val mName: TextView = rootView.findViewById(R.id.cardview_colour_name)
        internal val mIsFavourite: CheckBox = rootView.findViewById(R.id.cardview_colour_favourite)

        init {
            rootView.setOnClickListener {
                val intent = Intent(rootView.context, ColourDetailsActivity::class.java)
                intent.putExtra(EXTRA_COLOUR, rootView.tag as Serializable)
                rootView.context.startActivity(intent)
            }
        }
    }
}