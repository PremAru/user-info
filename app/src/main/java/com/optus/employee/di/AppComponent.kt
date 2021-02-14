package com.optus.employee.di

import android.content.Context
import com.optus.employee.interceptor.ForceCacheInterceptor
import com.optus.employee.userinfolist.UserInfoListComponent
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, NetworkModule::class, ViewModelFactoryModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun repositoriesListComponent() : UserInfoListComponent.Factory

    fun forceCacheInterceptor() : ForceCacheInterceptor
}