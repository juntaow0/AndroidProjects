package dl.useless.ezweather.network

import dl.useless.ezweather.data.WeatherResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//host: http://api.openweathermap.org
//
// PATH: /data/2.5/weather
//
// QUERY arguments: ? q=budapest & units=metric &appid=fff0ffec25b0bab74a094b4f82dee50a

interface WeatherAPI {
    @GET("/data/2.5/weather")
    fun getWeather(@Query("q") q: String, @Query("units") units: String, @Query("appid") appid: String): Call<WeatherResult>
}