package com.example.socialmediaapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.socialmediaapp.databinding.WeatherBinding
import com.example.socialmediaapp.utils.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.util.Locale

class weather : AppCompatActivity() {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
        val TAG = MapsWindow::class.java.simpleName
    }


    private fun requestLocationPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission granted
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
                // Location permission denied
            }
        }
    }

    private lateinit var binding: WeatherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestLocationPermissions()
        getCurrentWeather()
    }


    private fun getCurrentWeather() {
        GlobalScope.launch(Dispatchers.IO){
            val response = try {
                RetrofitInstance.api.getCurrentWeather("vijayawada","metric", "")
            }catch (e:IOException){
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext,"{${e.message}}",Toast.LENGTH_SHORT).show()
                }
                return@launch
            }catch(e:HttpException){
                withContext(Dispatchers.Main) {
                    Toast.makeText(applicationContext,"{${e.message}}",Toast.LENGTH_SHORT).show()
                }
                return@launch
            }

            if(response.isSuccessful && response.body()!=null){
                withContext(Dispatchers.Main){
                    val data = response.body()!!
                    binding.apply {
                        tvLocation.text = "${data.name}\n${data.sys.country}"
                        tvStatus.text = data.weather[0].description.uppercase(Locale.getDefault())
                        tvTemp.text = "${data.main.temp.toInt()}°C"
                        tvWindvelocity.text = "Wind Speed: ${data.wind.speed} m/s"
                        tvAirspeed.text = "Air Speed: ${data.wind.speed} m/s"
                        tvBreezeintensity.text = "Breeze Intensity: ${data.wind.speed} m/s"
                        tvAtmosphericvelocity.text = "Atmospheric Velocity: ${data.wind.speed} m/s"
                    }
                }
            }
        }
    }
}



