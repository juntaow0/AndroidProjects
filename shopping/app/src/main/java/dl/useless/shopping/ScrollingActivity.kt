package dl.useless.shopping

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import dl.useless.shopping.adapter.ShoppingAdapter
import dl.useless.shopping.data.AppDatabase
import dl.useless.shopping.data.Item
import dl.useless.shopping.touch.ItemRecyclerTouchCallback
import kotlinx.android.synthetic.main.activity_scrolling.*

class ScrollingActivity : AppCompatActivity(), ItemDialog.ItemHandler {

    lateinit var shoppingAdapter: ShoppingAdapter

    companion object {
        const val KEY_EDIT = "KEY_EDIT"
        const val KEY_VIEW = "KEY_VIEW"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        setSupportActionBar(toolbar)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        Thread {
            var itemList = AppDatabase.getInstance(this).itemDao().getAllItems()

            runOnUiThread {
                shoppingAdapter = ShoppingAdapter(this, itemList)
                recyclerItem.adapter = shoppingAdapter
                recyclerItem.setHasFixedSize(true)
                val touchCallbackList = ItemRecyclerTouchCallback(shoppingAdapter)
                val itemTouchHelper = ItemTouchHelper(touchCallbackList)
                itemTouchHelper.attachToRecyclerView(recyclerItem)
            }
        }.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_add) {
            // create new item
            showAddItemDialog()
        } else if (item.itemId == R.id.action_delete) {
            // create all items
            deleteAllItems()
            Toast.makeText(this, getString(R.string.list_clear), Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    fun showAddItemDialog() {
        ItemDialog().show(supportFragmentManager, "NEWDIALOG")
    }

    fun deleteAllItems(){
        shoppingAdapter.deleteAll()
    }

    var editIndex: Int = -1

    public fun showEditDialog(itemToEdit: Item, index: Int) {
        editIndex = index

        val editItemDialog = ItemDialog()

        val bundle = Bundle()
        bundle.putSerializable(KEY_EDIT, itemToEdit)
        editItemDialog.arguments = bundle

        editItemDialog.show(supportFragmentManager, "EDITDIALOG")
    }

    public fun showDetailActivity(item:Item){
        val detailIntent = Intent(this, ItemDetail::class.java)
        detailIntent.putExtra(KEY_VIEW,item)
        startActivity(detailIntent)
    }

    fun saveItem(item: Item) {
        Thread {
            item.itemId = AppDatabase.getInstance(this).itemDao().insertItem(item)

            runOnUiThread {
                shoppingAdapter.addItem(item)
            }
        }.start()
    }

    override fun itemCreated(item: Item) {
        saveItem(item)
    }

    override fun itemUpdated(item: Item) {
        Thread {
            AppDatabase.getInstance(this).itemDao().updateItem(item)

            runOnUiThread {
                shoppingAdapter.updateItem(item, editIndex)
            }
        }.start()
    }
}
