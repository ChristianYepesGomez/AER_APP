package com.example.aer_app.models

data class Institutions(
    val id: Int,
    val name: String,
    val problems_solved: Int,
    val shipments: Int,
    val logo_src: String,
    val users: List<Users>
) {
}