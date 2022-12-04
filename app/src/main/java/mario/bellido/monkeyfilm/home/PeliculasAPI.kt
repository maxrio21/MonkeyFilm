package mario.bellido.monkeyfilm.home

import androidx.annotation.DrawableRes
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mario.bellido.monkeyfilm.PeliculaModel
import mario.bellido.monkeyfilm.core.network.PeliculasRetrofit

class PeliculasAPI {
    var itemsArray: ArrayList<PeliculaModel> = ArrayList()

    suspend fun getPeliculas() {
        val service = PeliculasRetrofit.getPeliculasRetrofit().create(PeliculasService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getPeliculas()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val items = response.body()

                    if (items != null) {
                        for (i in 0 until items.arrayPeliculas.count()) {
                            val id = items.arrayPeliculas[i].id
                            val title = items.arrayPeliculas[i].title
                            val cartel = items.arrayPeliculas[i].cartel
                            val desc = items.arrayPeliculas[i].description
                            val score = items.arrayPeliculas[i].score
                            val favourites = items.arrayPeliculas[i].favorite
                            val genre = items.arrayPeliculas[i].genre
                            itemsArray.add(PeliculaModel(id, title, desc, cartel, score, favourites, genre))
                        }
                    }
                }
            }
        }
    }
}
