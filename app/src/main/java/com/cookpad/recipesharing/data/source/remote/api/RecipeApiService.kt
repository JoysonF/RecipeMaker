package com.cookpad.recipesharing.data.source.remote.api

import com.cookpad.recipesharing.model.food.FoodContent
import com.cookpad.recipesharing.model.recipe.Recipe
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Api Service to fetch food info.
 */
interface RecipeApiService {

    @GET("collections/")
    suspend fun getAllCollections(): List<FoodContent>

    @GET("collections/{id}/recipes/")
    suspend fun getAllRecipes(
        @Path("id") id: String
    ): List<Recipe>

    @GET("recipes/{recipe-id}/")
    suspend fun getRecipe(
        @Path("recipe-id") id: String
    ): Recipe
}
