package com.jayashree.countriesinfo

import NetworkLayer.Countries
import NetworkLayer.RetrofitInstance
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView:RecyclerView
    private var viewModel: CountryListViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel = ViewModelProvider(this).get(CountryListViewModel::class.java)
        setContent {
            ScreenTopBar()
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ScreenTopBar(){
        Column {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(title = { Text("Countries") })
                },
                content = { innerPadding->
                    ItemList(modifier = Modifier.padding(innerPadding))
                }
            )

        }
    }

    @Composable
    fun ItemList(modifier: Modifier = Modifier) {
        LaunchedEffect(Unit) {
            viewModel?.fetchCountries()
        }
        val countries:List<Countries> = viewModel?.items!!.value
        LazyColumn(contentPadding = PaddingValues(0.dp,100.dp,0.dp,0.dp)) {
            items(countries.size) { item ->
                ItemView(countries.get(item))
            }
        }

    }

    @Composable
    fun ItemView(country: Countries) {
        val context = LocalContext.current
        Column {
            LoadImage(country.flags.png)
            Column (modifier = Modifier.padding(start = 15.dp, top = 10.dp, bottom = 15.dp)){
                Text(
                    text = "Country Name: " + country.name.common,
                    color = Color.Black,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = "Country Capital: " + country.capital.firstOrNull() ?: "NA",
                    color = Color.Black,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = "Region: " +country.region,
                    color = Color.Black,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = "Population: " + country.population.toString(),
                    color = Color.Black,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = "Know more about ${country.name.common}",
                    color = Color.Blue,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable {
                            val intent = Intent(context, KnowMoreAboutCountryActivity::class.java)
                            intent.putExtra("CountryName", country.name.common)
                            context.startActivity(intent)
                        }
                )
            }
        }
    }

    @Composable
    fun LoadImage(image:String){
        AsyncImage(model = image,
            contentDescription = "Flag images",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }


    private fun fetchCountries(){
        RetrofitInstance.retrofit.getAllCountries().enqueue(object : Callback<List<Countries>> {
            override fun onResponse(call: Call<List<Countries>>, response: Response<List<Countries>>) {
                if (response.isSuccessful) {
                    val countries = response.body() ?: emptyList()
                    //recyclerView.adapter = CountryListViewModel(countries)
                }
            }

            override fun onFailure(call: Call<List<Countries>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
