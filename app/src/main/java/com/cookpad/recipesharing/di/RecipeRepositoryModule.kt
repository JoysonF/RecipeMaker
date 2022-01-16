package com.cookpad.recipesharing.di

import com.cookpad.recipesharing.data.source.repository.RecipeCollectionRepository
import com.cookpad.recipesharing.data.source.repository.RecipeCollectionRepositoryImpl
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
    fun bindRecipeRepository(repository: RecipeCollectionRepositoryImpl): RecipeCollectionRepository

}


/*
@InstallIn(SingletonComponent::class)
@Module
interface RecipeRepositoryModule {

    @Singleton
    @Binds
    fun bindRecipeRepository(repository: RecipeCollectionRepositoryImpl): RecipeCollectionRepository
}
*/
