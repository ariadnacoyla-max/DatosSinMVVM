package com.example.datossinmvvm

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import kotlinx.coroutines.launch

@Composable
fun ScreenUser() {

    val context = LocalContext.current

    var id by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    val dataUser = remember { mutableStateOf("") }

    val db = remember {
        crearDatabase(context)
    }

    val dao = db.userDao()
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // IMAGEN DE FONDO
        Image(
            painter = painterResource(
                id = R.drawable.gatito_fondo
            ),
            contentDescription = "Gatito de fondo",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )

        // CAPA TRANSPARENTE
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.White.copy(alpha = 0.08f)
                )
        )

        // FORMULARIO
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
                .padding(top = 45.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // TÍTULO
            Text(
                text = "♡ Usuarios ♡",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8754D1)
            )

            Text(
                text = "Registro de usuarios",
                fontSize = 14.sp,
                color = Color(0xFF674099)
            )

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            // ID
            TextField(
                value = id,
                onValueChange = { id = it },
                label = {
                    Text("ID (solo lectura)")
                },
                readOnly = true,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor =
                        Color.White.copy(alpha = 0.92f),

                    unfocusedContainerColor =
                        Color.White.copy(alpha = 0.88f),

                    disabledContainerColor =
                        Color.White.copy(alpha = 0.88f),

                    focusedIndicatorColor =
                        Color.Transparent,

                    unfocusedIndicatorColor =
                        Color.Transparent
                )
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // FIRST NAME
            TextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                },
                label = {
                    Text("First Name")
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor =
                        Color.White.copy(alpha = 0.92f),

                    unfocusedContainerColor =
                        Color.White.copy(alpha = 0.88f),

                    focusedIndicatorColor =
                        Color(0xFF9B6AD6),

                    unfocusedIndicatorColor =
                        Color.Transparent
                )
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // LAST NAME
            TextField(
                value = lastName,
                onValueChange = {
                    lastName = it
                },
                label = {
                    Text("Last Name")
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(18.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor =
                        Color.White.copy(alpha = 0.92f),

                    unfocusedContainerColor =
                        Color.White.copy(alpha = 0.88f),

                    focusedIndicatorColor =
                        Color(0xFF9B6AD6),

                    unfocusedIndicatorColor =
                        Color.Transparent
                )
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // BOTÓN AGREGAR
            Button(
                onClick = {

                    if (
                        firstName.isNotBlank() &&
                        lastName.isNotBlank()
                    ) {

                        val user = User(
                            uid = 0,
                            firstName = firstName,
                            lastName = lastName
                        )

                        coroutineScope.launch {

                            AgregarUsuario(
                                user = user,
                                dao = dao
                            )

                            // Actualizar lista
                            dataUser.value = getUsers(dao)
                        }

                        firstName = ""
                        lastName = ""
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF9259D1)
                )
            ) {

                Text(
                    text = "♡  Agregar Usuario",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // BOTÓN LISTAR
            Button(
                onClick = {

                    coroutineScope.launch {

                        dataUser.value =
                            getUsers(dao)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD5B2F4)
                )
            ) {

                Text(
                    text = "☰  Listar Usuarios",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF58307D)
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // BOTÓN ELIMINAR ÚLTIMO USUARIO
            Button(
                onClick = {

                    coroutineScope.launch {

                        dao.deleteLast()

                        // Actualiza la lista
                        dataUser.value = getUsers(dao)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB388D4)
                )
            ) {

                Text(
                    text = "✕  Eliminar último usuario",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // RESULTADOS
            if (dataUser.value.isNotEmpty()) {

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    color = Color.White.copy(alpha = 0.88f)
                ) {

                    Text(
                        text = dataUser.value,
                        modifier = Modifier.padding(12.dp),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF58307D),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

// CREAR BASE DE DATOS

fun crearDatabase(
    context: Context
): UserDatabase {

    return Room.databaseBuilder(
        context.applicationContext,
        UserDatabase::class.java,
        "user_db"
    ).build()
}

// LISTAR USUARIOS

suspend fun getUsers(
    dao: UserDao
): String {

    var rpta = ""

    val users = dao.getAll()

    users.forEach { user ->

        rpta +=
            "♡ ${user.firstName} ${user.lastName}\n"
    }

    return rpta
}

// AGREGAR USUARIO

suspend fun AgregarUsuario(
    user: User,
    dao: UserDao
) {

    try {

        dao.insert(user)

    } catch (e: Exception) {

        Log.e(
            "User",
            "Error insert: ${e.message}"
        )
    }
}
