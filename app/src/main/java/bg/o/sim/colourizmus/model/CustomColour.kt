package bg.o.sim.colourizmus.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.ColorInt
import java.io.Serializable

const val TABLE = "colour"
const val COLUMN_PK = "value"
const val COLUMN_NAME = "name"
const val COLUMN_IS_FAVOURITE = "is_favourite"

/**
 * The [CustomColour] represents a single, named, colour that
 * can be marked as favourite and is persisted in on-disk database.
 */
@Entity(tableName = TABLE, indices = [(Index(value = [COLUMN_NAME], unique = true))])
class CustomColour(@ColorInt value: Int, name: String) : Serializable {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = COLUMN_PK)
    @ColorInt val value: Int = value

    @ColumnInfo(name = COLUMN_NAME)
    var name: String = name

    @ColumnInfo(name = COLUMN_IS_FAVOURITE)
    var isFavourite: Boolean = false

    // TODO: 16/02/18 - include name in hash? Yes? No?
    override fun hashCode(): Int = value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (this.javaClass != other?.javaClass) return false

        other as CustomColour

        if (value != other.value) return false
        if (name != other.name) return false

        return true
    }
}
