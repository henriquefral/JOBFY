package br.com.fiap.job_fy

import MyFirebaseMessagingService
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
import br.com.fiap.job_fy.ui.theme.JOB_FYTheme
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.messaging.FirebaseMessaging
import com.sendbird.android.SendBird
import com.sendbird.android.SendBirdPushHelper


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SendBird.init("br.com.fiap.job_fy", this)

        SendBird.connect("br.com.fiap.job_fy") { user, e ->
            if (e != null) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            } else {
                SendBird.updateCurrentUserInfo("Henrique Freitas", null) { e ->
                    if (e != null) {
                        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                    }
                    finish()
                }
            }
        }

        SendBirdPushHelper.registerPushHandler(MyFirebaseMessagingService())

        SendBird.connect("sendbird_desk_agent_id_aaf19a01-b041-4ff0-abb9-f392f25af9e2"
        ) { user, sendBirdException ->
            if (sendBirdException != null) {
                Log.d(sendBirdException.message, sendBirdException.message.toString())
            }

            SendBirdPushHelper.registerPushHandler(MyFirebaseMessagingService())

            FirebaseMessaging.getInstance().token.addOnSuccessListener() {
                SendBird.registerPushTokenForCurrentUser(it,
                    SendBird.RegisterPushTokenWithStatusHandler{ status, sendBirdException ->
                        if (sendBirdException != null) {
                            Log.d(sendBirdException.message, sendBirdException.message.toString())
                        }
                })
            }
        }

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
                    }
                }
            }
        }
    }
}