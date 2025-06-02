package com.jayashree.countriesinfo

import NetworkLayer.Countries
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CountryListAdapter(private val countryList:List<Countries>):RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {
    private var context:Context? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryListAdapter.CountryViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.countries_item_view,parent,false)
        return CountryViewHolder(view)

    }

    override fun onBindViewHolder(holder: CountryListAdapter.CountryViewHolder, position: Int) {
        val country = countryList[position]
        val capitalText = "Capital: ${country.capital?.firstOrNull()?:"NA"}"
        holder.name.setText("Name: ${country.name.common}")
        holder.capital.setText(capitalText)
        holder.region.setText("Region: ${country.region}")
        holder.population.setText("Population: ${country.population}")
        Glide.with(holder.flag.context)
            .load(country.flags.png)
            .centerCrop()
            .into(holder.flag)
        holder.knowMore.paintFlags = holder.knowMore.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        holder.knowMore.setOnClickListener {
            Log.d("ClickTest", "Know More clicked")
            val intent = Intent(context, KnowMoreAboutCountryActivity::class.java)
            intent.putExtra("CountryName", country.name.common)
            context?.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    class CountryViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.country_name_text)
        val capital: TextView = view.findViewById(R.id.country_capital_text)
        val region: TextView = view.findViewById(R.id.country_region_text)
        val population:TextView = view.findViewById(R.id.country_popuplation_text)
        val flag : ImageView = view.findViewById(R.id.country_flag)
        val knowMore : TextView = view.findViewById(R.id.know_more_button)

    }
}