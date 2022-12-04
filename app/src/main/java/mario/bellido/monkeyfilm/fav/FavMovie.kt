package mario.bellido.monkeyfilm.fav

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import mario.bellido.monkeyfilm.PeliculaModel
import mario.bellido.monkeyfilm.ui.screens.Screen
import mario.bellido.monkeyfilm.ui.theme.Gotham

@Composable
fun FavMovie(peliculas: String, modelView: FavModelView, navController: NavHostController)
{
    Body(peliculas,modelView,navController)
}

@Composable
fun Body(peliculas: String,modelView: FavModelView, navController: NavHostController)
{
    var indicesPeliculas = peliculas.split(",")

    var peliculas = ArrayList<PeliculaModel>()
    peliculas.removeLast()

    for (p in indicesPeliculas)
    {
        peliculas.add(modelView.peliculas.value!!.get(p.toInt()-1))
    }

    Box(modifier = Modifier.fillMaxSize())
    {
        LazyRow(){
            items(peliculas) { item ->

                Reseña(title = item.title, cartel = item.cartel, score = item.score, id = item.id, navController = navController, modelView = modelView)

            }
        }
    }
}

@Composable
fun Reseña(title: String, cartel: Int, score: Int, id: Int, navController: NavHostController, modelView: FavModelView)
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
        modifier = Modifier.fillMaxSize()
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
fun CustomText(
    texto: String,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight,
    color: Color,
    modifier: Modifier,
    lineHeight: TextUnit = TextUnit.Unspecified
) {

    Text(
        text = texto,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontFamily = Gotham,
        color = color,
        modifier = modifier,
        lineHeight = lineHeight
    )
}

@Composable
fun Categoria(categoria: String)
{
    Card(backgroundColor = Color(0xFF1f222d), shape = RoundedCornerShape(10.dp))
    {
        CustomText(texto = categoria, fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color.White,Modifier.padding(5.dp))
    }
}