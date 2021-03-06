package com.example.aer_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aer_app.adapters.UsersAdapter
import com.example.aer_app.databinding.FragmentUserRecyclerBinding
import com.example.aer_app.models.Problems
import com.example.aer_app.models.Users
import com.example.aer_app.models.UsersNoProblems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserRecyclerFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: UsersAdapter
    private var _binding: FragmentUserRecyclerBinding? = null
    private val binding get() = _binding!!
    private var users_list = mutableListOf<UsersNoProblems>()
    private var problem_size = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserRecyclerBinding.inflate(inflater, container, false)
        manager = LinearLayoutManager(context);
        initRecycler()
        getAllData()
        binding.svUsers.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getDataByName(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return false
            }

        })
        binding.svUsers.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                getAllData()
                binding.recyclerViewUsers.requestFocus()
                return true
            }

        })
        return binding.root
    }

    private fun initRecycler() {
        recyclerView = binding.recyclerViewUsers.apply {
            myAdapter = UsersAdapter(users_list, problem_size, UsersAdapter.OnClickListener {
                val bundle = Bundle()
                bundle.putString("id", it.id_user.toString())
                val transaction = parentFragmentManager.beginTransaction()
                val fragment = UserFragment()
                fragment.arguments = bundle
                transaction.addToBackStack(null)
                transaction.replace(com.example.aer_app.R.id.frame_layout, fragment)
                transaction.commit()
            })
            layoutManager = manager
            adapter = myAdapter
        }
    }


    fun getAllData() {
        CoroutineScope(Dispatchers.IO).launch {
            Api.retrofitService.getUsersData()
                .enqueue(object : Callback<MutableList<UsersNoProblems>> {
                    override fun onResponse(
                        call: Call<MutableList<UsersNoProblems>>,
                        response: Response<MutableList<UsersNoProblems>>
                    ) {

                        if (response.isSuccessful) {
                            users_list.clear()
                            users_list.addAll(response.body()!!)
                        }
                    }

                    override fun onFailure(call: Call<MutableList<UsersNoProblems>>, t: Throwable) {
                        t.printStackTrace()
                    }
                })

            Api.retrofitService.getProblemsData().enqueue(object : Callback<MutableList<Problems>> {
                override fun onResponse(
                    call: Call<MutableList<Problems>>,
                    response: Response<MutableList<Problems>>
                ) {
                    if (response.isSuccessful) {
                        problem_size.add(response.body()!!.size)
                        myAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<MutableList<Problems>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }

    }

    fun getDataByName(name: String) {

        CoroutineScope(Dispatchers.IO).launch {
            Api.retrofitService.getUsersByName(name)
                .enqueue(object : Callback<MutableList<UsersNoProblems>> {
                    override fun onResponse(
                        call: Call<MutableList<UsersNoProblems>>,
                        response: Response<MutableList<UsersNoProblems>>
                    ) {

                        if (response.isSuccessful) {
                            users_list.clear()
                            users_list.addAll(response.body()!!)
                        }
                    }

                    override fun onFailure(call: Call<MutableList<UsersNoProblems>>, t: Throwable) {
                        t.printStackTrace()
                    }
                })

            Api.retrofitService.getProblemsData().enqueue(object : Callback<MutableList<Problems>> {
                override fun onResponse(
                    call: Call<MutableList<Problems>>,
                    response: Response<MutableList<Problems>>
                ) {
                    if (response.isSuccessful) {
                        problem_size.add(response.body()!!.size)
                    }
                }

                override fun onFailure(call: Call<MutableList<Problems>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }

        myAdapter.notifyDataSetChanged()
    }


    companion object {

    }
}