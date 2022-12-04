package mario.bellido.monkeyfilm.home

import com.google.gson.annotations.SerializedName
import mario.bellido.monkeyfilm.PeliculaModel

data class PeliculasResponse(@SerializedName("success") val arrayPeliculas: ArrayList<PeliculaModel>)