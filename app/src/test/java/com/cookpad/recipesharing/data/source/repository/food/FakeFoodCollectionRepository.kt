package com.cookpad.recipesharing.data.source.repository.food

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cookpad.recipesharing.MainCoroutineRule
import com.cookpad.recipesharing.data.source.remote.api.RecipeApiService
import com.cookpad.recipesharing.model.food.FoodContent
import io.mockk.MockKAnnotations
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

import org.mockito.Mockito
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class FakeFoodCollectionRepository {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    //System under Test
    private lateinit var foodCollectionRepository: FoodCollectionRepository

    private val recipeApiService: RecipeApiService = mock()

    private val successResponse = listOf(
        FoodContent(
            description = "DEsc",
            id = 1,
            previewImageUrls = listOf("", ""),
            recipe_count = 2,
            title = "Title"
        )
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        foodCollectionRepository = FoodCollectionRepositoryImpl(recipeApiService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when getAllCollections called return success`() = runBlockingTest {
        Mockito.`when`(recipeApiService.getAllCollections()).thenReturn(successResponse)
        val response = foodCollectionRepository.getRecipeCollection()
        Mockito.verify(recipeApiService).getAllCollections()
        assertNotNull(response)
    }
}
