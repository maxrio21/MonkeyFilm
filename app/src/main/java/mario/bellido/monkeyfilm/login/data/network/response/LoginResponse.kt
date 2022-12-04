package mario.bellido.monkeyfilm.login.data.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(@SerializedName("ok") val okLogin : Boolean)
