package com.optus.employee.userinfo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.then
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.optus.employee.model.UserDetail
import com.optus.employee.model.UserInfo
import com.optus.employee.service.UserInfoService
import com.optus.employee.userinfolist.UserInfoListViewModel
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
class UserInfoViewModelTest {

    @Mock
    lateinit var userInfoService: UserInfoService

    @Mock
    private lateinit var userObserver: Observer<Resource<List<UserDetail>>>

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()


    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    lateinit var viewModel: UserInfoViewModel

    private val userName = "userName"
    private val userId = "1"
    private val albumId = 1
    private val title = "title"
    private val url = "url"
    private val thumbNailUrl = "thumbNailurl"


    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        viewModel = UserInfoViewModel(userInfoService)
    }

    @Test
    fun givenServerResponse200_whenFetch_AndFilter_shouldReturnSuccess() {
        val userDetailList = createUserDetailListData()
        whenever(userInfoService.getUserDetail()).thenReturn(Observable.just(userDetailList))
        viewModel.getUser().observeForever(userObserver)
        viewModel.fetchUserDetail(userId = 1)
        then(verify(userObserver).onChanged(Resource.loading(null)))
        val expectedUserDetailList = listOf<UserDetail>(
            userDetailList.get(0),
            userDetailList.get(1)
        )
        then(verify(userObserver).onChanged(Resource.success(expectedUserDetailList)))
        viewModel.getUser().removeObserver(userObserver)
    }


    @Test
    fun giverServerResponseError_whenFetch_shouldReturnError() {
        val errorMessage = "Something went wrong"
        whenever(userInfoService.getUserDetail()).thenReturn(
            Observable.error(
                IOException(
                    errorMessage
                )
            )
        )
        viewModel.getUser().observeForever(userObserver)
        viewModel.fetchUserDetail(userId = 1)
        then(verify(userObserver).onChanged(Resource.loading(null)))
        then(verify(userObserver).onChanged(Resource.error(errorMessage, null)))
        viewModel.getUser().removeObserver(userObserver)
    }

    private fun createUserDetailListData(): List<UserDetail> {

        var userDetailList = listOf<UserDetail>(
            UserDetail(albumId, userId, title, url, thumbNailUrl),
            UserDetail(albumId, userId, "diffTitle", "diffUrl", "diffThumbNail"),
            UserDetail(2, userId, title, url, thumbNailUrl),
            UserDetail(3, "3", "diffTitle", "diffUrl", "diffThumbNail")
        )
        return userDetailList
    }
}