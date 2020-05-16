package dl.useless.textbookdust2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import dl.useless.textbookdust2.adapter.NadeAdapter
import dl.useless.textbookdust2.data.AppDatabase
import dl.useless.textbookdust2.data.Nade
import dl.useless.textbookdust2.data.Photo
import dl.useless.textbookdust2.touch.RecyclerTouchListener
import kotlinx.android.synthetic.main.activity_utility_menu.*

class UtilityMenu : AppCompatActivity(), RecyclerTouchListener {

    companion object{
        const val KEY_NADE = "KEY_NADE"
        const val KEY_P = "KEY_P"
        const val KEY_T = "KEY_T"
        const val KEY_V = "KEY_V"
    }

    private lateinit var nadeAdapter:NadeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_utility_menu)

        initRecyclerView()

        var nadesAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.nades_array,
            R.layout.my_spinner
        )

        var locAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.nades_loc,
            R.layout.my_spinner
        )

        nadesAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown)
        locAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown)
        nadeType.adapter = nadesAdapter
        nadeLoc.adapter = locAdapter
    }

    private fun initRecyclerView(){
        Thread {
            var nadeList = AppDatabase.getInstance(this).nadeDao().getAllNades()
            runOnUiThread {
                nadeAdapter = NadeAdapter(this, this, nadeList)
                recyclerNades.adapter = nadeAdapter
            }
        }.start()
    }

    fun applyFilter(view:View){
        val type = nadeType.selectedItemPosition
        val location = nadeLoc.selectedItemPosition
        Thread {
            val nadeList = if (type==0 && location ==0){
                AppDatabase.getInstance(this).nadeDao().getAllNades()
            } else if (type!=0 && location ==0){
                AppDatabase.getInstance(this).nadeDao().getByType(type)
            } else if (type==0 && location !=0){
                AppDatabase.getInstance(this).nadeDao().getByLocation(location)
            } else{
                AppDatabase.getInstance(this).nadeDao().getFiltered(type, location)
            }
            runOnUiThread {
                nadeAdapter.refreshView(nadeList)
            }
        }.start()
    }

    override fun onItemTouched(nade: Nade) {
        Thread {
            var result = AppDatabase.getInstance(this).nadeDao().getMediaByNadeID(nade.ID)
            runOnUiThread {
                val utilityIntent = Intent(this@UtilityMenu, UtilityView::class.java)
                utilityIntent.putExtra(KEY_NADE, nade)
                utilityIntent.putExtra(KEY_P, result[0])
                utilityIntent.putExtra(KEY_T, result[1])
                utilityIntent.putExtra(KEY_V, result[2])
                startActivity(utilityIntent)
            }
        }.start()
    }

    override fun onPhotoTouched(resid:Int) {
    }
}
