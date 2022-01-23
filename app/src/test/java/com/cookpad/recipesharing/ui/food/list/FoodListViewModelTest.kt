package com.cookpad.recipesharing.ui.food.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cookpad.recipesharing.MainCoroutineRule
import com.cookpad.recipesharing.data.source.repository.food.FoodCollectionRepository
import com.cookpad.recipesharing.getOrAwaitValue
import com.cookpad.recipesharing.model.food.FoodContent
import io.mockk.MockKAnnotations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.mockito.Mockito
import org.mockito.kotlin.mock
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class FoodListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var foodListViewModel: FoodListViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    private val repository = mock<FoodCollectionRepository>()

    private val listOfFoodContent = listOf(
        FoodContent(
            description = "DEsc",
            id = 1,
            previewImageUrls = listOf("", ""),
            recipe_count = 2,
            title = "Title"
        )
    )

    private val successResponse = com.cookpad.recipesharing.data.Result.Success(listOfFoodContent)

    private val failureResponse = "Something went wrong. Please try again later."


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        foodListViewModel = FoodListViewModel(repository, testDispatcher)
    }

    @Test
    fun `when getRecipeCollection called, return success`() =
        mainCoroutineRule.runBlockingTest {
            Mockito.`when`(repository.getRecipeCollection()).thenReturn(listOfFoodContent)
            foodListViewModel.getRecipeCollection()
            Assert.assertEquals(foodListViewModel.foodCollection.getOrAwaitValue(), successResponse)
        }

    @Test
    fun `when getRecipeCollection called, return failure`() =
        mainCoroutineRule.runBlockingTest {
            Mockito.`when`(repository.getRecipeCollection()).thenThrow(RuntimeException())
            foodListViewModel.getRecipeCollection()
            Assert.assertEquals(foodListViewModel.getErrorMsg().getOrAwaitValue(), failureResponse)
        }


    @After
    internal fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}
