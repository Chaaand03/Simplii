@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.bytes.a.half.simplii_android.composables

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bytes.a.half.simplii_android.R
import com.bytes.a.half.simplii_android.isValidString
import com.bytes.a.half.simplii_android.showToast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountComposable(
    context: Context,
    onSignIn: (email: String, password: String) -> Unit,
    onSignUp: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(id = R.string.login_signup),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary)
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 30.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val emailFieldValue = remember { mutableStateOf(TextFieldValue()) }

                OutlinedTextField(
                    value = emailFieldValue.value,
                    onValueChange = { emailFieldValue.value = it },
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom=8.dp),
                    placeholder = { Text(stringResource(id = R.string.email_id)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                )
                val passwordFieldValue = remember { mutableStateOf(TextFieldValue()) }

                OutlinedTextField(
                    value = passwordFieldValue.value,
                    onValueChange = { passwordFieldValue.value = it },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    placeholder = { Text(text = "Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    },
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xff4caf50)
                    ),
                        onClick = {
                            if (passwordFieldValue.value.text.isValidString() && emailFieldValue.value.text.isValidString()) {
                                onSignIn(emailFieldValue.value.text, passwordFieldValue.value.text)
                            } else {
                                context.showToast(R.string.sign_in_alert)
                            }
                        }) {
                        Text(
                            stringResource(id = R.string.login),
                            modifier = Modifier.padding(10.dp),
                            color = Color.White
                        )
                    }
                    Button(colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xff039be5)
                    ),
                        onClick = {
                            onSignUp()
                        }) {
                        Text(
                            stringResource(id = R.string.sign_up),
                            modifier = Modifier.padding(10.dp),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
