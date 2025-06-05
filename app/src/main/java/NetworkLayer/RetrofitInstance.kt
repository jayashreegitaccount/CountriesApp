package NetworkLayer

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

     val retrofit by lazy {
        Retrofit.Builder().baseUrl("https://restcountries.com/v3.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountriesAPI :: class.java)
    }

     val countryDescriptionrequest by lazy {

        Retrofit.Builder().baseUrl("https://en.wikipedia.org/api/rest_v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountryDescriptionAPI :: class.java)

    }


}