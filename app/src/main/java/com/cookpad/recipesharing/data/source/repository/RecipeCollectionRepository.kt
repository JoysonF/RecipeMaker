package com.cookpad.recipesharing.data.source.repository

import com.cookpad.recipesharing.data.source.remote.api.RecipeApiService
import com.cookpad.recipesharing.model.RecipeContent
import javax.inject.Inject

interface RecipeCollectionRepository {
    suspend fun getRecipeCollection(): List<RecipeContent>
}

class RecipeCollectionRepositoryImpl @Inject constructor(
    private val recipeApiService: RecipeApiService
) : RecipeCollectionRepository {

    override suspend fun getRecipeCollection(): List<RecipeContent> {
        return recipeApiService.getAllCollections()
    }
}
