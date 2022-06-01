package com.example.aer_app

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.futured.donut.DonutSection
import com.example.aer_app.databinding.FragmentProblemBinding
import com.example.aer_app.databinding.FragmentUserBinding
import com.example.aer_app.models.Problems
import com.example.aer_app.models.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProblemFragment : Fragment() {

    private var _binding: FragmentProblemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProblemBinding.inflate(inflater, container, false)
        val id_user = this.arguments?.get("id")

        getProblemData(id_user.toString())

        return binding.root

    }

    fun getProblemData(id_user: String) {
        CoroutineScope(Dispatchers.IO).launch {

            Api.retrofitService.getProblemData(id_user).enqueue(object : Callback<Problems> {
                override fun onResponse(
                    call: Call<Problems>,
                    response: Response<Problems>
                ) {

                    if (response.isSuccessful) {
                        val problem = response.body()!!
                        binding.titleProblem.text = problem.title
                        binding.numberProblemNumber.text = problem.id_problem.toString()
                        binding.percentageDifficultyNumber.text =
                            problem.percentage_users_completed.toString() + "%"
                        binding.numberShipmentsNumber.text =
                            (problem.java_shipments + problem.c_shipments + problem.cpp_shipments).toString()
                        binding.usersAttemptNumber.text = problem.attempts.toString()
                        binding.usersAcceptedNumber.text = problem.no_repeated_accepteds.toString()

                        //Donuts sections

                        val section_wrongAnswer = DonutSection(
                            name = "wrongAnswer",
                            color = Color.parseColor("#FF9900"),
                            amount = problem.wrong_answer.toFloat()
                        )

                        val section_accepteds = DonutSection(
                            name = "accepteds",
                            color = Color.parseColor("#3366CC"),
                            amount = problem.accepteds.toFloat()
                        )

                        val section_timeLimit = DonutSection(
                            name = "timeLimit",
                            color = Color.parseColor("#109618"),
                            amount = problem.time_limit.toFloat()
                        )

                        val section_memoryLimit = DonutSection(
                            name = "memoryLimit",
                            color = Color.parseColor("#8A2BE2"),
                            amount = problem.memory_limit.toFloat()
                        )

                        val section_presentationError = DonutSection(
                            name = "presentationError",
                            color = Color.parseColor("#DC3912"),
                            amount = problem.presentation_error.toFloat()
                        )

                        val section_other = DonutSection(
                            name = "other",
                            color = Color.parseColor("#CCCCCC"),
                            amount = problem.other.toFloat()
                        )

                        val section_restrictedFunction = DonutSection(
                            name = "restrictedFunction",
                            color = Color.parseColor("#DD4477"),
                            amount = problem.restricted_function.toFloat()
                        )

                        val section_compilationError = DonutSection(
                            name = "compilationError",
                            color = Color.parseColor("#B82E2E"),
                            amount = problem.compilation_error.toFloat()
                        )
                        binding.problemShipmentsDonut.cap = (problem.shipments.toFloat())
                        binding.problemShipmentsDonut.submitData(
                            listOf(
                                section_accepteds,
                                section_other,
                                section_compilationError,
                                section_memoryLimit,
                                section_presentationError,
                                section_restrictedFunction,
                                section_timeLimit,
                                section_wrongAnswer
                            )
                        )

                    }
                }

                override fun onFailure(call: Call<Problems>, t: Throwable) {
                    t.printStackTrace()
                }


            })
        }
    }


    companion object {

    }
}