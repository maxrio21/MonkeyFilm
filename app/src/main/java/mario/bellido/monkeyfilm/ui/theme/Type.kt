package mario.bellido.monkeyfilm.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import mario.bellido.monkeyfilm.R

val Gotham = FontFamily(
    Font(R.font.gotham_bold, FontWeight.Bold),
    Font(R.font.gotham_book, FontWeight.Normal),
    Font(R.font.gotham_light, FontWeight.Light),
    Font(R.font.gotham_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.gotham_book_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.gotham_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.gotham_medium, FontWeight.Medium),
    Font(R.font.gotham_medium_italic, FontWeight.Medium, FontStyle.Italic),
    )

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)