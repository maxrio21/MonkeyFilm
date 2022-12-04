package mario.bellido.monkeyfilm

import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName

data class PeliculaModel(
    @SerializedName("id")
    var id: Int,

    @SerializedName("title")
    var title: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("catel")
    @DrawableRes var cartel: Int,

    @SerializedName("score")
    var score: Int,

    @SerializedName("favourite")
    var favorite: Boolean = false,

    @SerializedName("genre")
    var genre: List<String>
){

}