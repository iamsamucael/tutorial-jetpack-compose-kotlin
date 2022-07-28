package com.samuelaraujo.tutorialjetpackcompose

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.samuelaraujo.tutorialjetpackcompose.model.AddUserState
import kotlinx.coroutines.launch

class FormActivity : ComponentActivity() {

    private val _addUserState = MutableLiveData(AddUserState())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { FormContent() }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    private fun FormContent(){
        Scaffold(topBar = { FormTopBar() }) {
            FormFields()
        }
    }

    @Composable
    private fun FormTopBar() {
        TopAppBar(title = { Text(text = stringResource(id = R.string.add_tittle))},
            navigationIcon = {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Icon Button Back")
                }
            },
            contentColor = Color.White,
            backgroundColor = colorResource(id = R.color.purple_500)

        )
    }

    @Composable
    private fun FormFields() {

        val nameState = remember { mutableStateOf("") }
        val emailState = remember { mutableStateOf("") }
        val roles = resources.getStringArray(R.array.roles)
        val selectedRoleName = roles.firstOrNull {it == _addUserState.value?.role} ?: "Desconhecido"
        val isRoleOpen = remember { mutableStateOf(false) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()) {

            OutlinedTextField(value = nameState.value, onValueChange = { nameState.value = it
                                                                       _addUserState.value = _addUserState.value?.copy(name = it)},
                label = { Text(text = stringResource(id = R.string.name_hint)) })

            OutlinedTextField(value = emailState.value, onValueChange = { emailState.value = it
                                                                        _addUserState.value = _addUserState.value?.copy(email = it)},
                label = { Text(text = stringResource(id = R.string.email_hint)) })

            Row(verticalAlignment = Alignment.CenterVertically){
                TextButton(onClick = { isRoleOpen.value = true }) {
                    Text(text = stringResource(id = R.string.role_select))
                }
                Text(text = selectedRoleName)
            DropdownMenu(expanded = isRoleOpen.value, onDismissRequest = { /*TODO*/ }) {

                    for (role in roles) {
                        DropdownMenuItem(onClick = { isRoleOpen.value = false
                        _addUserState.value = _addUserState.value?.copy(role = role)}) {
                            Text(text = role)
                        }
                    }
                }
            }
            Button(onClick = { onAddTapped() }) {
                Text(text = stringResource(id = R.string.add_button_text))
            }
        }
    }

    private fun onAddTapped() {
        val userState = _addUserState.value ?: return
        if (userState.name.isNotEmpty() && userState.email.isNotEmpty() && userState.role.isNotEmpty()){
            lifecycleScope.launch {
                setResult(RESULT_OK, Intent().apply {
                    putExtra("user_state", userState)
                })
                finish()
            }
        }
    }

    @Preview
    @Composable
    private fun DefaultPreview() {
        FormContent()
    }
}