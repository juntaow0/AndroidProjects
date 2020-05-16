package dl.useless.textbookdust2.data

import androidx.room.*
import java.io.Serializable

@Entity(tableName = "Nades")
data class Nade(
    @PrimaryKey var ID : Int,
    @ColumnInfo(name = "Name") var name: String,
    @ColumnInfo(name = "Type") var type: Int,
    @ColumnInfo(name = "Location") var location: Int,
    @ColumnInfo(name = "Description") var description: String
) : Serializable

@Entity(tableName = "Media")
data class Media(
    @PrimaryKey var ID: Int,
    @ColumnInfo(name = "Name") var name: String,
    @ColumnInfo(name = "Type") var type: Int,
    @ColumnInfo(name = "Description") var description: String,
    @ColumnInfo(name = "NadeID") var nadeid: Int
):Serializable