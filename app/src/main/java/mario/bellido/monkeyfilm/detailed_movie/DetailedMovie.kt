package mario.bellido.monkeyfilm.detailed_movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import mario.bellido.monkeyfilm.home.Reseña
import mario.bellido.monkeyfilm.ui.theme.Gotham

@Composable
fun DetailedMovie(id: String, modelView: DetailedMovieModelView, navController: NavHostController)
{
    Body(id, modelView)
}

@Composable
fun Body(id: String,modelView: DetailedMovieModelView)
{
    val pelicula = modelView.getPelicula(id)


    val context = LocalContext.current
    val imagen = "c" + pelicula.cartel

    val drawableId = remember(imagen){
        context.resources.getIdentifier(
            imagen,"drawable",context.packageName
        )
    }

    Box(modifier = Modifier.fillMaxSize())
    {
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = pelicula.description,
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        startY = 1300f,
                        endY = 0f,
                        colors = listOf(
                            Color(0xFF0e0f15),
                            Color.Transparent,
                        )
                    )
                )
                .padding(10.dp)
        )
        {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom)
            {

                LazyRow(
                ){
                    items(pelicula.genre) { item ->
                        Categoria(categoria = item)
                    }
                }
                /*
                Row() /**Cambiar por un LazyRow*/
                {
                    for (c in pelicula.genre)
                    {
                        Categoria(categoria = c)
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }

                 */
                
                Spacer(modifier = Modifier.height(10.dp))
                /**Titulo*/
                CustomText(texto = pelicula.title, fontSize = 20.sp, fontWeight = FontWeight.Bold, Color.White,Modifier)
                Spacer(modifier = Modifier.height(10.dp))
                /**Descripcion*/
                CustomText(texto = pelicula.description, fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Color(0xFFa6a6a6), modifier = Modifier, lineHeight = 20.sp)
                Spacer(modifier = Modifier.height(30.dp))

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