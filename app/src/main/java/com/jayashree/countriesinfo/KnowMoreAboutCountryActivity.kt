package com.jayashree.countriesinfo

import NetworkLayer.CountryDecsription
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class KnowMoreAboutCountryActivity : AppCompatActivity() {
    var viewModel:CountryListViewModel? = null
    var mCountryDecsription:CountryDecsription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel = ViewModelProvider(this).get(CountryListViewModel::class.java)
        //mCountryDecsription = viewModel?.fetchCountryDescription(selectedCountry)
        val intent = intent
        val selectedCountry = intent.getStringExtra("CountryName")?:"default"
        viewModel?.fetchCountryDescription(selectedCountry)
        viewModel?.countryDescriptionLiveData?.observe(this, Observer {countryDescription->
            if(countryDescription != null){
                mCountryDecsription = countryDescription
                setContent{
                    ShowTextAboutCountry()
                }
            }

        })


    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ShowTextAboutCountry(){
       Scaffold(
           topBar = {
               mCountryDecsription?.let { CenterAlignedTopAppBar(title = { Text(mCountryDecsription?.title?:"Title") }) }?:Text(text = "Data not available")
           },
           content = { innerPadding ->
               ShowCountryDescriptionText(modifier = Modifier.padding(innerPadding))
           }
       )

    }

    @Composable
    fun ShowCountryDescriptionText(modifier: Modifier = Modifier){
        Column(modifier = modifier) {
            mCountryDecsription?.let { Text(text = mCountryDecsription?.description?:"Description  Not available", color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold) }?:Text(text = "Data not available")
            mCountryDecsription?.let { Text(text = mCountryDecsription?.extract?:"Extract  Not available", color = Color.Black, fontSize = 15.sp) }?:Text(text = "Data not available")

            mCountryDecsription?.let { Text(text = mCountryDecsription?.url?:"URL Not available",
                color = Color.Blue,
                fontSize = 15.sp,
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_VIEW, mCountryDecsription?.url?.toUri())
                    startActivity(intent)
            }) }?:Text(text = "Data not available")
        }

    }
}