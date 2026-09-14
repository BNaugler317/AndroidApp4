package com.example.androidapp4.data

import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesApi {
    @GET("search")
    suspend fun searchPodcast(
        @Query("term") term: String,
        @Query("media") media: String = "podcast"
    ): PodcastResponse
}