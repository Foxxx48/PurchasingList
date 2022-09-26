package com.fox.purchasinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fox.purchasinglist.data.PurchaseListRepositoryImpl
import com.fox.purchasinglist.domain.AddPurchaseItemUseCase
import com.fox.purchasinglist.domain.EditPurchaseItemUseCase
import com.fox.purchasinglist.domain.GetPurchaseItemUseCase
import com.fox.purchasinglist.domain.PurchaseItem

class PurchaseItemViewModel(application: Application): AndroidViewModel(application) {

    private val repository = PurchaseListRepositoryImpl(application)

    private val getPurchaseItemUseCase= GetPurchaseItemUseCase(repository)
    private val addPurchaseItemUseCase = AddPurchaseItemUseCase(repository)
    private val editPurchaseItemUseCase = EditPurchaseItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _purchaseItem = MutableLiveData<PurchaseItem>()
    val purchaseItem: LiveData<PurchaseItem>
        get() =_purchaseItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen


    fun getPurchaseItem(purchaseItemId: Int) {
       val item = getPurchaseItemUseCase.getPurchase(purchaseItemId)
        _purchaseItem.value = item
    }

    fun addPurchaseItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValidate = validateInput(name, count)
        if (fieldsValidate) {
            val purchaseItem = PurchaseItem(name, count, true)
            addPurchaseItemUseCase.addPurchase(purchaseItem)
            finishWork()
        }

    }
    fun editPurchaseItem(inputName: String?, inputCount: String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValidate = validateInput(name, count)
        if (fieldsValidate) {
            _purchaseItem.value?.let {
                val item = it.copy(name = name, count = count)
                editPurchaseItemUseCase.editPurchase(item)
                finishWork()
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