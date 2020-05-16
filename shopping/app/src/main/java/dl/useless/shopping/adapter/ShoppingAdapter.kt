package dl.useless.shopping.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dl.useless.shopping.R
import dl.useless.shopping.ScrollingActivity
import dl.useless.shopping.data.AppDatabase
import dl.useless.shopping.data.Item
import dl.useless.shopping.touch.ItemTouchHelperCallback
import kotlinx.android.synthetic.main.item_row.view.*
import java.util.*

class ShoppingAdapter : RecyclerView.Adapter<ShoppingAdapter.ViewHolder>,
    ItemTouchHelperCallback {

    var shoppingItems = mutableListOf<Item>()
    val context: Context

    constructor(context: Context, listItems: List<Item>) {
        this.context = context
        shoppingItems.addAll(listItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.item_row, parent, false
        )

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shoppingItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = shoppingItems[position]
        holder.tvName.text = currentItem.itemName
        holder.tvPrice.text = context.resources.getString(R.string.display_price, currentItem.itemPrice)
        holder.tvQuantity.text = context.resources.getString(R.string.display_quantity, currentItem.quantity)
        holder.cbPurchased.isChecked = currentItem.purchased

        holder.btnDelete.setOnClickListener {
            deleteItem(holder.adapterPosition)
        }

        holder.btnEdit.setOnClickListener {
            (context as ScrollingActivity).showEditDialog(
                shoppingItems[holder.adapterPosition], holder.adapterPosition
            )
        }

        holder.btnDetail.setOnClickListener {
            (context as ScrollingActivity).showDetailActivity(
                shoppingItems[holder.adapterPosition])
        }

        holder.cbPurchased.setOnClickListener {
            shoppingItems[holder.adapterPosition].purchased = holder.cbPurchased.isChecked
            Thread{
                AppDatabase.getInstance(context).itemDao().updateItem(shoppingItems[holder.adapterPosition])
            }.start()
        }

        if(shoppingItems[holder.adapterPosition].category == 0) {
            holder.ivIcon.setImageResource(R.drawable.cpu)
        } else if(shoppingItems[holder.adapterPosition].category == 1) {
            holder.ivIcon.setImageResource(R.drawable.cooler)
        } else if(shoppingItems[holder.adapterPosition].category == 2) {
            holder.ivIcon.setImageResource(R.drawable.motherboard)
        } else if(shoppingItems[holder.adapterPosition].category == 3) {
            holder.ivIcon.setImageResource(R.drawable.memory)
        } else if(shoppingItems[holder.adapterPosition].category == 4) {
            holder.ivIcon.setImageResource(R.drawable.storage)
        } else if(shoppingItems[holder.adapterPosition].category == 5) {
            holder.ivIcon.setImageResource(R.drawable.videocard)
        } else if(shoppingItems[holder.adapterPosition].category == 6) {
            holder.ivIcon.setImageResource(R.drawable.psu)
        } else if(shoppingItems[holder.adapterPosition].category == 7) {
            holder.ivIcon.setImageResource(R.drawable.casenzxt)
        } else if(shoppingItems[holder.adapterPosition].category == 8) {
            holder.ivIcon.setImageResource(R.drawable.monitor)
        } else if(shoppingItems[holder.adapterPosition].category == 9) {
            holder.ivIcon.setImageResource(R.drawable.peripherals)
        } else if(shoppingItems[holder.adapterPosition].category == 10) {
            holder.ivIcon.setImageResource(R.drawable.videogame)
        } else if(shoppingItems[holder.adapterPosition].category == 11) {
            holder.ivIcon.setImageResource(R.drawable.mugi)
        }
    }

    private fun deleteItem(position: Int) {
        Thread {
            AppDatabase.getInstance(context).itemDao().deleteItem(
                shoppingItems.get(position))

            (context as ScrollingActivity).runOnUiThread {
                shoppingItems.removeAt(position)
                notifyItemRemoved(position)
            }
        }.start()
    }

    private fun deleteAllItems(){
        Thread {
            AppDatabase.getInstance(context).itemDao().nukeTable()
        }.start()
    }

    private fun clearView(){
        var size = shoppingItems.size
        shoppingItems.clear()
        notifyItemRangeRemoved(0, size)
    }

    public fun addItem(item: Item) {
        shoppingItems.add(item)
        notifyItemInserted(shoppingItems.lastIndex)
    }

    public fun updateItem(item:Item, editIndex: Int) {
        shoppingItems.set(editIndex, item)
        notifyItemChanged(editIndex)
    }

    public fun deleteAll(){
        deleteAllItems()
        clearView()
    }

    override fun onDismissed(position: Int) {
        deleteItem(position)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(shoppingItems, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName = itemView.item_name
        val tvPrice = itemView.item_price
        val tvQuantity = itemView.item_quantity
        val cbPurchased = itemView.checkBox
        val btnDelete = itemView.btn_delete
        val btnEdit = itemView.btn_detail
        val btnDetail = itemView.btn_view
        val ivIcon = itemView.item_icon
    }
}