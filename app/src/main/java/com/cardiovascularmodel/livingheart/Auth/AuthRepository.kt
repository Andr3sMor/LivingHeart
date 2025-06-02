package com.cardiovascularmodel.livingheart.Auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

/**
 * Repositorio para manejar la autenticación con Firebase Auth.
 * Aquí exponemos funciones para registrarse, iniciar sesión y cerrar sesión.
 */
class AuthRepository(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    /**
     * Registra un usuario con email y contraseña.
     * Retorna `Result.success(uid)` si todo va bien, o `Result.failure(exception)` si falla.
     */
    suspend fun registerWithEmailAndPassword(email: String, password: String): Result<String> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val uid = authResult.user?.uid
            if (uid != null) {
                Result.success(uid)
            } else {
                Result.failure(Exception("No se pudo obtener el UID del usuario"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Inicia sesión con email y contraseña.
     * Retorna `Result.success(uid)` si todo va bien, o `Result.failure(exception)` si falla.
     */
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<String> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val uid = authResult.user?.uid
            if (uid != null) {
                Result.success(uid)
            } else {
                Result.failure(Exception("No se pudo obtener el UID del usuario"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    fun signOut() {
        firebaseAuth.signOut()
    }

    /**
     * Retorna el UID del usuario actual, o null si no hay nadie logueado.
     */
    fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }
}
