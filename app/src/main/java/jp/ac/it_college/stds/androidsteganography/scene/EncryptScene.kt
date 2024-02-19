package jp.ac.it_college.stds.androidsteganography.scene

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.ac.it_college.stds.androidsteganography.components.common.FilePickerButton
import jp.ac.it_college.stds.androidsteganography.components.common.ImagePickerView
import jp.ac.it_college.stds.androidsteganography.components.desing.ColorProgressBar
import jp.ac.it_college.stds.androidsteganography.components.fileOperations.conversion.byteArrayToIntList
import jp.ac.it_college.stds.androidsteganography.components.steganography.colorChanger.QrCodeLSB
import jp.ac.it_college.stds.androidsteganography.components.steganography.colorChanger.SimpleLSB
import jp.ac.it_college.stds.androidsteganography.components.steganography.createSteganography
import jp.ac.it_college.stds.androidsteganography.ui.theme.AndroidSteganographyTheme

@Composable
fun EncryptScene(
    modifier: Modifier = Modifier,
    onEncryptResult: (Bitmap,Int,MutableList<Int> ) -> Unit = { _, _,_ -> },
    bm: Bitmap,
    encSelect: Int
) {
    var zipBitList by remember { mutableStateOf(mutableListOf(0)) }
    val context = LocalContext.current
    var zipByte: ByteArray? by remember { mutableStateOf(null) }
    var sBm: Bitmap by remember { mutableStateOf(bm.copy(Bitmap.Config.ARGB_8888, true))}
    var text by remember { mutableStateOf("sample") }

    if(encSelect!=1) zipBitList = byteArrayToIntList(zipByte)

    Column {
        Box(
            Modifier
                .background(jp.ac.it_college.stds.androidsteganography.ui.theme.mainWhite)
                .fillMaxWidth()
                .height(360.dp)
        ) {
            Box(modifier = Modifier
                .background(
                    jp.ac.it_college.stds.androidsteganography.ui.theme.mainGray,
                    shape = RoundedCornerShape(bottomEnd = 120f)
                )
                .fillMaxWidth()
                .height(360.dp)
            ){
                Column {
                    Text(text = "詳細情報",  fontSize = 24.sp, modifier = Modifier.padding(10.dp,0.dp,0.dp,0.dp))
                    Row{
                        Spacer(modifier = Modifier.width(35.dp))
                        Column(Modifier
                            .height(120.dp)
                        ) {
                            Spacer(modifier = modifier.width(15.dp))
                            Text(text = "保存可能容量 : ${sBm.width*sBm.height*3/8000}KB (${sBm.width*sBm.height*3}bit)", fontSize = 18.sp)
                            Text(text = "埋込使用容量 : ${zipBitList.size/8000}KB (${zipBitList.size}bit)", fontSize = 18.sp, modifier = Modifier.padding(0.dp,0.dp,0.dp,10.dp))
                            Text(text = "埋込方式 : ${
                                when (encSelect) {
                                    0 -> "simpleLSB"
                                    1 -> "QRcode"
                                    else -> "null"
                                }
                            }", fontSize = 18.sp)
                            Text(text = "埋込比率 : ${
                                when (encSelect) {
                                    0 -> "1pixel = 3bit"
                                    1 -> "特殊"
                                    else -> "null"
                                }
                            }"
                                , fontSize = 18.sp)
                        }
                        Spacer(modifier = modifier.width(10.dp))
                    }

                    Row(
                        Modifier
                            .height(60.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom
                    )
                    {
                        Column {
                            Text(text = "${zipBitList.size}bit<${sBm.width*sBm.height*3}bit",
                                Modifier
                                    .padding(0.dp, 0.dp, 0.dp, 4.dp)
                                    .align(Alignment.CenterHorizontally))
                            ColorProgressBar(
                                maxValue = sBm.width*sBm.height*3,
                                currentValue = zipBitList.size,
                                modifier = Modifier,
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(60.dp)
                                .background(
                                    if (sBm.width * sBm.height * 3 > zipBitList.size) Color.Green else Color.Red,
                                    shape = RoundedCornerShape(200.dp)
                                )
                        )
                    }

                }
            }
        }

        Box(modifier = Modifier
            .background(jp.ac.it_college.stds.androidsteganography.ui.theme.mainGray)
            .fillMaxWidth()
            .fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .background(
                        jp.ac.it_college.stds.androidsteganography.ui.theme.mainWhite,
                        shape = RoundedCornerShape(topStart = 120f)
                    )
                    .fillMaxWidth()
                    .fillMaxHeight(),

                ) {

                Box(
                    modifier = Modifier
                        .background(jp.ac.it_college.stds.androidsteganography.ui.theme.mainGray)
                        .fillMaxWidth()
                        .height(160.dp)
                        .align(Alignment.BottomStart)
                ){
                    Row {
                        Box(
                            modifier = Modifier
                                .background(
                                    jp.ac.it_college.stds.androidsteganography.ui.theme.mainWhite,
                                    shape = RoundedCornerShape(bottomEnd = 260f)
                                )
                                .width(180.dp)
                                .fillMaxHeight()
                        ) {
                            Button(
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(120.dp)
                                    .align(Alignment.Center),
                                onClick = { /*TODO*/ },
                                enabled = false,
                            ) {
                                Icon(Icons.Default.Settings, contentDescription = "test")
                            }
                        }

                        Box(
                            modifier = Modifier
                                .background(jp.ac.it_college.stds.androidsteganography.ui.theme.mainWhite)
                                .fillMaxWidth()
                                .fillMaxHeight()
                        ){
                            Box(
                                modifier = Modifier
                                    .background(
                                        jp.ac.it_college.stds.androidsteganography.ui.theme.mainGray,
                                        shape = RoundedCornerShape(topStart = 200f)
                                    )
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            ){
                                Column(modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    if (encSelect!=1) {
                                        FilePickerButton(
                                            modifier = Modifier
                                                .width(160.dp)
                                                .height(50.dp),
                                            textModifier = Modifier,
                                            btText = "ファイル選択",
                                            context = context,
                                            onFilesChange = {zipByte = it}
                                        )
                                    } else {
                                        Button(
                                            modifier = Modifier
                                                .width(160.dp)
                                                .height(50.dp),
                                            onClick = {
                                                val bmHW = if(bm.width >= bm.height) bm.height else bm.width
                                                zipBitList = QrCodeLSB.generateQR(text,bmHW,bmHW,"H")
                                            }) {
                                            Text(text = "QRコード生成")
                                        }
                                    }

                                    Spacer(modifier = Modifier
                                        .width(30.dp)
                                        .height(20.dp)
                                    )

                                    Button(
                                        modifier = Modifier
                                            .width(160.dp)
                                            .height(50.dp)
                                        ,
                                        enabled = zipBitList.size < sBm.width*sBm.height*3  && zipBitList.size > 10,
                                        onClick = {
                                            when (encSelect) {
                                                0 -> sBm = bm.createSteganography(zipBitList, (SimpleLSB::create))
                                                1 -> sBm = bm.createSteganography(zipBitList, (QrCodeLSB::create))
                                            }
                                            onEncryptResult(sBm, encSelect, zipBitList)
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            contentColor = Color.White,
                                            disabledContainerColor = Color.Gray
                                        )
                                    ) {
                                        Text("結果画面へ")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(250.dp))



        Column(
            modifier = Modifier.fillMaxSize()
        ) {


            OutlinedCard(
                modifier = Modifier
                    .width(350.dp)
                    .height(220.dp)
                    .align(Alignment.CenterHorizontally),
                border = BorderStroke(1.5.dp, Color.Gray),
                colors = CardDefaults.outlinedCardColors(

                )

            ) {
                Box(modifier.fillMaxSize()) {
                    Text(text = "画像選択", modifier.align(Alignment.Center), fontSize = 24.sp)
                    ImagePickerView(
                        modifier = Modifier.align(Alignment.Center),
                        initialBitmap = bm,
                        context = LocalContext.current,
                        onBitmapChange = { sBm = it }
                    )
                }
            }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp, 20.dp, 30.dp, 20.dp),
                value = text,
                enabled = encSelect == 1,
                onValueChange = { text = it },
                label = { Text("埋込文字列") }
            )
        }



        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(1f),
        ) {


        }

    }
}

@Preview(showBackground = true)
@Composable
fun EncryptScenePreview() {
    AndroidSteganographyTheme {
        EncryptScene(
            Modifier,
            bm = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
            encSelect = 0
        )
    }
}