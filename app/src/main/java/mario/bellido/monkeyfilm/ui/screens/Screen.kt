package mario.bellido.monkeyfilm.ui.screens

sealed class Screen (val route: String)
{
    object Login: Screen("login_screen")
    object Register: Screen("register_screen")
    object Home: Screen("home_screen")
    object Favourites: Screen("favourites_screen")
    object AddMovie: Screen("addmovie_screen")
    object DetailedMovie: Screen("detailedmovie_screen")

    fun withArgs(vararg args: String) : String
    {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}