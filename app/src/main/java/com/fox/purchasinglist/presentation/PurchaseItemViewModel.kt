package com.fox.purchasinglist.presentation

import androidx.lifecycle.ViewModel
import com.fox.purchasinglist.data.PurchaseListRepositoryImpl
import com.fox.purchasinglist.domain.*
import java.lang.Exception as Exception

class PurchaseItemViewModel: ViewModel() {

    private val repository = PurchaseListRepositoryImpl

    private val getPurchaseItemUseCase= GetPurchaseItemUseCase(repository)
    private val addPurchaseItemUseCase = AddPurchaseItemUseCase(repository)
    private val editPurchaseItemUseCase = EditPurchaseItemUseCase(repository)

    fun getPurchaseItem(purchaseItemId: Int) {
       val item = getPurchaseItemUseCase.getPurchase(purchaseItemId)
    }

    fun addPurchaseItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValidate = validateInput(name, count)
        if (fieldsValidate) {
            val purchaseItem = PurchaseItem(name, count, true)
            addPurchaseItemUseCase.addPurchase(purchaseItem)
        }

    }
    fun editPurchaseItem(inputName: String?, inputCount: String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValidate = validateInput(name, count)
        if (fieldsValidate) {
            val purchaseItem = PurchaseItem(name, count, true)
            editPurchaseItemUseCase.editPurchase(purchaseItem)
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
            // TODO: show Error input name
            result = false
        }
        if (count <= 0) {
            // TODO: show Error input count
            result = false
        }
        return result
    }
}