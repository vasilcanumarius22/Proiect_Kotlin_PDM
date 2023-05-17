package com.example.kotlin_project_pdm.models

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val password: String
){
    constructor(username: String, email: String, password: String) : this(0,username, email, password)
}