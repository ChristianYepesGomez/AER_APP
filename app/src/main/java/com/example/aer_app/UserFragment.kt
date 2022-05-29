package com.example.aer_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aer_app.databinding.FragmentUserBinding
import com.example.aer_app.models.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        val id_user = this.arguments?.get("id")

        getUserData(id_user.toString())

        return binding.root
    }

    fun getUserData(id_user: String) {
        CoroutineScope(Dispatchers.IO).launch {

            Api.retrofitService.getUserData(id_user).enqueue(object : Callback<Users> {
                override fun onResponse(
                    call: Call<Users>,
                    response: Response<Users>
                ) {
                    println(response)

                    if (response.isSuccessful) {
                        val user = response.body()!!
                        binding.nameUserFragment.text = user.name
                        binding.nickUserFragment.text = user.nick
                        binding.institutionUserFragment.text = user.institution
                        binding.userTotalShipments.text =
                            "Envios totales: " + user.shipments.toString()
                        binding.userCompletedProblems.text =
                            "Completados: " + user.accepteds.toString()
                        binding.userIntentedProblems.text = "Intentados: " + user.intents.toString()
                    }
                }

                override fun onFailure(call: Call<Users>, t: Throwable) {
                    t.printStackTrace()
                }


            })
        }
    }

    companion object {
    }

}