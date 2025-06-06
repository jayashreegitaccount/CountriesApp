package com.jayashree.countriesinfo

import android.content.Intent
import android.credentials.GetCredentialRequest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.credentials.CustomCredential
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegistrationActivity : AppCompatActivity() {
    var isLoading:Boolean = false
    var mAuth:FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        if(Firebase.auth.currentUser != null){
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
       /* val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(baseContext.getString(R.string.default_web_client_id))
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
        } else {
            Log.w(TAG, "Credential is not of type Google ID!")
        }*/
        setContent {
              PrepareRegistrationScreenWithToolBar()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PrepareRegistrationScreenWithToolBar(){
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(title = {Text("Registration")})
            },
            content = { innerPadding->
                PrepareSignUpPageFields(modifier = Modifier.padding(innerPadding))
            }
        )
    }

    @Composable
    fun PrepareSignUpPageFields(modifier: Modifier){
        var emailText by remember { mutableStateOf("") }
        var PasswordText by remember { mutableStateOf("") }


        Column(modifier  = modifier) {
            TextField(
                value = emailText,
                onValueChange = {newText->
                    emailText = newText
                },
                label = { Text("Email") },
                placeholder = {Text("Enter Email Address")}

            )
            TextField(
                value = PasswordText,
                onValueChange = {newText->
                    PasswordText = newText
                },
                label = { Text("Password") },
                placeholder = {Text(text = "Set your password")}

            )

            PrepareSpinner()

            Button(onClick = {onRegisterButtonClick(emailText, PasswordText)}) {
                Text(text = "SignUp")
            }

            AnimatedVisibility(visible =  isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(48.dp), color = MaterialTheme.colorScheme.primary, strokeWidth = 4.dp)
            }



        }

    }

    @Composable
    fun PrepareSpinner(){
        var expanded by remember { mutableStateOf(false) }
        val items = listOf("Type1. Type2, Type3")
        var selectedItem by remember { mutableStateOf(items[0]) }
        Column {
            Text(
                text = selectedItem,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        expanded = true
                    }.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            DropdownMenu(expanded = expanded, onDismissRequest = {expanded = false}, modifier = Modifier.fillMaxWidth()) {
                items.forEach{
                    label ->
                    DropdownMenuItem(
                        text = {Text(label)},
                        onClick = {
                            selectedItem = label
                            expanded = false
                        }

                    )
                }
            }
        }
    }

    fun onRegisterButtonClick(email:String, password:String){
        mAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener{
            task->
            if(task.isSuccessful){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Registration not successfull", Toast.LENGTH_LONG).show()
            }
        }


    }
}