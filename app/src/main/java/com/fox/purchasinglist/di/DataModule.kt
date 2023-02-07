package com.fox.purchasinglist.di

import android.app.Application
import com.fox.purchasinglist.data.database.AppDatabase
import com.fox.purchasinglist.data.database.PurchaseListDao
import com.fox.purchasinglist.data.repository.PurchaseListRepositoryImpl
import com.fox.purchasinglist.domain.PurchaseListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @AppScope
    fun bindsPurchaseListRepository(impl: PurchaseListRepositoryImpl) : PurchaseListRepository

    companion object {
        @Provides
        @AppScope
        fun providesPurchaseItemDao(application: Application): PurchaseListDao {
            return AppDatabase.getInstance(application).purchaseListDao()
        }

    }

}