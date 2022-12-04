package mario.bellido.monkeyfilm.login.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mario.bellido.monkeyfilm.login.domain.LoginUseCase
import mario.bellido.monkeyfilm.ui.screens.Screen

class LoginViewModel : ViewModel()
{
    private val loginUseCase = LoginUseCase()

    private val _user = MutableLiveData<String>()
    val user = _user

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _isButtonLoginEnable = MutableLiveData<Boolean>()
    val isButtonLoginEnable : LiveData<Boolean> = _isButtonLoginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _forgottenEmail = MutableLiveData<String>()
    val forgottenEmail = _forgottenEmail

    fun onLoginChanged(user : String, password : String){
        _user.value = user
        _password.value = password
        _isButtonLoginEnable.value = validEmail(user) && validPassword(password)
    }

    fun passwordForgotten(user : String){
        _forgottenEmail.value = user
    }

    fun validEmail(user : String) : Boolean = Patterns.EMAIL_ADDRESS.matcher(user).matches()

    private fun validPassword(password: String) : Boolean = password.length > 5

    fun onButtonLoginPress(navController : NavHostController){
        viewModelScope.launch {
            _isLoading.value = true
            delay(4000)
            val result = loginUseCase(user.value!!, password.value!!)
            if(result){
                Log.i("DAM", "Se ha completado el login")
                navController.navigate(Screen.Home.route)
            }
            _isLoading.value = false

        }
    }
}