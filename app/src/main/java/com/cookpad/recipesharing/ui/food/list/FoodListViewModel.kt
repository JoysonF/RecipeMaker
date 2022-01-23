package com.cookpad.recipesharing.ui.food.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookpad.recipesharing.R
import com.cookpad.recipesharing.data.source.repository.food.FoodCollectionRepository
import com.cookpad.recipesharing.model.food.FoodContent
import dagger.hilt.android.lifecycle.HiltViewModel
import com.cookpad.recipesharing.data.Result
import com.cookpad.recipesharing.di.IoDispatcher
import com.cookpad.recipesharing.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(
    private val repository: FoodCollectionRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _foodCollection = MutableLiveData<Result<List<FoodContent>>>()
    val foodCollection: LiveData<Result<List<FoodContent>>>
        get() = _foodCollection

    /**
     * Used for one time events to notify the views ..like network errors / api errors
     */
    private val actionEvent: SingleLiveEvent<String> = SingleLiveEvent()

    fun getErrorMsg(): SingleLiveEvent<String> {
        return actionEvent
    }

    init {
        getRecipeCollection()
    }

    fun getRecipeCollection() {
        viewModelScope.launch(dispatcher) {
            _foodCollection.postValue(Result.Loading)
            runCatching {
                val recipeCollection = repository.getRecipeCollection()
                _foodCollection.postValue(Result.Success(recipeCollection))
            }.onFailure {
                _foodCollection.postValue(it.message?.let { msg ->
                    Result.Error(msg)
                })
                actionEvent.postValue(handleError(it))
            }
        }
    }

    /**
     * Handle exceptions
     */
    private fun handleError(e: Throwable): String = when (e) {
        is IOException -> "No Internet Connection. Please check your internet connection."
        else -> "Something went wrong. Please try again later."
    }

}
