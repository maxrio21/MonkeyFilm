package mario.bellido.monkeyfilm.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import mario.bellido.monkeyfilm.detailed_movie.DetailedMovie
import mario.bellido.monkeyfilm.detailed_movie.DetailedMovieModelView
import mario.bellido.monkeyfilm.login.ui.Login
import mario.bellido.monkeyfilm.home.Home
import mario.bellido.monkeyfilm.home.HomeModelView
import mario.bellido.monkeyfilm.register.Register
import mario.bellido.monkeyfilm.login.ui.LoginViewModel
import mario.bellido.monkeyfilm.login.ui.usuarioDefault
import mario.bellido.monkeyfilm.register.RegisterModelView
import mario.bellido.monkeyfilm.fav.FavModelView
import mario.bellido.monkeyfilm.fav.FavMovie

@Composable
fun Navigation() {

    val navController = rememberNavController()
    val login = LoginViewModel()
    val register = RegisterModelView()
    val home = HomeModelView()
    val detailedmovie = DetailedMovieModelView()
    val fav = FavModelView()

    NavHost(navController = navController, startDestination = Screen.Login.route)
    {
        composable(route = Screen.Login.route)
        {
            usuarioDefault()
            Login(login,navController)
        }
        composable( //Screen de la pantalla Home
            route = Screen.Home.route
            /*
            route = Screen.Home.route + "/{user}/{password}",
            arguments = listOf(
                navArgument("user")
                {
                    type = NavType.StringType
                    defaultValue = "guest"
                    nullable = false
                },
                navArgument("password")
                {
                    type = NavType.StringType
                    defaultValue = "1234"
                    nullable = false
                }
            )*/
        )
        {
            Home(home,navController)
        }
        /*
        {
            entry ->
            Home(user = entry.arguments?.getString("user"), password = entry.arguments?.getString("password"),home,navController)
        }
        */

        composable(route = Screen.Register.route)
        {
            Register(register,navController = navController)
        }

        composable(
            route = Screen.DetailedMovie.route + "/{id}",
            arguments = listOf(
                navArgument("id")
                {
                    type = NavType.StringType
                    defaultValue = "1"
                    nullable = false
                }
            )
        )
        {
            entry ->
            entry.arguments?.getString("id")
                ?.let { DetailedMovie(id = it,detailedmovie,navController = navController) }
        }

        composable(
            route = Screen.Favourites.route + "/{peliculas}",
            arguments = listOf(
                navArgument("peliculas")
                {
                    type = NavType.StringType
                    defaultValue = "1"
                    nullable = false
                }
            )
        )
        {
            entry ->
            entry.arguments?.getString("peliculas")
                ?.let { FavMovie(peliculas = it,fav, navController = navController) }
        }
    }
}