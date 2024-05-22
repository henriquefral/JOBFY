package br.com.fiap.job_fy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.job_fy.login_.LoginScreen
import br.com.fiap.job_fy.menu.MenuScreen
import br.com.fiap.job_fy.model.Usuario
import br.com.fiap.job_fy.register.RegisterScreen
import br.com.fiap.job_fy.search.SearchScreen
import br.com.fiap.job_fy.ui.theme.JOB_FYTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JOB_FYTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        composable(route = "login")    { LoginScreen(navController) }
                        composable(route = "register") { RegisterScreen(navController) }
                        composable(route = "menu")     {
                            val id = navController.previousBackStackEntry
                                        ?.savedStateHandle?.get<Int>("id")

                            val nome = navController.previousBackStackEntry
                                        ?.savedStateHandle?.get<String>("nome")

                            val usuario = Usuario(id = id!!, nome = nome!!)

                            MenuScreen(navController, usuario)
                        }
                        composable(route = "search") {

                            val id = navController.previousBackStackEntry
                                ?.savedStateHandle?.get<Int>("id")

                            val nome = navController.previousBackStackEntry
                                ?.savedStateHandle?.get<String>("nome")

                            val usuario = Usuario(id = id!!, nome = nome!!)

                            SearchScreen(navController, usuario)
                        }
                    }
                }
            }
        }
    }
}