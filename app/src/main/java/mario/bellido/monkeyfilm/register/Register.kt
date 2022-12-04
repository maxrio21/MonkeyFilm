package mario.bellido.monkeyfilm.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import mario.bellido.monkeyfilm.R
import mario.bellido.monkeyfilm.ui.theme.Gotham


@Composable
fun Register(modelView: RegisterModelView, navController: NavHostController)
{
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    val usuario by modelView.user.observeAsState("")
    val email by modelView.email.observeAsState("")
    val password by modelView.password.observeAsState("")
    val confirmPassword by modelView.confirmPassword.observeAsState("")

    val enabledButton by modelView.isButtonRegisterEnable.observeAsState(false)
    val isLoading by modelView.isLoading.observeAsState(false)

    Scaffold(scaffoldState = scaffoldState)
    {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color(0xFF1e1d21))
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        )
        {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier.height(150.dp)
            )

            Text(
                text = "REGISTRATE",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = Gotham
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Crea tu cuenta y disfruta de monkeyfilm",
                color = Color(0xFFa6a6a6),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = Gotham
            )

            Spacer(modifier = Modifier.height(20.dp))

            //Campo usuario
            Usuario(
                modelView,
                usuario = usuario) {
                modelView.validRegister(
                    it,
                    password,
                    confirmPassword,
                    email) }

            Spacer(modifier = Modifier.height(20.dp))

            //Campo correo
            Correo(
                modelView,
                email = email,
                change = { modelView.validRegister(usuario, password, confirmPassword, it) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            //Campo contraseña
            CamposContrasenya(
                modelView,
                password,
                confirmPassword,
                { modelView.validRegister(usuario, it, confirmPassword, email) }) {
                modelView.validRegister(usuario, password, it, email)
            }

            Spacer(modifier = Modifier.height(30.dp))

            /* Este boton envia los datos del usuario para comprobarlos */

            Registrate(enabledButton) { modelView.registerButtonPress(navController) }
            if (isLoading) {
                LinearProgressIndicator(
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 30.dp),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun Usuario(modelView: RegisterModelView, usuario: String, change: (String) -> Unit)
{
    Text(
        text = "USUARIO",
        color = Color.White,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = Gotham,
        modifier = Modifier
            .fillMaxWidth(0.715f),
        textAlign = TextAlign.Left
    )

    Spacer(modifier = Modifier.height(8.dp))

    TextField(
        value = usuario,
        onValueChange = { change(it) },
        placeholder = { Text(text = "Introduce tu usuario",fontWeight = FontWeight.Medium, fontFamily = Gotham, fontSize = 14.sp) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFF525157),
            textColor = Color(0xFFc8c7cb),
            placeholderColor = Color(0xFF83818a),
            focusedIndicatorColor = fieldIsValidColor(modelView.validUser(usuario)),
            cursorColor = Color(0xFF1e1d21)
        ),
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.height(50.dp),
        textStyle = TextStyle(
            fontFamily = Gotham,
            fontWeight = FontWeight.Medium
        )
    )
}

@Composable
fun Correo(modelView: RegisterModelView,email: String, change: (String) -> Unit)
{
    Text(
        text = "EMAIL",
        color = Color.White,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = Gotham,
        modifier = Modifier
            .fillMaxWidth(0.715f),
        textAlign = TextAlign.Left
    )

    Spacer(modifier = Modifier.height(8.dp))

    TextField(
        value = email,
        onValueChange = { change(it) },
        placeholder = { Text(text = "Introduce tu email",fontWeight = FontWeight.Medium, fontFamily = Gotham, fontSize = 14.sp) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFF525157),
            textColor = Color(0xFFc8c7cb),
            placeholderColor = Color(0xFF83818a),
            focusedIndicatorColor = fieldIsValidColor(modelView.validEmail(email)),
            cursorColor = Color(0xFF1e1d21)
        ),
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.height(50.dp),
        textStyle = TextStyle(
            fontFamily = Gotham,
            fontWeight = FontWeight.Medium
        )
    )
}

/** Composables*/

@Composable
fun CamposContrasenya(
    modelView: RegisterModelView,
    password: String,
    repeatPassword: String,
    change: (String) -> Unit,
    confirmChange: (String) -> Unit
) {

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }

    Text(
        text = "CONTRASEÑA",
        color = Color.White,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = Gotham,
        modifier = Modifier
            .fillMaxWidth(0.715f),
        textAlign = TextAlign.Left
    )

    Spacer(modifier = Modifier.height(8.dp))

    TextField(
        value = password,
        onValueChange = { change(it) },
        placeholder = { Text(text = "Introduce tu contraseña",fontWeight = FontWeight.Medium, fontFamily = Gotham, fontSize = 14.sp) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFF525157),
            textColor = Color(0xFFc8c7cb),
            placeholderColor = Color(0xFF83818a),
            focusedIndicatorColor = fieldIsValidColor(modelView.validPassword(password)),
            cursorColor = Color(0xFF1e1d21)
        ),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {

            val icono =
                if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val descripcion =
                if (passwordVisible) "Ocultar constraseña" else "Mostrar contraseña"

            IconButton(onClick = { passwordVisible = !passwordVisible })
            {
                Icon(imageVector = icono, contentDescription = descripcion, tint = Color.White)
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.height(50.dp),
        textStyle = TextStyle(
            fontFamily = Gotham,
            fontWeight = FontWeight.Medium
        )
    )

    Spacer(modifier = Modifier.height(20.dp))

    Text(
        text = "CONFIRMA CONTRASEÑA",
        color = Color.White,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = Gotham,
        modifier = Modifier
            .fillMaxWidth(0.715f),
        textAlign = TextAlign.Left
    )

    Spacer(modifier = Modifier.height(8.dp))

    //Campo repetir contraseña
    TextField(
        value = repeatPassword,
        onValueChange = { confirmChange(it) },
        placeholder = { Text(text = "Repite tu contraseña",fontWeight = FontWeight.Medium, fontFamily = Gotham, fontSize = 14.sp) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFF525157),
            textColor = Color(0xFFc8c7cb),
            placeholderColor = Color(0xFF83818a),
            focusedIndicatorColor = fieldIsValidColor(modelView.validateSecondPassword(password,repeatPassword)),
            cursorColor = Color(0xFF1e1d21)
        ),
        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {

            val icono =
                if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val descripcion =
                if (confirmPasswordVisible) "Ocultar constraseña" else "Mostrar contraseña"

            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible })
            {
                Icon(imageVector = icono, contentDescription = descripcion, tint = Color.White)
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.height(50.dp),
        textStyle = TextStyle(
            fontFamily = Gotham,
            fontWeight = FontWeight.Medium
        )
    )
}


@Composable
fun Registrate(enabledButton: Boolean, onButtonSelected: () -> Unit) {

    Button(
        onClick = { onButtonSelected() }, modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            backgroundColor = Color(0xFFec2b47),
            disabledBackgroundColor = Color.White,
            disabledContentColor = Color.Black
        ),
        enabled = enabledButton
    )
    {
        Text(
            text = "REGISTRATE",
            fontWeight = FontWeight.Bold,
            fontFamily = Gotham
        )
    }
}

fun fieldIsValidColor(valid: Boolean): Color {

    if (valid) {
        return Color.White
    }
    return Color(0xFFf27d68)
}



