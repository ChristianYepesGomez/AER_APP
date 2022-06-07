package com.example.aer_app

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import com.example.aer_app.databinding.FragmentInstitutionBinding
import com.example.aer_app.databinding.FragmentUserBinding
import com.example.aer_app.models.Institutions
import com.example.aer_app.models.Users
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InstitutionFragment : Fragment() {

    private var _binding: FragmentInstitutionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInstitutionBinding.inflate(inflater, container, false)
        val id_institution = this.arguments?.get("id")
        getInstitutionData(id_institution.toString())
        return binding.root
    }

    //Calling the API to take the data from specific USER
    fun getInstitutionData(id_institution: String) {
        CoroutineScope(Dispatchers.IO).launch {
            Api.retrofitService.getInstitutionData(id_institution)
                .enqueue(object : Callback<Institutions> {
                    override fun onResponse(
                        call: Call<Institutions>,
                        response: Response<Institutions>
                    ) {

                        if (response.isSuccessful) {
                            //Fill the view for individual user with the info if response was succesful
                            val institution = response.body()!!
                            Picasso.get().load(institution.logo_src)
                                .into(binding.specificInstitutionImageview);
                            binding.nameSpecificInstitution.text = institution.name
                            binding.specificInstitutionShipments.text = "Envios totales: " +
                                    institution.shipments.toString()
                            binding.specificInstitutionsProblemsSolved.text =
                                "Resueltos totales: " +
                                        institution.problems_solved.toString()

                            val table_users = binding.institutionUserTable


                            //Loop for the users of the institution
                            institution.users.forEach {
                                val row = TableRow(activity)
                                val name_user = TextView(activity)
                                val accepteds_user = TextView(activity)
                                val country_user = TextView(activity)

                                //Username
                                name_user.text = it.nick.toString()
                                //Accepteds user
                                accepteds_user.text = it.accepteds.toString()
                                accepteds_user.setTextColor(Color.parseColor("#32cd32"))
                                accepteds_user.layoutParams =
                                    binding.institutionUserTableAccepteds.layoutParams
                                //Country user
                                country_user.text = it.country
                                country_user.setTextColor(Color.LTGRAY)
                                country_user.layoutParams =
                                    binding.institutionUserTableCountry.layoutParams
                                //Add TV to ROW and ROW to TABLE
                                row.addView(name_user)
                                row.addView(accepteds_user)
                                row.addView(country_user)
                                table_users.addView(row)
                            }


                        }
                    }

                    override fun onFailure(call: Call<Institutions>, t: Throwable) {
                        t.printStackTrace()
                    }


                })
        }
    }


    companion object {

    }
}
