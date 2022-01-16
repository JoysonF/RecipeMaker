package com.cookpad.recipesharing.data.source.remote.api

import com.cookpad.recipesharing.model.RecipeContent
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Api Service to fetch all recipe Content .
 */
interface RecipeApiService {

    @Headers("Content-Type: application/json")
    @GET("collections/")
    suspend fun getAllCollections(): List<RecipeContent>
}
