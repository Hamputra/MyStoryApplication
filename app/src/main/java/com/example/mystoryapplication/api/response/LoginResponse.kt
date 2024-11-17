package com.example.mystoryapplication.api.response

data class LoginResponse(
    val loginResult: LoginResult,
    val error: Boolean,
    val message: String
)

data class LoginResult(
    val userId: String,
    val name: String,
    val token: String
)