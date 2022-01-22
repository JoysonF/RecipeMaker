package com.cookpad.recipesharing.data.source.repository.recipe

import com.cookpad.recipesharing.data.source.remote.api.RecipeApiService
import com.cookpad.recipesharing.model.recipe.Recipe
import javax.inject.Inject

interface RecipeCollectionRepository {
    suspend fun getAllRecipes(collectionId: String): List<Recipe>
    suspend fun getRecipe(recipeId: String): Recipe
}

class RecipeCollectionRepositoryImpl @Inject constructor(
    private val recipeApiService: RecipeApiService
) : RecipeCollectionRepository {

    override suspend fun getAllRecipes(collectionId: String): List<Recipe> {
        return recipeApiService.getAllRecipes(collectionId)
    }

    override suspend fun getRecipe(recipeId: String): Recipe {
        return recipeApiService.getRecipe(recipeId)
    }
}
