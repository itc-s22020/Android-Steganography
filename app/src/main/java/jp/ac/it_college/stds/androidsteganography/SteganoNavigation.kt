package jp.ac.it_college.stds.androidsteganography

import android.graphics.Bitmap
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.ac.it_college.stds.androidsteganography.scene.DecodeScene
import jp.ac.it_college.stds.androidsteganography.scene.EncryptResultScene
import jp.ac.it_college.stds.androidsteganography.scene.EncryptScene
import jp.ac.it_college.stds.androidsteganography.scene.StartScene

object Destinations {
    const val START = "start"
    const val ENCRYPTION = "encryption"
    const val DECRYPTION = "decryption"
    const val RESULT_ENC = "result_enc"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SteganoNavigation(
    navController: NavHostController = rememberNavController(),
) {
    var titleText by remember { mutableStateOf("") }
    var testBitmap by remember {
        mutableStateOf(
            Bitmap.createBitmap(
                1,
                1,
                Bitmap.Config.ARGB_8888
            )
        )
    }
    var showText by remember { mutableStateOf(false) }



    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val items = listOf(
        "Inbox",
        "Starred",
        "Sent",
        "Drafts",
        "Settings"
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(

            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    title = { Text(text = titleText) },
                    navigationIcon = {
                        if (titleText != "スタート画面") {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = { showText = true }) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
        ) {
            NavHost(
                navController = navController,
                startDestination = Destinations.START,
                modifier = Modifier.padding(it)
            ) {
                composable(Destinations.START) {
                    titleText = "スタート画面"
                    StartScene(
                        onEncryptClick = {
                            testBitmap = it
                            navController.navigate(Destinations.ENCRYPTION)
                        },
                        onDecryptClick = {
                            testBitmap = it
                            navController.navigate(Destinations.DECRYPTION)
                        }
                    )
                }

                composable(Destinations.ENCRYPTION) {
                    titleText = "暗号化画面"
                    EncryptScene(
                        receive = testBitmap,
                        onEncryptResult = {
                            testBitmap = it
                            navController.navigate(Destinations.RESULT_ENC)
                        }
                    )
                }

                composable(Destinations.RESULT_ENC) {
                    titleText = "暗号化結果画面"
                    EncryptResultScene(
                        receive2 = testBitmap,
                        onEndClick = { navController.navigate(Destinations.START) }
                    )
                }

                composable(Destinations.DECRYPTION) {
                    titleText = "復号化画面"

                    DecodeScene(
                        decReceive = testBitmap,
                        onDecodeClick = { navController.navigate(Destinations.START) }
                    )
                }
            }
        }
    }

}