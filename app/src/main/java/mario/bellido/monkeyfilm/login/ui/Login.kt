package mario.bellido.monkeyfilm.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import mario.bellido.monkeyfilm.User

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import mario.bellido.monkeyfilm.R
import mario.bellido.monkeyfilm.ui.screens.Screen
import mario.bellido.monkeyfilm.ui.theme.Gotham

var usuarios : ArrayList<User> = ArrayList()

fun usuarioDefault()
{
    usuarios.add(User("mario","mariobj1999@gmail.com","1234"))
    usuarios.add(User("admin","admin@gmail.com","admin"))
    usuarios.add(User("pepe","pepe@gmail.com","1234"))
}

fun añadirUsuario(nickname: String, email: String, password: String)
{
    usuarios.add(User(nickname,email,password))
}

fun comprobarUsuario(nickname: String, password: String): Boolean {

    for (u in usuarios) {
        if (u.nickname == nickname && u.password == password) {
            return true
        }
    }
    return false
}

@Composable
fun Login(loginViewModel: LoginViewModel, navController: NavHostController)
{
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    val user : String by loginViewModel.user.observeAsState(initial = "")
    val password: String by loginViewModel.password.observeAsState(initial = "")

    Scaffold(
        scaffoldState = scaffoldState
    )
    {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(Color(0xFF121212))
                .fillMaxSize(),
        )
        {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier
            )

            Text(
                text = "INICIA SESIÓN",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = Gotham
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Disfruta de la mejor cartelera",
                color = Color(0xFFa6a6a6),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = Gotham
            )

            Spacer(modifier = Modifier.height(30.dp))

            /**Ingresar usuario*/
            Usuario(
                user = user,
                change = {loginViewModel.onLoginChanged(it, password)}
            )

            Spacer(modifier = Modifier.height(35.dp))

            /**Introducir contraseña*/
            Password(
                password = password,
                change = {loginViewModel.onLoginChanged(user,it)}
            )

            Spacer(modifier = Modifier.height(35.dp))

            /** Este boton envia los datos del usuario para comprobarlos */
            Login(user,password,navController,scaffoldState)

            Spacer(modifier = Modifier.height(35.dp))

            /** Este boton envia te redirecciona al register para registrar una nueva cuenta. */
            TextButton(
                onClick = {
                    navController.navigate(route = Screen.Register.route)
                }
            )
            {
                Column(horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Text(
                        text = "¿Aun no tienes cuenta?",
                        fontWeight = FontWeight.Bold,
                        fontFamily = Gotham,
                        color = Color(0xFFa6a6a6)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "REGISTRATE",
                        fontWeight = FontWeight.Bold,
                        fontFamily = Gotham,
                        color = Color(0xFFec2b47)
                    )
                }
            }
        }
    }
}

@Composable
fun Usuario(user: String, change: (String) -> Unit)
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
        value = user,
        onValueChange = { change(it) },
        placeholder = { Text(text = "Introduce tu usuario o email",fontWeight = FontWeight.Medium, fontFamily = Gotham, fontSize = 14.sp) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFF525157),
            textColor = Color(0xFFc8c7cb),
            placeholderColor = Color(0xFF83818a),
            focusedIndicatorColor = Color.Transparent,
            cursorColor = Color(0xFFec2b47)
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
fun Password(password: String, change: (String) -> Unit)
{
    var passwordVisible by rememberSaveable{mutableStateOf(false)}

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
        singleLine = true,
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
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(0xFF525157),
            textColor = Color(0xFFc8c7cb),
            placeholderColor = Color(0xFF83818a),
            focusedIndicatorColor = Color.Transparent,
            cursorColor = Color(0xFF1e1d21)
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .height(50.dp),
        textStyle = TextStyle(
            fontFamily = Gotham,
            fontWeight = FontWeight.Medium
        )
    )
}

@Composable
fun Login(
    user: String,
    password: String,
    navController: NavHostController,
    scaffoldState: ScaffoldState
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    Button(
        onClick = {

            if (comprobarUsuario(user, password)) {
                navController.navigate(Screen.Home.route/*route = Screen.Home.withArgs(user, password)*/)
            } else {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Error en login",
                        actionLabel = ""
                    )
                }
            }

        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFFec2b47)
        ),
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(50.dp)
    )
    {
        Text(
            text = "INICIA SESION",
            fontWeight = FontWeight.Bold,
            fontFamily = Gotham,
            color = Color.White
        )
    }
}