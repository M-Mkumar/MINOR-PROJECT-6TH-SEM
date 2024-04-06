package org.root.sentiments

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.root.sentiments.databinding.ActivityLoginBinding


private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    private val activityResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.i(TAG, "Checkpoint: ")
        if (result.resultCode == RESULT_OK) {
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val signInAccount = accountTask.getResult(
                    ApiException::class.java
                )
                val authCredential =
                    GoogleAuthProvider.getCredential(signInAccount.idToken, null)
                auth.signInWithCredential(authCredential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.i(TAG, "Sign In Successful : ")
                        auth = FirebaseAuth.getInstance()
                        Intent(this, IPAddressActivity::class.java).apply {
                            startActivity(this)
                        }
                    } else {
                        Log.i(TAG, "Sign In Failed")
                        Toast.makeText(
                            this,
                            "Failed to sign in: " + task.exception,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: ApiException) {
                e.printStackTrace()
            }
        }
    }


    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate: ${applicationContext.packageName}")
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        FirebaseApp.initializeApp(this);
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("553275164506-0hc5rad9ko485rd5kuq47v50gnu07f9l.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, options)

        binding.btnSignUp.setOnClickListener {
            googleSignInClient.signInIntent.also {  intent ->
                activityResultLauncher.launch(intent)
            }
        }

        if (auth.currentUser != null) {
            Intent(this, IPAddressActivity::class.java).apply {
                startActivity(this)
            }
        }

    }

override fun onStart() {
    super.onStart()

}




































private fun googleAuthForFirebase(account: GoogleSignInAccount) {
    val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
    CoroutineScope(Dispatchers.IO).launch {
        try {
            auth.signInWithCredential(credentials).await()
            withContext(Dispatchers.Main) {
                Toast.makeText(this@LoginActivity, "Welcome to FitFriend", Toast.LENGTH_SHORT)
                    .show()
                Log.i(TAG, "Sign up successful")
                Intent(this@LoginActivity, IPAddressActivity::class.java).apply {
                    startActivity(this)
                }
            }
        } catch (ex: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(this@LoginActivity, ex.message, Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Exception while signing up: $ex")
            }
        }
    }
}
}


//fun commented() {
//        val currentUser = auth.currentUser
//        super.onStart()
//        resultActivityLauncher =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//                try {
//                    val account = GoogleSignIn.getSignedInAccountFromIntent(it.data).result
//                    Log.i(TAG, "Account: $account")
//                    account?.let { signInAccount ->
//                        Log.i(TAG, "Moving Further!!")
//                        googleAuthForFirebase(signInAccount)
//                    }
//                } catch (ex: Exception) {
////                Log.w(TAG, "${ex.message}", )/
//                }
//
//                binding.btnSignUp.setOnClickListener {
//                    Log.i(TAG, "Trying to Sign Up")
//                    val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                        .requestIdToken(getString(R.string.web_detail))
//                        .requestEmail()
//                        .build()
//                    val signInClient = GoogleSignIn.getClient(this, options)
//                    signInClient.signInIntent.also { intent ->
//                        Log.i(TAG, "Launching sign-in activity")
//                        resultActivityLauncher.launch(intent)
//                    }
//                }
//            }
//
//}