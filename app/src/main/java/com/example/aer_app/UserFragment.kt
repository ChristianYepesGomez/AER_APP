package com.example.aer_app

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.core.view.marginLeft
import androidx.recyclerview.widget.RecyclerView
import com.example.aer_app.databinding.FragmentUserBinding
import com.example.aer_app.models.Institutions
import com.example.aer_app.models.Users
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        val id_user = this.arguments?.get("id")

        getUserData(id_user.toString())

        return binding.root
    }

    //Calling the API to take the data from specific USER
    fun getUserData(id_user: String) {
        CoroutineScope(Dispatchers.IO).launch {
            Api.retrofitService.getUserData(id_user).enqueue(object : Callback<Users> {
                override fun onResponse(
                    call: Call<Users>,
                    response: Response<Users>
                ) {

                    if (response.isSuccessful) {
                        //Fill the view for individual user with the info if response was succesful
                        val user = response.body()!!
                        Picasso.get().load(user.logo_src).into(binding.imgUserFragment);
                        binding.nameUserFragment.text = user.name
                        binding.nickUserFragment.text = user.nick


                        CoroutineScope(Dispatchers.IO).launch {
                            Api.retrofitService.getInstitutionData(user.institution)
                                .enqueue(object : Callback<Institutions> {
                                    override fun onResponse(
                                        call: Call<Institutions>,
                                        response: Response<Institutions>
                                    ) {

                                        if (response.isSuccessful) {

                                            binding.institutionUserFragment.text =
                                                response.body()!!.name

                                        }
                                    }

                                    override fun onFailure(call: Call<Institutions>, t: Throwable) {
                                        t.printStackTrace()
                                    }


                                })
                        }

                        binding.userTotalShipments.text =
                            "Envios totales: " + user.shipments.toString()
                        binding.userCompletedProblems.text =
                            "Completados: " + user.accepteds.toString()
                        binding.userIntentedProblems.text = "Intentados: " + user.intents.toString()

                        val table_problems = binding.tableProblemsUser

                        //Both Loops just make a dynamic table with the problems attempted by the user

                        //Loop for the problems attempted by the user and not solved
                        user.problems_attempted.forEach {
                            val row = TableRow(activity)
                            val id_problem = TextView(activity)
                            val name_problem = TextView(activity)
                            val state_problem = TextView(activity)

                            //ID Problem TV
                            id_problem.text = it.id_problem.toString()
                            //Name Problem TV
                            name_problem.text = it.title
                            name_problem.setTextColor(Color.parseColor("#CCF3EE"))
                            name_problem.layoutParams = binding.titleProblemUserTable.layoutParams
                            //State Problems TV
                            state_problem.text = "X"
                            state_problem.setTextColor(Color.RED)
                            state_problem.layoutParams = binding.stateProblemUserTable.layoutParams
                            //Add TV to ROW and ROW to TABLE
                            row.addView(id_problem)
                            row.addView(name_problem)
                            row.addView(state_problem)
                            table_problems.addView(row)
                        }
                        //Loop for the problems solved by the user
                        user.problems_solved.forEach {
                            val row = TableRow(activity)
                            val id_problem = TextView(activity)
                            val name_problem = TextView(activity)
                            val state_problem = TextView(activity)

                            //ID Problem TV
                            id_problem.text = it.id_problem.toString()
                            //Name Problem TV
                            name_problem.text = it.title
                            name_problem.setTextColor(Color.parseColor("#CCF3EE"))
                            name_problem.layoutParams = binding.titleProblemUserTable.layoutParams
                            //State Problems TV
                            state_problem.text = "AC"
                            state_problem.setTextColor(Color.GREEN)
                            state_problem.layoutParams = binding.stateProblemUserTable.layoutParams
                            //Add TV to ROW and ROW to TABLE
                            row.addView(id_problem)
                            row.addView(name_problem)
                            row.addView(state_problem)
                            table_problems.addView(row)
                        }
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