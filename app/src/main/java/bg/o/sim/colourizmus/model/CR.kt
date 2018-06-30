@file:JvmName("CR")

package bg.o.sim.colourizmus.model

import android.arch.lifecycle.LiveData
import android.os.AsyncTask

// todo - review whole file and stroage/db systems

/**
 * This class offers access to true-data in a clean abstracted way to the rest of the application.
 * It handles retrieving and caching data and provides an access-point to the persistence model.
 * <p>
 * note: ColourRepository shortened to CR for general code brevity, as it is oft referenced and always in a static manner.
 */

// TODO: 16/02/18 - can this be handled better (as in not using @JvmField)? Can't const because not primitive
@JvmField val LIVE_COLOUR: LiveColour = LiveColour()
@JvmField var sCachedColours: LiveData<MutableList<CustomColour>>? = null

private lateinit var sDatabase: ColourDatabase

/** Initialize the Repository by passing a database instance and start a query to pull data into cache */
fun init(database: ColourDatabase) {
    sDatabase = database
    sCachedColours = database.colourDao().allColours
}

fun setColourFavorite(colour: CustomColour, isChecked: Boolean) {
    colour.isFavourite = isChecked
    UpdateTask().execute(colour)
}

// '*' is a 'spread operator' in this case. Why is it necessary you ask?! I have no fucking idea...
fun saveColour(vararg colours: CustomColour) { SaveTask().execute(*colours) }

fun deleteAllColours() { DeleteTask().execute() }

private class SaveTask : AsyncTask<CustomColour, Unit, LongArray>() {
    override fun doInBackground(vararg customColours: CustomColour): LongArray {
        return sDatabase!!.colourDao().insertColours(*customColours)
    }
}

private class UpdateTask : AsyncTask<CustomColour, Unit, Int>() {
    override fun doInBackground(vararg customColours: CustomColour): Int? {
        return sDatabase!!.colourDao().updateColour(customColours[0])
    }
}

private class DeleteTask : AsyncTask<Unit, Unit, Unit>() {
    override fun doInBackground(vararg voids: Unit): Unit? {
        sDatabase!!.colourDao().deleteAllColours()
        return null
    }
}
