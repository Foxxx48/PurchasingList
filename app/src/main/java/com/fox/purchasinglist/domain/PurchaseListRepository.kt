package com.fox.purchasinglist.domain

import androidx.lifecycle.LiveData

interface PurchaseListRepository {

     suspend fun addPurchaseItem(purchaseItem: PurchaseItem)

     suspend fun deletePurchaseItem(purchaseItem: PurchaseItem)

     suspend fun editPurchaseItem(purchaseItem: PurchaseItem)

     suspend fun getPurchaseItem(purchaseItemId: Int): PurchaseItem

     fun getListPurchaseItem(): LiveData<List<PurchaseItem>>
}