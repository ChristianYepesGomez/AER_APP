package com.example.aer_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aer_app.adapters.ProblemsAdapter
import com.example.aer_app.adapters.UsersAdapter
import com.example.aer_app.databinding.FragmentProblemRecyclerBinding
import com.example.aer_app.databinding.FragmentUserBinding
import com.example.aer_app.databinding.FragmentUserRecyclerBinding
import com.example.aer_app.models.Problems
import com.example.aer_app.models.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProblemRecyclerFragment : Fragment() {

    private var _binding: FragmentProblemRecyclerBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: ProblemsAdapter
    private var problems_list = mutableListOf<Problems>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProblemRecyclerBinding.inflate(inflater, container, false)
        manager = LinearLayoutManager(context);

        initRecycler()
        getAllDataProblems()
        return binding.root
    }

    private fun initRecycler() {
        recyclerView = binding.recyclerViewProblems.apply {
            myAdapter = ProblemsAdapter(problems_list, ProblemsAdapter.OnClickListener {
                val bundle = Bundle()
                bundle.putString("id", it.id_problem.toString())
                val transaction = parentFragmentManager.beginTransaction()
                val fragment = ProblemFragment()
                fragment.arguments = bundle
                transaction.addToBackStack(null)
                transaction.replace(R.id.frame_layout, fragment)
                transaction.commit()
            })
            layoutManager = manager
            adapter = myAdapter
        }
    }

    fun getAllDataProblems() {
        CoroutineScope(Dispatchers.IO).launch {

            Api.retrofitService.getProblemsData().enqueue(object : Callback<MutableList<Problems>> {
                override fun onResponse(
                    call: Call<MutableList<Problems>>,
                    response: Response<MutableList<Problems>>
                ) {
                    if (response.isSuccessful) {
                        problems_list.addAll(response.body()!!)
                        myAdapter.notifyDataSetChanged()
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