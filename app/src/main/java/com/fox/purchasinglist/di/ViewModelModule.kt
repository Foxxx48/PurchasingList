package com.fox.purchasinglist.di

import androidx.lifecycle.ViewModel
import com.fox.purchasinglist.presentation.MainViewModel
import com.fox.purchasinglist.presentation.PurchaseItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(impl: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PurchaseItemViewModel::class)
    fun bindPurchaseItemViewModel(impl: PurchaseItemViewModel): ViewModel
}