package com.samuelaraujo.tutorialjetpackcompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.samuelaraujo.tutorialjetpackcompose.model.AddUserState

class MainActivity : ComponentActivity() {

    private val responseState = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainContent() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val response = data?.getParcelableExtra<AddUserState>("user_state")
            responseState.value = response.toString()
        }
    }

    @Composable
    private fun MainContent() {
        Scaffold(
            topBar = { MainTopBar() },
            floatingActionButton = { MainFab() }
        ) {
            Text(text = responseState.value)
        }
    }

    @Composable
    private fun MainTopBar() {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
            backgroundColor = colorResource(id = R.color.purple_500),
            contentColor = Color.White)
    }

    @Composable
    private fun MainFab() {
        FloatingActionButton(onClick = { showAddForm() }) {
            Icon(imageVector = Icons.Filled.Add , contentDescription = "Button Icon Add")
        }
    }

    private fun showAddForm() {
        startActivityForResult(Intent(this, FormActivity::class.java), 100)
    }

}
