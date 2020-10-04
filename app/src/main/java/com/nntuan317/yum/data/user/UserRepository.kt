package com.nntuan317.yum.data.user

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Singleton

interface UserRepository {
    fun getUser(): User?

    fun signUp(email: String, password: String): Task<AuthResult>

    fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult>

    fun signInWithGoogle(idToken: String): Task<AuthResult>
}

@Singleton
class UserRepositoryImp @Inject constructor(private val auth: FirebaseAuth) : UserRepository {

    override fun getUser(): User? {
        val firebaseUser = auth.currentUser
        return firebaseUser?.let {
            User(
                uid = it.uid,
                name = it.displayName,
                email = it.email,
                photoUrl = it.photoUrl,
                isEmailVerified = it.isEmailVerified
            )
        }
    }

    override fun signUp(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    override fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    override fun signInWithGoogle(idToken: String): Task<AuthResult> {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return auth.signInWithCredential(credential)
    }
}