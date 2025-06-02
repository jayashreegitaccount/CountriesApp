package com.jayashree.countriesinfo

import NetworkLayer.CountryDecsription
import NetworkLayer.RetrofitInstance
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.core.net.toUri

class KnowMoreAboutCountryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_know_more_about_country)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val intent = intent
        val selectedCountry = intent.getStringExtra("CountryName")?:"default"
        RetrofitInstance.countryDescriptionrequest.getCountryDescription(selectedCountry).enqueue(
            object : Callback<CountryDecsription> {
                override fun onResponse(
                    call: Call<CountryDecsription>,
                    response: Response<CountryDecsription>
                ) {
                    if(response.isSuccessful){
                        val countryDescription = response.body()
                        val title = countryDescription?.title
                        val summary = countryDescription?.description + " " + countryDescription?.extract
                        val titleTextview:TextView = findViewById(R.id.title_text)
                        titleTextview.setText(title)
                        val summaryTextView : TextView = findViewById(R.id.summary_text)
                        summaryTextView.setText(summary)
                        val urlTextView : TextView = findViewById(R.id.url_text)
                        urlTextView.paintFlags = urlTextView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                        urlTextView.setText(countryDescription?.url)
                        urlTextView.setOnClickListener{
                            val intent = Intent(Intent.ACTION_VIEW, countryDescription?.url?.toUri())
                            startActivity(intent)
                        }

                    }
                }

                override fun onFailure(call: Call<CountryDecsription>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

    }
}