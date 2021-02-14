package com.optus.employee.di

import androidx.lifecycle.ViewModel
import com.optus.employee.userinfo.UserInfoViewModel
import com.optus.employee.userinfolist.UserInfoListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserInfoListViewModel::class)
    internal abstract fun bindUserInfoListViewModel(viewModel: UserInfoListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserInfoViewModel::class)
    internal abstract fun bindUserInfoViewModel(viewModel: UserInfoViewModel): ViewModel
}