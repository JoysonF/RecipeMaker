package com.cookpad.recipesharing.data.source.repository.food

import com.cookpad.recipesharing.data.source.remote.api.RecipeApiService
import com.cookpad.recipesharing.model.food.FoodContent
import javax.inject.Inject

interface FoodCollectionRepository {
    suspend fun getRecipeCollection(): List<FoodContent>
}

class FoodCollectionRepositoryImpl @Inject constructor(
    private val recipeApiService: RecipeApiService
) : FoodCollectionRepository {

    override suspend fun getRecipeCollection(): List<FoodContent> {
        return recipeApiService.getAllCollections()
    }
}
