package com.example.aer_app

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aer_app.models.Users
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aer_app.databinding.FragmentUserBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        manager = LinearLayoutManager(context);
        getAllData()

        return binding.root
    }


    fun getAllData() {
        Api.retrofitService.getUserData().enqueue(object : Callback<List<Users>> {
            override fun onResponse(
                call: Call<List<Users>>,
                response: Response<List<Users>>
            ) {
                if (response.isSuccessful) {
                    recyclerView = binding.recyclerViewUsers.apply {
                        myAdapter = UsersAdapter(response.body()!!)
                        layoutManager = manager
                        adapter = myAdapter
                    }
                }
            }

            override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }


    companion object {

    }
}