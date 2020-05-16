package dl.useless.shopping.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "item")
data class Item(
    @PrimaryKey(autoGenerate = true) var itemId : Long?,
    @ColumnInfo(name = "category") var category: Int,
    @ColumnInfo(name = "itemName") var itemName: String,
    @ColumnInfo(name = "itemPrice") var itemPrice: Long,
    @ColumnInfo(name = "quantity") var quantity: Long,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "purchased") var purchased: Boolean
) : Serializable