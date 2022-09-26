package com.fox.purchasinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.fox.purchasinglist.domain.PurchaseItem
import com.fox.purchasinglist.domain.PurchaseListRepository

class PurchaseListRepositoryImpl(
    application: Application
) : PurchaseListRepository {

    private val purchaseListDao = AppDatabase.getInstance(application).purchaseListDao()

    private val mapper = PurchaseListMapper()

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
//        MediatorLiveData<List<PurchaseItem>>().apply {
//        addSource(purchaseListDao.getPurchaseList()) {
//            value = mapper.mapListDbModelToListEntity(it)
//        }
}


