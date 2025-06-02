package com.jayashree.countriesinfo

import NetworkLayer.CountryDecsription
import NetworkLayer.CountryDescriptionAPI
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.internal.runners.statements.Fail
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryDescriptionAPITest {

    @Test
    fun `getCountryDescription returns success`(){
        val mockAPI = mock(CountryDescriptionAPI::class.java)
        val mockCall = mock(Call::class.java) as Call<CountryDecsription>
        val mockResponse = CountryDecsription("title", "description", "extract", "url")
        `when`(mockAPI.getCountryDescription("title")).thenReturn(mockCall)
        doAnswer {
            val callback = it.getArgument<Callback<CountryDecsription>>(0)
            callback.onResponse(mockCall, Response.success(mockResponse))
            null

        }.`when`(mockCall).enqueue(any())


        mockAPI.getCountryDescription("title").enqueue(object : Callback<CountryDecsription>{
            override fun onResponse(
                call: Call<CountryDecsription>,
                response: Response<CountryDecsription>
            ) {
               assertEquals("title", response.body()?.title)
            }

            override fun onFailure(call: Call<CountryDecsription>, t: Throwable) {
                Fail(t)
            }

        })

    }

    @Test
    fun `getCountryDescription returns failure`(){
        val mockAPI = mock(CountryDescriptionAPI::class.java)
        val mockCall = mock(Call:: class.java) as Call<CountryDecsription>
        `when`(mockAPI.getCountryDescription("SomeCountry")).thenReturn(mockCall)
        doAnswer {
            val callback = it.getArgument<Callback<CountryDecsription>>(0)
            callback.onFailure(mockCall, Throwable("404 Not Found"))
            null
        }.`when`(mockCall).enqueue(any())

        mockAPI.getCountryDescription("SomeCountry").enqueue(object : Callback<CountryDecsription>{
            override fun onResponse(
                call: Call<CountryDecsription>,
                response: Response<CountryDecsription>
            ) {
                Fail(Throwable("Expected Failure but got success"))
            }

            override fun onFailure(call: Call<CountryDecsription>, t: Throwable) {
                assertEquals("404 Not Found", t.message)
            }

        })

    }
}