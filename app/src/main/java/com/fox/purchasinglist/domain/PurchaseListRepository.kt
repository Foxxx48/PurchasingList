package com.fox.purchasinglist.domain

import androidx.lifecycle.LiveData

interface PurchaseListRepository {

     fun addPurchaseItem(purchaseItem: PurchaseItem)

     fun deletePurchaseItem(purchaseItem: PurchaseItem)

     fun editPurchaseItem(purchaseItem: PurchaseItem)

     fun getPurchaseItem(purchaseItemId: Int): PurchaseItem

     fun getListPurchaseItem(): LiveData<List<PurchaseItem>>
}