package com.example.aer_app.models

data class Users(
    val id_user: Int,
    val nick: String,
    val name: String,
    val country: String,
    val institution: String,
    val logo_src: String,
    val shipments: Int,
    val total_accepteds: Int,
    val intents: Int,
    val accepteds: Int,
    val problems_attempted: List<ProblemsShort>,
    val problems_solved: List<ProblemsShort>
) {
}