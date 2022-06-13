package zyx.and.dyj.autocheck.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.sharp.Build
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.blankj.utilcode.util.ToastUtils
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import zyx.and.dyj.autocheck.data.StuData
import zyx.and.dyj.autocheck.theme.ActionBarColor
import zyx.and.dyj.autocheck.theme.mainColor
import zyx.and.dyj.autocheck.theme.sakuraTitle
import zyx.and.dyj.autocheck.theme.xiamulv
import zyx.and.dyj.autocheck.viewmodel.MainViewModel

@Composable
fun mainScreen(mainViewModel: MainViewModel = viewModel()) {
    var userList = mainViewModel.getUserList
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(mainColor, false)
    }
    val lazyState = rememberLazyListState()
    var showDialog by remember {
        mutableStateOf(false)
    }
    val response = mainViewModel.resData.collectAsState().value


    var name by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }
    var id by remember {
        mutableStateOf("")
    }
    var pwd by remember {
        mutableStateOf("")
    }
    var xueyuan by remember {
        mutableStateOf("")
    }
    var banji by remember {
        mutableStateOf("")
    }
    var zhuanye by remember {
        mutableStateOf("")
    }
    var err by remember {
        mutableStateOf(false)
    }
    var showHowToUse by remember {
        mutableStateOf(false)
    }
    var showDeleteDialog by remember {
        mutableStateOf(false)
    }
    var needDelete by remember {
        mutableStateOf(Any())
    }
    if (showDeleteDialog) {
        AlertDialog(onDismissRequest = { showDeleteDialog = false }, text = {
            Column {
                Text(text = "提示", fontSize = 25.sp)
                Text(text = "是否删除？删除后只能重新再填了捏")
            }
        }, confirmButton = {
            TextButton(onClick = {
                val de = needDelete as StuData
                mainViewModel.delUser(needDelete as StuData)
                userList = mainViewModel.getUserList
                ToastUtils.showShort("已删除:${de.name}")
                showDeleteDialog = false
            }) {
                Text(text = "删除", color = mainColor)
            }
        }
        )
    }

    if (showHowToUse) {
        AlertDialog(onDismissRequest = { showHowToUse = false },
            text = {
                Column {
                    Text(text = "使用须知：", fontSize = 25.sp)
                    Text(
                        text = """
                          1.该程序由琴三岁开发
                          2.该程序目的为实现学习通批量打卡
                          3.该程序不会泄露任何个人信息到除学习通打卡网站外的任何网站
                          4.该程序完全免费，请不要二改并倒卖！
                          5.有问题致邮：qinsansui@gmail.com
                      """.trimIndent()
                    )
                    Text(text = "使用方法：", fontSize = 25.sp)
                    Text(text = """
                          1.点击右下角添加按钮，添加需要打卡的人的个人信息
                          2.添加完成后，点击右上角扳手按钮开始自动打卡
                          3.打卡过程由当前设备网络环境与打卡人列表有关
                          4.若打卡相关信息填写错误，请长按该卡片清除并重新填写
                    """.trimIndent())
                }
            },
            confirmButton = {
                TextButton(onClick = { showHowToUse = false }) {
                    Text(text = "知道了", color = mainColor)
                }
            })
    }
    if (showDialog) {

        AlertDialog(
            onDismissRequest = { showDialog = false },

            confirmButton = {
                TextButton(onClick = {
                    if (checkInfo(name, phone, id, pwd, xueyuan, banji, zhuanye)) {
                        err = false
                        if (mainViewModel.saveUser(
                                StuData(
                                    name,
                                    phone,
                                    id,
                                    pwd,
                                    xueyuan,
                                    zhuanye,
                                    banji
                                )
                            )
                        ) {
                            ToastUtils.showShort("添加成功！点击右上角小扳手即可开始打卡！")
                        } else {
                            ToastUtils.showShort("已有重名用户存在！")
                        }
                        userList = mainViewModel.getUser()
                        name = ""
                        phone = ""
                        id = ""
                        pwd = ""
                        xueyuan = ""
                        banji = ""
                        zhuanye = ""
                        showDialog = false
                    } else {
                        err = true
                    }
                }) {
                    Text(text = "添加", color = xiamulv)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(text = "取消", color = sakuraTitle)
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    Text(text = "添加用户相关信息", fontSize = 20.sp)
                    Spacer(modifier = Modifier.padding(top = 20.dp))
                    OutlinedTextField(
                        isError = err,
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(text = "请输入名字") },
                        maxLines = 1,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = mainColor,
                            cursorColor = mainColor,
                            placeholderColor = mainColor,
                            focusedLabelColor = mainColor,
                            unfocusedIndicatorColor = ActionBarColor,
                            unfocusedLabelColor = ActionBarColor,
                            focusedIndicatorColor = mainColor,
                            backgroundColor = Color.Transparent
                        ),
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        isError = err,
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text(text = "请输入手机号") },
                        maxLines = 1,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = mainColor,
                            cursorColor = mainColor,
                            placeholderColor = mainColor,
                            focusedLabelColor = mainColor,
                            unfocusedIndicatorColor = ActionBarColor,
                            unfocusedLabelColor = ActionBarColor,
                            focusedIndicatorColor = mainColor,
                            backgroundColor = Color.Transparent
                        ),
                        singleLine = true,

                        )
                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        isError = err,
                        value = id,
                        onValueChange = { id = it },
                        label = { Text(text = "请输入身份证号") },
                        maxLines = 1,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = mainColor,
                            cursorColor = mainColor,
                            placeholderColor = mainColor,
                            focusedLabelColor = mainColor,
                            unfocusedIndicatorColor = ActionBarColor,
                            unfocusedLabelColor = ActionBarColor,
                            focusedIndicatorColor = mainColor,
                            backgroundColor = Color.Transparent
                        ),
                        singleLine = true,

                        )
                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        isError = err,
                        value = pwd,
                        onValueChange = { pwd = it },
                        label = { Text(text = "请输入学习通登录密码") },
                        maxLines = 1,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = mainColor,
                            cursorColor = mainColor,
                            placeholderColor = mainColor,
                            focusedLabelColor = mainColor,
                            unfocusedIndicatorColor = ActionBarColor,
                            unfocusedLabelColor = ActionBarColor,
                            focusedIndicatorColor = mainColor,
                            backgroundColor = Color.Transparent
                        ),
                        singleLine = true,

                        )
                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        isError = err,
                        value = xueyuan,
                        onValueChange = { xueyuan = it },
                        label = { Text(text = "请输入所在学院") },
                        maxLines = 1,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = mainColor,
                            cursorColor = mainColor,
                            placeholderColor = mainColor,
                            focusedLabelColor = mainColor,
                            unfocusedIndicatorColor = ActionBarColor,
                            unfocusedLabelColor = ActionBarColor,
                            focusedIndicatorColor = mainColor,
                            backgroundColor = Color.Transparent
                        ),
                        singleLine = true,

                        )
                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        isError = err,
                        value = zhuanye,
                        onValueChange = { zhuanye = it },
                        label = { Text(text = "请输入专业") },
                        maxLines = 1,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = mainColor,
                            cursorColor = mainColor,
                            placeholderColor = mainColor,
                            focusedLabelColor = mainColor,
                            unfocusedIndicatorColor = ActionBarColor,
                            unfocusedLabelColor = ActionBarColor,
                            focusedIndicatorColor = mainColor,
                            backgroundColor = Color.Transparent
                        ),
                        singleLine = true,

                        )
                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        isError = err,
                        value = banji,
                        onValueChange = { banji = it },
                        label = { Text(text = "请输入所在班级") },
                        maxLines = 1,
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = mainColor,
                            cursorColor = mainColor,
                            placeholderColor = mainColor,
                            focusedLabelColor = mainColor,
                            unfocusedIndicatorColor = ActionBarColor,
                            unfocusedLabelColor = ActionBarColor,
                            focusedIndicatorColor = mainColor,
                            backgroundColor = Color.Transparent
                        ),
                        singleLine = true,
                    )

                }
            }
        )
    }
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(modifier = Modifier.fillMaxWidth(), backgroundColor = mainColor) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "学习通批量打卡",
                    fontSize = 25.sp,
                    fontFamily = FontFamily.Cursive,
                    color = Color.White,
                    modifier = Modifier.padding(start = 10.dp)
                )
                Row {
                    IconButton(
                        onClick = {
                            if (userList.size == 0) {
                                ToastUtils.showShort("请先添加要打卡的人的信息（）")
                            } else {
                                mainViewModel.startCheck()
                                ToastUtils.showShort("正在进行打卡操作，请稍等...")
                            }
                        }, modifier = Modifier
                            .padding(end = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Sharp.Build,
                            contentDescription = null,
                            tint = Color.White,
                        )
                    }
                    IconButton(onClick = { showHowToUse = true }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }, floatingActionButton = {
        ExtendedFloatingActionButton(
            backgroundColor = mainColor,
            text = {
                Text(text = "添加", color = Color.White)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            },
            onClick = {
                showDialog = true
            },
        )
    }) {
        if (userList.size == 0) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = mainColor)
                Text(text = "这里啥也没有捏，赶紧添加一个吧！", fontSize = 20.sp)
            }
        } else {
            LazyColumn(state = lazyState) {

                itemsIndexed(userList) { i: Int, stuData: StuData ->
                    if (i == 0) {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 20.dp, end = 20.dp
                            )
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = {
                                        showDeleteDialog = true
                                        needDelete = stuData
                                    }
                                )
                            }
                            .background(mainColor, RoundedCornerShape(10.dp))

                    ) {
                        var isComplete by remember {
                            mutableStateOf(false)
                        }
                        if (response.size == userList.size) {
                            isComplete = true
                        }
                        Text(
                            text = "姓名：${stuData.name}",
                            modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "打卡信息：${if (isComplete) response[i].msg else ""}",
                            color = Color.White,
                            modifier = Modifier.padding(start = 20.dp, bottom = 20.dp)
                        )


                    }
                    if (i != (userList.size - 1)) {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

            }
        }

    }
}

internal fun checkInfo(
    name: String,
    phone: String,
    id: String,
    pwd: String,
    xueyuan: String,
    banji: String,
    zhuanye: String
): Boolean {
    return !(name == "" || phone == "" || id == "" || pwd == "" || xueyuan == "" || banji == "" || zhuanye == "")
}
