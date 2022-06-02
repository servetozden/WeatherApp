package com.example.weatherapp_android

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.weatherapp_android.databinding.ActivityMainBinding
import com.example.weatherapp_android.viewmodel.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var viewmodel: MainViewModel
    lateinit var binding: ActivityMainBinding

    private lateinit var GET: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewmodel.refreshData("İstanbul")
        liveDataControl()

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
        }

        binding.buttonSearch.setOnClickListener {
            var cityName = binding.edittextCityName.text.toString()
            viewmodel.refreshData(cityName)
        }

    }


    fun liveDataControl(){
        viewmodel.weather_data.observe(this, Observer { data ->
            data?.let {

                binding?.tempText?.text = data.main.temp.toInt().toString()
                binding?.cityName?.text = data.name
                binding.textviewSun.text = data.wind.deg.toString()+" %"
                binding.textviewLatitude.text = data.coord.lat.toString()
                binding.textviewLongitude.text = data.coord.lon.toString()
                binding.textviewHumidity.text = data.main.humidity.toString()+" %"
                binding.textviewSpeed.text = data.wind.speed.toString() +" m/s"
                binding.descriptionText.text = data.weather[0].description


                val cDate = Date()
                val fDate = SimpleDateFormat("yyyy/MM/dd HH:mm").format(cDate)

                binding.dateText.text = fDate.toString()



            }
        })

        viewmodel.weather_data_error.observe(this, Observer { error ->
            error?.let {
                if (error) {
                    val text = "Not found city"
                    val duration = Toast.LENGTH_SHORT

                    val toast = Toast.makeText(applicationContext, text, duration)
                    toast.show()
                } else {
                }
            }
        })

    }
}