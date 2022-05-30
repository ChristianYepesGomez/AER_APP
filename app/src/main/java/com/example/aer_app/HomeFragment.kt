package com.example.aer_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aer_app.databinding.FragmentHomeBinding
import com.example.aer_app.databinding.FragmentUserBinding
import com.example.aer_app.models.Problems
import com.example.aer_app.models.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var users_list = mutableListOf<Users>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        getUsersData()

        return binding.root
    }

    fun getUsersData() {
        CoroutineScope(Dispatchers.IO).launch {
            Api.retrofitService.getUsersData().enqueue(object : Callback<MutableList<Users>> {
                override fun onResponse(
                    call: Call<MutableList<Users>>,
                    response: Response<MutableList<Users>>
                ) {
                    println(response)

                    if (response.isSuccessful) {
                        var total_shipments = 0
                        var institution_list = mutableListOf<String>()
                        users_list.addAll(response.body()!!)
                        binding.homeTotalUsersNumber.text = users_list.size.toString()
                        users_list.forEach {
                            if (!institution_list.contains(it.institution)) {
                                institution_list.add(it.institution)
                            }
                            total_shipments += it.shipments
                        }
                        binding.homeTotalShipmentsNumber.text = total_shipments.toString()
                        binding.homeTotalInstitutionsNumber.text = institution_list.size.toString()
                    }
                }

                override fun onFailure(call: Call<MutableList<Users>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
            Api.retrofitService.getProblemData().enqueue(object : Callback<MutableList<Problems>> {
                override fun onResponse(
                    call: Call<MutableList<Problems>>,
                    response: Response<MutableList<Problems>>
                ) {
                    if (response.isSuccessful) {
                        var problem_list: MutableList<Problems>
                        problem_list = response.body()!!
                        binding.homeProblemDayNumber.text =
                            problem_list.get((0..problem_list.size).random()).title
                        binding.homeTotalProblemsNumber.text = problem_list.size.toString()

                    }
                }

                override fun onFailure(call: Call<MutableList<Problems>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }

    companion object {

    }
}