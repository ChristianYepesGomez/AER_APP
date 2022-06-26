package com.example.aer_app.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.futured.donut.DonutProgressView
import app.futured.donut.DonutSection
import com.example.aer_app.Api
import com.example.aer_app.R
import com.example.aer_app.models.Institutions
import com.example.aer_app.models.Users
import com.example.aer_app.models.UsersNoProblems
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersAdapter(
    private val user_data: MutableList<UsersNoProblems>,
    private val problem_size: MutableList<Int>,
    private val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


        fun bind(user: UsersNoProblems, problem_size: Int) {
            val nick = view.findViewById<TextView>(R.id.recyler_user_nick)
            val institution = view.findViewById<TextView>(R.id.recyler_user_institution)
            val id = view.findViewById<TextView>(R.id.recyler_user_id)
            val name = view.findViewById<TextView>(R.id.recyler_user_name_country)
            val donut_accepteds = view.findViewById<DonutProgressView>(R.id.problem_language_donut)
            val donut_accepteds_total =
                view.findViewById<DonutProgressView>(R.id.donut_view_accepteds_total)
            val donut_accepteds_text = view.findViewById<TextView>(R.id.user_donut_accepteds)
            val donut_accepteds_total_text =
                view.findViewById<TextView>(R.id.user_donut_accepteds_total)
            val profileImage = view.findViewById<CircleImageView>(R.id.circleImageViewUserItem)

            Picasso.get().load(user.logo_src).into(profileImage);

            nick.text = user.nick
            name.text = user.name

            if (user.institution.isEmpty()) {

                institution.text = "Sin establecer"

            } else {

                CoroutineScope(Dispatchers.IO).launch {
                    Api.retrofitService.getInstitutionData(user.institution)
                        .enqueue(object : Callback<Institutions> {
                            override fun onResponse(
                                call: Call<Institutions>,
                                response: Response<Institutions>
                            ) {

                                if (response.isSuccessful) {

                                    institution.text = response.body()!!.name

                                }
                            }

                            override fun onFailure(call: Call<Institutions>, t: Throwable) {
                                t.printStackTrace()
                            }


                        })
                }


            }

            id.text = "Usuario numero " + user.id_user.toString().trim()


            if (!user.country.equals("Sin establecer") and user.country.isNotEmpty()) {
                name.text = user.name + " | " + user.country
            }

            //Donut for the intents / accepteds for user

            if (!user.accepteds.equals(0)) {

                donut_accepteds_text.text =
                    (user.accepteds.toDouble() / user.intents * 100).toInt().toString() + "%"

                donut_accepteds_total_text.text =
                    (user.accepteds.toDouble() / problem_size * 100).toInt().toString() + "%"

            } else {
                donut_accepteds_text.text = user.accepteds.toString() + "%"
                donut_accepteds_total_text.text = user.accepteds.toString() + "%"
            }

            val section_accepteds = DonutSection(
                name = "accepteds",
                color = Color.GREEN,
                amount = user.accepteds.toFloat()
            )

            val section_intents = DonutSection(
                name = "intents",
                color = Color.WHITE,
                amount = user.intents.toFloat() - user.accepteds.toFloat()
            )
            donut_accepteds.cap = (user.accepteds + user.intents).toFloat()
            donut_accepteds.submitData(listOf(section_accepteds, section_intents))

            //Donut for the accepteds / total_problems for user

            val section_accepteds_total = DonutSection(
                name = "accepteds",
                color = Color.CYAN,
                amount = user.accepteds.toFloat()
            )

            val section_total_problems = DonutSection(
                name = "total_problems",
                color = Color.WHITE,
                amount = problem_size.toFloat()
            )
            donut_accepteds_total.cap = (problem_size).toFloat()
            donut_accepteds_total.submitData(
                listOf(
                    section_accepteds_total,
                    section_total_problems
                )
            )


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return user_data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = user_data[position]
        holder.itemView.setOnClickListener {
            onClickListener.onClick(user)
        }
        holder.bind(user_data[position], problem_size[0])

    }

    class OnClickListener(val clickListener: (user: UsersNoProblems) -> Unit) {
        fun onClick(user: UsersNoProblems) = clickListener(user)
    }

}