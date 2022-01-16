package com.cookpad.recipesharing.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookpad.recipesharing.R
import com.cookpad.recipesharing.data.source.repository.RecipeCollectionRepository
import com.cookpad.recipesharing.model.RecipeContent
import dagger.hilt.android.lifecycle.HiltViewModel
import com.cookpad.recipesharing.data.Result
import com.cookpad.recipesharing.di.IoDispatcher
import com.cookpad.recipesharing.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RecipeCollectionRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _recipeCollection = MutableLiveData<Result<List<RecipeContent>>>()
    val recipeCollection: LiveData<Result<List<RecipeContent>>>
        get() = _recipeCollection


    /**
     * Used for one time events to notify the views ..like network errors / api errors
     */
    private val actionEvent: SingleLiveEvent<Int> = SingleLiveEvent()

    fun getErrorMsg(): SingleLiveEvent<Int> {
        return actionEvent
    }

    init {
        getRecipeCollection()
    }

    fun getRecipeCollection() {
        viewModelScope.launch(dispatcher) {
            _recipeCollection.postValue(Result.Loading)
            runCatching {
                val recipeCollection = repository.getRecipeCollection()
                _recipeCollection.postValue(Result.Success(recipeCollection))
            }.onFailure {
                _recipeCollection.postValue(it.message?.let { msg ->
                    Result.Error(msg)
                })
                actionEvent.postValue(handleError(it))
            }
        }
    }

    /**
     * Handle exceptions
     */
    private fun handleError(e: Throwable): Int = when (e) {
        is IOException -> R.string.error_no_connection
        else -> R.string.error_server
    }

}
