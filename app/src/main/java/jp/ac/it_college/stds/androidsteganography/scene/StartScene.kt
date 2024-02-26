package jp.ac.it_college.stds.androidsteganography.scene

import android.graphics.Bitmap
import android.widget.Toast
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import jp.ac.it_college.stds.androidsteganography.components.common.ImagePickerView
import jp.ac.it_college.stds.androidsteganography.components.desing.ColorProgressBar
import jp.ac.it_college.stds.androidsteganography.ui.theme.AndroidSteganographyTheme
import jp.ac.it_college.stds.androidsteganography.ui.theme.mainGray
import jp.ac.it_college.stds.androidsteganography.ui.theme.mainWhite

@Composable
fun StartScene(modifier: Modifier = Modifier, onEncryptClick: (Bitmap, Int) -> Unit = { _, _ -> }, onDecryptClick: (Bitmap, Int) -> Unit = { _, _ -> })  {
    var bm by remember { mutableStateOf(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)) }
    val initialBitmap by remember { mutableStateOf(Bitmap.createBitmap(3500, 2200, Bitmap.Config.ARGB_8888)) }
    var selectNum: Int by remember { mutableIntStateOf(0) }
    var bool: Boolean by remember { mutableStateOf(false) }


    Column {
        Box(
            Modifier
                .background(mainWhite)
                .fillMaxWidth()
                .height(360.dp)
        ) {
            Box(modifier = Modifier
                .background(mainGray, shape = RoundedCornerShape(bottomEnd = 120f))
                .fillMaxWidth()
                .height(360.dp)
            ){
                Column{
                    Text(text = "詳細情報",  fontSize = 24.sp, modifier = Modifier.padding(10.dp,0.dp,0.dp,0.dp))
                    Row{
                        Spacer(modifier = Modifier.width(35.dp))
                        Column(Modifier
                            .height(120.dp)
                        ) {
                            Spacer(modifier = modifier.width(15.dp))
                            Text(text = "保存可能容量 : ${bm.width*bm.height*3/8000}KB (${bm.width*bm.height*3}bit)", fontSize = 18.sp)
                            Text(text = "埋込使用容量 : 0KB (0bit)", fontSize = 18.sp, modifier = Modifier.padding(0.dp,0.dp,0.dp,10.dp))
                            Text(text = "埋込方式 : ${
                                when (selectNum) {
                                    0 -> "simpleLSB"
                                    1 -> "QRcode"
                                    else -> "null"
                                }
                            }"

                                , fontSize = 18.sp)
                            Text(text = "埋込比率 : ${
                                when (selectNum) {
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
                            Text(text = "0bit<${bm.width*bm.height*3}bit",
                                Modifier
                                    .padding(0.dp, 0.dp, 0.dp, 4.dp)
                                    .align(Alignment.CenterHorizontally))
                            ColorProgressBar(
                                maxValue = bm.width*bm.height,
                                currentValue = 0,
                                modifier = Modifier,
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(60.dp)
                                .background(
                                    Color.Green,
                                    shape = RoundedCornerShape(200.dp)
                                )
                        )
                    }

                }
            }
        }

        Box(modifier = Modifier
            .background(mainGray)
            .fillMaxWidth()
            .fillMaxHeight()
        ) {
            Box(
                modifier = Modifier
                    .background(mainWhite, shape = RoundedCornerShape(topStart = 120f))
                    .fillMaxWidth()
                    .fillMaxHeight(),

                ) {

                Box(
                    modifier = Modifier
                        .background(mainGray)
                        .fillMaxWidth()
                        .height(160.dp)
                        .align(Alignment.BottomStart)
                ){
                    Row {
                        Box(
                            modifier = Modifier
                                .background(
                                    mainWhite,
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
                                .background(mainWhite)
                                .fillMaxWidth()
                                .fillMaxHeight()
                        ){
                            Box(
                                modifier = Modifier
                                    .background(
                                        mainGray,
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
                                    Button(
                                        modifier = Modifier
                                            .width(160.dp)
                                            .height(50.dp),
                                        onClick = { onEncryptClick(bm, selectNum) },
                                        enabled = bm.width + bm.height > 2 && bool,
                                        colors = ButtonDefaults.buttonColors(
                                            contentColor = Color.White,
                                            disabledContainerColor = Color.Gray
                                        )
                                    ) {
                                        Text("暗号化")
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
                                        onClick = { onDecryptClick(bm, selectNum) },
                                        enabled = bm.width + bm.height > 2 && bool,
                                        colors = ButtonDefaults.buttonColors(
                                            contentColor = Color.White,
                                            disabledContainerColor = Color.Gray
                                        )
                                    ) {
                                        Text("復号化")
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


        OutlinedCard(
            modifier = Modifier
                .width(350.dp)
                .height(220.dp)
                .align(Alignment.CenterHorizontally)
            ,
            border = BorderStroke(1.5.dp, Color.Gray),
            colors = CardDefaults.outlinedCardColors(

            )

            ) {
            Box(modifier.fillMaxSize()){
                Text(text = "画像選択", modifier.align(Alignment.Center), fontSize = 24.sp)
                ImagePickerView(
                    modifier = Modifier.align(Alignment.Center),
                    initialBitmap = initialBitmap,
                    context = LocalContext.current,
                    onBitmapChange = {bm = it}
                )
            }
        }

        selectNum = demoExposedDropdownMenuBox(
            onSelectionChange = {bool = it},
            selectPattern = arrayOf("simpleLSB", "QRcode"),
            defaultText = "方式選択"
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(1f),
        ) {


        }

    }
}


@Preview(showBackground = true)
@Composable
fun StartScenePreview() {
    AndroidSteganographyTheme {
        StartScene(Modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun demoExposedDropdownMenuBox(onSelectionChange: (Boolean) -> Unit, selectPattern: Array<String>, defaultText: String): Int {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(defaultText) }
    var selectIndex: Int by remember { mutableIntStateOf(-1) }
    val bool: Boolean by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 20.dp, 30.dp, 20.dp),
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                selectPattern.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                            onSelectionChange(!bool)
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
                selectIndex = selectPattern.indexOf(selectedText)
            }
        }
    }
    return selectIndex
}

