package com.cookpad.recipesharing.di

import com.cookpad.recipesharing.data.source.repository.food.FoodCollectionRepository
import com.cookpad.recipesharing.data.source.repository.food.FoodCollectionRepositoryImpl
import com.cookpad.recipesharing.data.source.repository.recipe.RecipeCollectionRepository
import com.cookpad.recipesharing.data.source.repository.recipe.RecipeCollectionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RecipeRepositoryModule {

    @Singleton
    @Binds
    fun bindFoodCollectionRepository(repository: FoodCollectionRepositoryImpl): FoodCollectionRepository

    @Singleton
    @Binds
    fun bindRecipeRepository(repository: RecipeCollectionRepositoryImpl): RecipeCollectionRepository

}
