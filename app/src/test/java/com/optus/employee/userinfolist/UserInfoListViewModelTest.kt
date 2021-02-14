package com.optus.employee.userinfolist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.then
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.optus.employee.model.UserInfo
import com.optus.employee.service.UserInfoService
import com.optus.employee.utils.Resource
import com.optus.employee.utils.RxImmediateSchedulerRule
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class UserInfoListViewModelTest {

    @Mock
    lateinit var userInfoService: UserInfoService

    @Mock
    private lateinit var usersObserver: Observer<Resource<List<UserInfo>>>

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()


    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    lateinit var viewModel: UserInfoListViewModel

    private val userName = "userName"
    private val userId = 1
    private val phone = "phone"
    private val email = "email"



    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        viewModel = UserInfoListViewModel(userInfoService)
    }

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        var userInfoList = createUserInfoListData()
        whenever(userInfoService.getUserInfosList()).thenReturn(Observable.just(userInfoList))
        viewModel.getUsers().observeForever(usersObserver)
        viewModel.fetchUserInfoList()
        then(verify(usersObserver).onChanged(Resource.success(userInfoList)))
        viewModel.getUsers().removeObserver(usersObserver)
    }


    @Test
    fun giverServerResponseError_whenFetch_shouldReturnError() {
        val errorMessage = "Something went wrong"
        whenever(userInfoService.getUserInfosList()).thenReturn(Observable.error(IOException(errorMessage)))
        viewModel.getUsers().observeForever(usersObserver)
        viewModel.fetchUserInfoList()
        then(verify(usersObserver).onChanged(Resource.loading(null)))
        then(verify(usersObserver).onChanged(Resource.error(errorMessage, null)))
        viewModel.getUsers().removeObserver(usersObserver)
    }

    private fun createUserInfoListData() : List<UserInfo> {

        var repositoryList = listOf<UserInfo>(
            UserInfo(userId, userName, phone, email)
        )
        return repositoryList
    }
}