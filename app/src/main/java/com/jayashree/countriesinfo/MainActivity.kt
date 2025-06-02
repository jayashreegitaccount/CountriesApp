package com.jayashree.countriesinfo

import NetworkLayer.Countries
import NetworkLayer.RetrofitInstance
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.countries_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter
        fetchCountries()
    }


    private fun fetchCountries(){
        RetrofitInstance.retrofit.getAllCountries().enqueue(object : Callback<List<Countries>> {
            override fun onResponse(call: Call<List<Countries>>, response: Response<List<Countries>>) {
                if (response.isSuccessful) {
                    val countries = response.body() ?: emptyList()
                    recyclerView.adapter = CountryListAdapter(countries)
                }
            }

            override fun onFailure(call: Call<List<Countries>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
