package NetworkLayer

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryDescriptionAPI {
    @GET("all")
    fun getAboutCountry(): Call<CountryDecsription>

    @GET("page/summary/{countryName}")
    fun getCountryDescription(@Path("countryName") countryName: String): Call<CountryDecsription>

}