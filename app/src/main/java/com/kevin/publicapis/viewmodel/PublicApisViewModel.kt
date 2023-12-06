package com.kevin.publicapis.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.publicapis.model.APiRepository
import com.kevin.publicapis.model.Entry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PublicApisViewModel @Inject constructor(private val aPiRepository: APiRepository) :
    ViewModel() {

    private val _entries = MutableLiveData<List<Entry>>()
    val entries: LiveData<List<Entry>>
        get() = _entries


    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error


    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading


    fun getEntries() {
        viewModelScope.launch {
            try {
                _loading.postValue(true)
                val res = aPiRepository.getEntries()

                if (res.isSuccessful) {
                    _loading.postValue(false)
                    res.body()?.let {
                        _entries.postValue(it.entries)
                    }

                } else {
                    _loading.postValue(false)
                    _error.postValue("Some error")
                }
            } catch (e: Exception) {
                _loading.postValue(false)
                _error.postValue("Some error")
            }
        }
    }

}