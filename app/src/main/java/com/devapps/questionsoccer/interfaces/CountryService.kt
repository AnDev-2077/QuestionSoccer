package com.devapps.questionsoccer.interfaces

import com.devapps.questionsoccer.items.CountryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface CountryService {
    @Headers(
        //"x-rapidapi-key: 84bd0aa6dc133eb6b2fe0c8a336da534",
        //"x-rapidapi-key: 1d63a493af62503a34eee5919e8ea2cd",
        //"x-rapidapi-key: 177ba7ad773f514929d35cf2707fe5df",
        //"x-rapidapi-key: 12dd289570856da79353621a538056d4",
        //"x-rapidapi-key: 9136ddb976288d7ala0f9dcf148361df",
        "x-rapidapi-host: v3.football.api-sports.io"
    )
    @GET("teams/countries")
    suspend fun getCountries(): Response<CountryResponse>
}