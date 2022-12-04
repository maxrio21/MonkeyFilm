package mario.bellido.monkeyfilm.detailed_movie

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mario.bellido.monkeyfilm.PeliculaModel
import mario.bellido.monkeyfilm.home.PeliculasAPI

class DetailedMovieModelView
{
    private val _peliculas = MutableLiveData<MutableList<PeliculaModel>>()
    val peliculas = _peliculas

    init {
        val api = PeliculasAPI()
        CoroutineScope(Dispatchers.Main).launch {
            api.getPeliculas()
            _peliculas.value = api.itemsArray
        }
    }

    fun getPelicula(id: String): PeliculaModel
    {
        for(p in peliculas.value!!)
        {
            if (p.id.toString() == id)
            {
                return p
            }
        }
        return peliculas.value!!.get(0)
    }
}