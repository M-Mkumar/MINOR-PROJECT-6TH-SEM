package org.root.sentiments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.root.sentiments.databinding.ActivityLoginBinding

private const val TAG = "LoginActivity"
class LoginActivity : AppCompatActivity() {
    private lateinit var resultActivityLauncher: ActivityResultLauncher<Intent>
    private lateinit var binding: ActivityLoginBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)
    }

    override fun onStart() {
        super.onStart()
        resultActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                try {
                    val account = GoogleSignIn.getSignedInAccountFromIntent(it.data).result
                    account?.let { signInAccount ->
                        googleAuthForFirebase(signInAccount)
                    }
                } catch (ex: Exception) {
//                Log.w(TAG, "${ex.message}", )/
                }
            }
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
