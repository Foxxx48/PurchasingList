package com.fox.purchasinglist.di

import android.app.Application
import android.content.ContentProvider
import com.fox.purchasinglist.data.mapper.PurchaseListMapper
import com.fox.purchasinglist.data.provider.PurchaseListProvider
import com.fox.purchasinglist.presentation.MainActivity
import com.fox.purchasinglist.presentation.PurchaseItemFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(provider: PurchaseListProvider)

    fun inject(fragment: PurchaseItemFragment)

    fun inject(mapper: PurchaseListMapper)

    @Component.Factory
    interface AppComponentFactory {
        fun create(@BindsInstance application: Application
        ): AppComponent
    }
}