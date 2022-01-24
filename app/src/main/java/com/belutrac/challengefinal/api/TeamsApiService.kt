package com.belutrac.challengefinal.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface TeamsApiService{
    @GET("search_all_teams.php?s=Soccer&c=Argentina")
    suspend fun getTeams()
}

private var retrofit = Retrofit.Builder()
    .baseUrl("https://www.thesportsdb.com/api/v1/json/2/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

var service: TeamsApiService = retrofit.create(TeamsApiService::class.java)