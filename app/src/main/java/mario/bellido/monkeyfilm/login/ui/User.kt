package mario.bellido.monkeyfilm

class User(nickname : String, email: String, password: String)
{
    val nickname : String
    val email : String
    val password : String

    init {
        this.nickname = nickname
        this.email = email
        this.password = password
    }
}