package dl.useless.textbookdust2.data

import androidx.room.*

@Dao
interface NadeDAO {
    @Query("SELECT * FROM Nades")
    fun getAllNades(): List<Nade>

    @Query("SELECT * FROM Nades WHERE Type = :type")
    fun getByType(type: Int): List<Nade>

    @Query("SELECT * FROM Nades WHERE Location = :location")
    fun getByLocation(location:Int): List<Nade>

    @Query("SELECT * FROM Nades WHERE Type = :type AND Location = :location")
    fun getFiltered(type: Int, location: Int): List<Nade>

    @Query("SELECT * FROM Media WHERE NadeID = :id")
    fun getMediaByNadeID(id:Int): List<Media>
}