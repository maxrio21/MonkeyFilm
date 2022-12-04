package mario.bellido.monkeyfilm.login.domain

import mario.bellido.monkeyfilm.login.data.LoginRepository

class LoginUseCase()
{
    private val repository = LoginRepository()

    suspend operator fun invoke(user: String, password: String): Boolean {
        return repository.doLogin(user,password)
    }

}