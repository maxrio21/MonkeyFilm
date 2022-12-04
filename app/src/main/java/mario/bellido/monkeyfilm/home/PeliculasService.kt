package mario.bellido.monkeyfilm.home

import retrofit2.Response
import retrofit2.http.GET

interface PeliculasService {
    @GET("https://run.mocky.io/v3/ba19d40a-9750-4413-bd70-9c6e703cc9e9")
    suspend fun getPeliculas(): Response<PeliculasResponse>
}