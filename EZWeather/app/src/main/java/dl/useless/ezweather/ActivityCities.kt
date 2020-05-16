package dl.useless.ezweather

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import dl.useless.ezweather.adapter.CityAdapter
import kotlinx.android.synthetic.main.activity_cities.*
import dl.useless.ezweather.touch.RecyclerTouchListener

class ActivityCities : AppCompatActivity(), RecyclerTouchListener{
    companion object{
        const val KEY_NAME = "KEY_NAME"
    }

    private lateinit var cityAdapter: CityAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            showAddCityDialog()
        }
        cityAdapter = CityAdapter(this, this)
        recyclerCity.adapter = cityAdapter
    }

    private fun showAddCityDialog(){
        AddCityDialog().show(supportFragmentManager, "NEWDIALOG")
    }

    public fun getCityName(name:String){
        cityAdapter.addCity(name)
    }

    override fun onItemTouched(city: String) {
        var weatherIntent = Intent(this@ActivityCities, ActivityWeather::class.java)
        weatherIntent.putExtra(KEY_NAME, city)
        startActivity(weatherIntent)
    }
}
