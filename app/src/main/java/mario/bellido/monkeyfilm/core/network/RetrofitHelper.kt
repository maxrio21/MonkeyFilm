package mario.bellido.monkeyfilm.core.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofitHelper() : Retrofit {
        return Retrofit.Builder().baseUrl("https://run.mocky.io/").addConverterFactory(GsonConverterFactory.create()).build()
    }
}

object PeliculasRetrofit{
    fun getPeliculasRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/ba19d40a-9750-4413-bd70-9c6e703cc9e9/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}