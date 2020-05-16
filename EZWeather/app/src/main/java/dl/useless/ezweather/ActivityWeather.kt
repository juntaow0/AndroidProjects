package dl.useless.ezweather

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dl.useless.ezweather.data.WeatherResult
import dl.useless.ezweather.network.WeatherAPI
import kotlinx.android.synthetic.main.activity_weather.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ActivityWeather : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val city = intent.getStringExtra(ActivityCities.KEY_NAME)

        val imgUrl = getString(R.string.img_host)

        val retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.api_host))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val weatherAPI = retrofit.create(WeatherAPI::class.java)
        val weatherCall = weatherAPI.getWeather(city!!, "metric", getString(R.string.api_key))

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        weatherCall.enqueue(
            object: Callback<WeatherResult>{
                override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                    tvLoad.text = t.message
                }

                override fun onResponse(
                    call: Call<WeatherResult>,
                    response: Response<WeatherResult>
                ) {
                    val weatherResult = response.body()
                    if (weatherResult?.cod!=null){
                        val lat = weatherResult.coord!!.lat!!.toDouble()
                        val lon = weatherResult.coord.lon!!.toDouble()
                        val location = LatLng(lat,lon)
                        val zoomLevel = 10.0f
                        // Add a marker at intended location and move camera
                        mMap.addMarker(MarkerOptions().position(location).title(getString(R.string.warning)))
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,zoomLevel))
                        val speed = weatherResult.wind!!.speed!!.toDouble() * (18/5)
                        val icon =  weatherResult.weather!![0].icon + "@2x.png"
                        val finalUrl = imgUrl + icon
                        Glide.with(this@ActivityWeather).load(finalUrl).into(ivIcon)
                        tvCity.text = city
                        tvDes.text = getString(R.string.description, weatherResult.weather[0].description)
                        tvTemp.text = getString(R.string.main_temp, weatherResult.main?.temp?.toDouble())
                        tvFeel.text = getString(R.string.feels_like, weatherResult.main?.feels_like?.toDouble())
                        tvMax.text = getString(R.string.max_temp, weatherResult.main?.temp_max?.toDouble())
                        tvMin.text = getString(R.string.min_temp, weatherResult.main?.temp_min?.toDouble())
                        tvHum.text = getString(R.string.humidity, weatherResult.main?.humidity?.toDouble())
                        tvWind.text = getString(R.string.wind, speed)

                        tvLoad.visibility = View.GONE
                        tvCity.visibility = View.VISIBLE
                        tvDes.visibility = View.VISIBLE
                        tvTemp.visibility = View.VISIBLE
                        tvFeel.visibility = View.VISIBLE
                        tvMax.visibility = View.VISIBLE
                        tvMin.visibility = View.VISIBLE
                        tvHum.visibility = View.VISIBLE
                        tvWind.visibility = View.VISIBLE
                        ivIcon.visibility = View.VISIBLE
                        mapHolder.visibility = View.VISIBLE
                    } else{
                        tvLoad.text = getString(R.string.city_error)
                    }
                }
            }
        )
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }
}
