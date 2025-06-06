package com.jayashree.countriesinfo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    var mAuth:FirebaseAuth?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
       setContent {
           PrepareLoginScreen()
       }
    }

    @Composable
    fun PrepareLoginScreen(){
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Column{
            TextField(
                value = email,
                onValueChange = {newText ->
                email = newText
            },
                label = { Text("Email") },
                placeholder = {Text("Enter your email")}
            )

            TextField(
                value = password,
                onValueChange = {newText->
                    password = newText
                },
                label = { Text("Password") },
                placeholder = { Text("Enter your password") }
            )
            Button(onClick = {
                loginUser(email, password)
            }){
                Text(text = "Login")
            }
        }
    }

    fun loginUser(email:String, password:String){
mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener{
    task->
    if(task.isSuccessful){
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }else{
        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
    }
}
    }
}