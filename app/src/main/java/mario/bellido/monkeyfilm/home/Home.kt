package mario.bellido.monkeyfilm.home

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mario.bellido.monkeyfilm.ui.screens.Screen
import mario.bellido.monkeyfilm.ui.theme.Gotham


@Composable
fun Home(
    //user: String?,
    //password: String?,
    modelView: HomeModelView,
    navController: NavHostController
) {
    val enabledButton by modelView.buttonIconChange.observeAsState(false)

    Scaffold(
        bottomBar = {BottomBar(modelView,navController) },
        floatingActionButton = {FlotaingAddMovie(){modelView.onFAVButtonPress(navController)} }
    )
    {

        Column(
            Modifier
                .background(Color(0xFF1a1a1a))
                .fillMaxSize())
        {
            /* DATOS DEL USUARIO
            Text(text = "Usuario: $user")
            Text(text = "Contraseña $password")*/

            Seccion("Prueba",modelView, navController = navController)

        }

    }
}
@Composable
fun Seccion(titulo: String,modelView: HomeModelView, navController: NavHostController)
{

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(15.dp))
    {
        for (i in modelView.getGeneros())
        {
            Spacer(modifier = Modifier.height(15.dp))
            Row(verticalAlignment = Alignment.CenterVertically)
            {
                Box(
                    Modifier
                        .height(30.dp)
                        .width(4.dp)
                        .background(Color(0xFFec2b47))
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = i,
                    color = Color.White,
                    fontSize = 30.sp,
                    fontFamily = Gotham,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            LazyRow(){
                items(modelView.peliculas.value!!) { item ->
                    if (item.genre.contains(i))
                    {
                        Reseña(title = item.title, cartel = item.cartel, score = item.score, id = item.id, navController = navController, modelView = modelView)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
fun Reseña(title: String, cartel: Int, score: Int, id: Int, navController: NavHostController, modelView: HomeModelView)
{
    var changeIcon by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    val imagen = "c" + cartel
    val drawableId = remember(imagen){
        context.resources.getIdentifier(
            imagen,"drawable",context.packageName
        )
    }

    Card(
        modifier = Modifier
            .size(width = 160.dp, height = 210.dp)
            .padding(horizontal = 5.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(bounded = true, color = Color.Black),
                onClick = { navController.navigate(route = Screen.DetailedMovie.withArgs(id.toString())) }
            ),
        backgroundColor = Color(0xFF1a1a1a),
        elevation = 12.dp,
        shape = RoundedCornerShape(6.dp)

    ) {
        Box(modifier = Modifier.fillMaxSize())              /**Aqui metemos la imagen*/
        {
            Image(
                painter = painterResource(id = drawableId),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(6.dp))
            )
            Box(                                            /**Aquí le aplicamos un degradado*/
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            startY = 500f,
                            endY = 0f,
                            colors = listOf(
                                Color(0xFF262626),
                                Color.Transparent,
                            )
                        )
                    )
                    .padding(10.dp)
            )
            {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom
                ) {

                    /**Titulo de la pelicula*/
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        color = Color.White,
                        fontFamily = Gotham,
                        fontWeight = FontWeight.Normal
                    )

                    /**Creamos una fila para añadir al mismo sitio la puntuacion y el boton para añadir a favoritos*/
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                    )
                    {

                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "",
                            tint = Color(0xFFec2b47),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.weight(0.05f))
                        Text(
                            text = score.toString(),
                            fontSize = 16.sp,
                            color = Color.White,
                            fontFamily = Gotham,
                            fontWeight = FontWeight.Medium
                        )

                        val icono = if (changeIcon) Icons.Filled.Star else Icons.Filled.StarBorder
                            
                        Spacer(modifier = Modifier.weight(1f))
                        
                        Icon(
                            imageVector = icono,
                            contentDescription = "",
                            tint = Color(0xFFead341),
                            modifier = Modifier
                                .size(16.dp)
                                .clickable(onClick = {
                                    modelView.peliculas.value?.get(id - 1)?.favorite =
                                        !modelView.peliculas.value?.get(id - 1)?.favorite!!



                                    changeIcon = !changeIcon
                                })
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FlotaingAddMovie(onFavClick: () -> Unit)
{
    FloatingActionButton(
        onClick = {  }
    )
    {
        Icon(imageVector = Icons.Filled.MovieCreation, contentDescription = "Crear pelicula")
    }
}

@Composable
fun BottomBar(modelView: HomeModelView,navController: NavHostController)
{

    BottomNavigation(backgroundColor = Color(0xFF262626))
    {
        BottomNavigationItem(
            selected = true,
            onClick = { modelView.onHomeButtonPress(navController) },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = ""
                )
            },
            selectedContentColor = Color(0xFFec2b47),
            unselectedContentColor = Color(0xFFa6a6a6),
            label = {Text(text = "HOME", fontFamily = Gotham, fontWeight = FontWeight.Medium)
            }
        )
        BottomNavigationItem(
            selected = false,
            onClick = { modelView.onFAVButtonPress(navController) },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = ""
                )
            },
            selectedContentColor = Color(0xFFec2b47),
            unselectedContentColor = Color(0xFFa6a6a6),
            label = {Text(text = "FAVORITOS", fontFamily = Gotham, fontWeight = FontWeight.Medium)}
        )
    }


}
