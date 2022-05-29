package com.example.aer_app.models

data class Problems(
    val id_problem: Int,
    val title: String,
    val no_repeated_accepteds: Int,
    val wrong_answer: Int,
    val accepteds: Int,
    val shipments: Int,
    val time_limit: Int,
    val memory_limit: Int,
    val presentation_error: Int,
    val attempts: Int,
    val other: Int,
    val restricted_function: Int,
    val compilation_error: Int,
    val c_shipments: Int,
    val cpp_shipments: Int,
    val java_shipments: Int,
    val categories: List<Categories>,
    val percentage_users_completed: Int,

    ) {
}