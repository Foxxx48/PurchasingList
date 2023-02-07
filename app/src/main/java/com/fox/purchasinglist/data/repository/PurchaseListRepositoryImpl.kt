package com.fox.purchasinglist.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.fox.purchasinglist.data.database.PurchaseListDao
import com.fox.purchasinglist.data.mapper.PurchaseListMapper
import com.fox.purchasinglist.domain.PurchaseItem
import com.fox.purchasinglist.domain.PurchaseListRepository
import javax.inject.Inject

class PurchaseListRepositoryImpl @Inject constructor(
    private val purchaseListDao: PurchaseListDao,
    private val mapper: PurchaseListMapper

) : PurchaseListRepository {

    override suspend fun addPurchaseItem(purchaseItem: PurchaseItem) {
        purchaseListDao.addPurchaseItem(mapper.mapEntityToDbModel(purchaseItem))

    }

    override suspend fun deletePurchaseItem(purchaseItem: PurchaseItem) {
        purchaseListDao.deletePurchaseItem(purchaseItem.id)
    }

    override suspend fun editPurchaseItem(purchaseItem: PurchaseItem) {
        purchaseListDao.addPurchaseItem(mapper.mapEntityToDbModel(purchaseItem))
    }

    override suspend fun getPurchaseItem(purchaseItemId: Int): PurchaseItem {
        val dbModel = purchaseListDao.getPurchaseItem(purchaseItemId)
        return mapper.mapDbModelToEntity(dbModel)

    }

    override fun getListPurchaseItem(): LiveData<List<PurchaseItem>> = Transformations.map(
        purchaseListDao.getPurchaseList()
    ) {
        mapper.mapListDbModelToListEntity(it)
    }
}


