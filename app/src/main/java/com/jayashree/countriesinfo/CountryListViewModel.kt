package com.jayashree.countriesinfo

import NetworkLayer.Countries
import NetworkLayer.CountryDecsription
import NetworkLayer.RetrofitInstance
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryListViewModel: ViewModel() {
    private val _items = mutableStateOf<List<Countries>>(emptyList())
    val items: State<List<Countries>> = _items
    val countryDescriptionLiveData:MutableLiveData<CountryDecsription> = MutableLiveData()


     fun fetchCountries(){
        RetrofitInstance.retrofit.getAllCountries().enqueue(object : Callback<List<Countries>> {
            override fun onResponse(call: Call<List<Countries>>, response: Response<List<Countries>>) {
                if (response.isSuccessful) {
                    val countries = response.body() ?: emptyList()
                    _items.value = countries
                }
            }

            override fun onFailure(call: Call<List<Countries>>, t: Throwable) {
            }
        })
    }

    fun fetchCountryDescription(selectedCountry: String) {
        var countryDescription: CountryDecsription? = null
        RetrofitInstance.countryDescriptionrequest.getCountryDescription(selectedCountry).enqueue(
            object : Callback<CountryDecsription> {
                override fun onResponse(call: Call<CountryDecsription>, response: Response<CountryDecsription>) {
                    if (response.isSuccessful) {
                        countryDescriptionLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<CountryDecsription>, t: Throwable) {

                }
            }
        )
    }


}