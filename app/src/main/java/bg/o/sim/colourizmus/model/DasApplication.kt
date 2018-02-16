package bg.o.sim.colourizmus.model

import android.app.Application
import android.arch.persistence.room.Room
import bg.o.sim.colourizmus.utils.DB_NAME


class DasApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        CR.innit(Room.databaseBuilder(this, ColourDatabase::class.java, DB_NAME).build())
    }

//    // This is called when the overall system is running low on memory,
//    // and would like actively running processes to tighten their belts.
//    override fun onLowMemory() {
//        super.onLowMemory()
//        // TODO: 24-09-17 - might be reasonable to clear cache until requested a-new
//    }
//
//    override fun onTrimMemory(level: Int) {
//        super.onTrimMemory(level)
//    }
//
//    override fun onTerminate() {
//        super.onTerminate()
//    }
}