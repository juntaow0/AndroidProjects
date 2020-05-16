package dl.useless.shopping.data

import androidx.room.*

@Dao
interface ItemDAO {
    @Query("SELECT * FROM item")
    fun getAllItems(): List<Item>

    @Query("DELETE FROM item")
    fun nukeTable()

    @Insert
    fun insertItem(item: Item) : Long

    @Delete
    fun deleteItem(item: Item)

    @Update
    fun updateItem(item: Item)
}