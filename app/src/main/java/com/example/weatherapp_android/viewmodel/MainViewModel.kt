package com.example.weatherapp_android.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp_android.model.WeatherModel
import com.example.weatherapp_android.service.WeatherAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    private val weatherApiService = WeatherAPIService()
    private val disposable = CompositeDisposable()
    val weather_data = MutableLiveData<WeatherModel>()
    val weather_data_error = MutableLiveData<Boolean>()


    fun refreshData(cityName: String) {
        getDataFromAPI(cityName)
    }


    private fun getDataFromAPI(cityName: String) {
        disposable.add(
            weatherApiService.dataDetail(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object  : DisposableSingleObserver<WeatherModel>(){
                    override fun onSuccess(t: WeatherModel) {
                        weather_data.value = t
                    }

                    override fun onError(e: Throwable) {
                        weather_data_error.value = true

                        e.printStackTrace()


                    }

                })
        )


    }

}