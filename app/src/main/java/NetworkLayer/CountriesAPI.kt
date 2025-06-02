package NetworkLayer

import retrofit2.Call
import retrofit2.http.GET

interface CountriesAPI {
    @GET("all")
    fun getAllCountries(): Call<List<Countries>>
}