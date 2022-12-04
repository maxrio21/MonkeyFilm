package mario.bellido.monkeyfilm.login.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mario.bellido.monkeyfilm.core.network.RetrofitHelper

class LoginService {
    private val retrofit = RetrofitHelper.getRetrofitHelper()

    suspend fun doLogin(user: String, password: String): Boolean{
        return withContext(Dispatchers.IO){
            val response= retrofit.create(LoginClient::class.java).doLogin()
            response.body()?.okLogin ?:false
        }
    }
}