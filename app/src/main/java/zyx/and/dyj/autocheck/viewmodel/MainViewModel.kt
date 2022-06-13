package zyx.and.dyj.autocheck.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import zyx.and.dyj.autocheck.api.Repository
import zyx.and.dyj.autocheck.data.StuData
import zyx.and.dyj.autocheck.data.XXTResult

class MainViewModel : ViewModel() {
    var getUserList by mutableStateOf(Repository.getUser())
    fun getUser(): MutableList<StuData> {
        getUserList = Repository.getUser()
        return Repository.getUser()
    }

    fun saveUser(stuData: StuData): Boolean {
        getUserList = Repository.getUser()
        return Repository.addUser(stuData)
    }

    fun delUser(stuData: StuData) {
        Repository.delUser(stuData)
        getUserList = Repository.getUser()
    }

    private val _resData = MutableStateFlow(listOf(XXTResult("", false)))
    val resData = _resData.asStateFlow()
    fun startCheck() {
        viewModelScope.launch {
            Repository.loopGetCookie().collect {
                _resData.value = it
            }
        }
    }

}