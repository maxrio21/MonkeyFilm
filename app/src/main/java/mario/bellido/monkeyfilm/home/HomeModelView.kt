package mario.bellido.monkeyfilm.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mario.bellido.monkeyfilm.PeliculaModel
import mario.bellido.monkeyfilm.R
import mario.bellido.monkeyfilm.ui.screens.Screen

class HomeModelView
{
    private val _peliculas = MutableLiveData<MutableList<PeliculaModel>>()
    val peliculas = _peliculas

    fun getFavoritios() : IntArray
    {
        var favoritos = ArrayList<Int>()

        for (p in peliculas.value!!)
        {
            if (p.favorite)
            {
                favoritos.add(p.id)
            }
        }

        return favoritos.toIntArray()
    }

    init {
        val api = PeliculasAPI()
        CoroutineScope(Dispatchers.Main).launch {
            api.getPeliculas()
            _peliculas.value = api.itemsArray
        }
    }

    fun setFavorito(id: Int)
    {
        peliculas.value?.get(id -1)?.favorite = !peliculas.value?.get(id -1)?.favorite!!
    }

    fun getGeneros() : ArrayList<String>
    {
        val pelis = peliculas.value!!

        var generos = ArrayList<String>()

        for (p in pelis)
        {
            for (pg in p.genre)
            {
                if (!generos.contains(pg))
                {
                    generos.add(pg)
                }
            }
        }

        return generos
    }

    private val _buttonIconChange = MutableLiveData<Boolean>()
    val buttonIconChange : LiveData<Boolean> = _buttonIconChange

    fun onHomeButtonPress(navController : NavHostController){
        navController.navigate(Screen.Home.route)
    }

    fun onFAVButtonPress(navController: NavHostController)
    {
        var favs = ""

        for (id in getFavoritios())
        {
            favs += "${id},"
        }
        navController.navigate(route = Screen.Favourites.withArgs(favs))
    }
}
