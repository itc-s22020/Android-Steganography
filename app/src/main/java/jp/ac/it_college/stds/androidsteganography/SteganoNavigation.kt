package jp.ac.it_college.stds.androidsteganography

import android.graphics.Bitmap
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jp.ac.it_college.stds.androidsteganography.components.desing.GradientTopAppBar
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


@Composable
fun SteganoNavigation(
    navController: NavHostController = rememberNavController(),
) {
    var titleText by remember { mutableStateOf("") }
    var bm by remember {
        mutableStateOf(
            Bitmap.createBitmap(
                1,
                1,
                Bitmap.Config.ARGB_8888
            )
        )
    }
    var selectNum by remember { mutableIntStateOf(0) }
    var zipList: MutableList<Int> = remember { mutableListOf<Int>().toMutableList() }


    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Scaffold(
            topBar = {
                GradientTopAppBar(
                    titleText,
                    navController = navController,
                    titleText!="スタート画面"
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
                        onEncryptClick = { bitmap, intValue ->
                            bm = bitmap
                            selectNum = intValue
                            navController.navigate(Destinations.ENCRYPTION)
                        },
                        onDecryptClick = { bitmap, intValue ->
                            bm = bitmap
                            selectNum = intValue
                            navController.navigate(Destinations.DECRYPTION)
                        }
                    )
                }

                composable(Destinations.ENCRYPTION) {
                    titleText = "暗号画面"
                    EncryptScene(
                        bm = bm,
                        encSelect = selectNum,
                        onEncryptResult = {bitmap, intValue, mutableList ->
                            bm = bitmap
                            selectNum = intValue
                            zipList = mutableList
                            navController.navigate(Destinations.RESULT_ENC)
                        }
                    )
                }

                composable(Destinations.RESULT_ENC) {
                    titleText = "暗号化結果画面"
                    EncryptResultScene(
                        bm = bm,
                        encSelect = selectNum,
                        zipBitList = zipList,
                        onEndClick = { navController.navigate(Destinations.START) }
                    )
                }

                composable(Destinations.DECRYPTION) {
                    titleText = "復号化画面"
                    DecodeScene(
                        bm = bm,
                        decSelect = selectNum,
                        onDecodeClick = { navController.navigate(Destinations.START) }
                    )
                }
            }
        }
    }
}
