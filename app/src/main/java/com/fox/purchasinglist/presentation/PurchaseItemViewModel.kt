package com.fox.purchasinglist.presentation

import android.app.Application
import androidx.lifecycle.*
import com.fox.purchasinglist.data.repository.PurchaseListRepositoryImpl
import com.fox.purchasinglist.domain.*
import kotlinx.coroutines.*
import javax.inject.Inject

class PurchaseItemViewModel @Inject constructor(
    private val getPurchaseItemUseCase: GetPurchaseItemUseCase,
    private val editPurchaseItemUseCase: EditPurchaseItemUseCase,
    private val addPurchaseItemUseCase: AddPurchaseItemUseCase
) : ViewModel() {

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _purchaseItem = MutableLiveData<PurchaseItem>()
    val purchaseItem: LiveData<PurchaseItem>
        get() = _purchaseItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen


    fun getPurchaseItem(purchaseItemId: Int) {
        viewModelScope.launch {
            val item = getPurchaseItemUseCase(purchaseItemId)
            _purchaseItem.value = item
        }
    }

    fun addPurchaseItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValidate = validateInput(name, count)
        if (fieldsValidate) {
            viewModelScope.launch {
                val purchaseItem = PurchaseItem(name, count, true)
                addPurchaseItemUseCase(purchaseItem)
                finishWork()
            }

        }
    }

    fun editPurchaseItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValidate = validateInput(name, count)
        if (fieldsValidate) {
            _purchaseItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(name = name, count = count)
                    editPurchaseItemUseCase(item)
                    finishWork()
                }
            }
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }

}