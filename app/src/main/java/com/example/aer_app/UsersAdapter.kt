package com.example.aer_app

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.futured.donut.DonutProgressView
import app.futured.donut.DonutSection
import com.example.aer_app.models.Users

class UsersAdapter(private val data: List<Users>) :
    RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(user: Users) {
            val nick = view.findViewById<TextView>(R.id.recyler_user_nick)
            val institution = view.findViewById<TextView>(R.id.recyler_user_institution)
            val id = view.findViewById<TextView>(R.id.recyler_user_id)
            val name = view.findViewById<TextView>(R.id.recyler_user_name_country)
            val donut = view.findViewById<DonutProgressView>(R.id.donut_view_accepteds)
            val donut_accepteds_text = view.findViewById<TextView>(R.id.user_donut_accepteds)

            nick.text = user.nick
            name.text = user.name

            if (user.institution.isEmpty()) {

                institution.text = "Sin establecer"

            } else {
                
                institution.text = user.institution

            }

            id.text = "Usuario numero " + user.id_user.toString().trim()


            if (!user.country.equals("Sin establecer") and user.country.isNotEmpty()) {
                name.text = user.name + " | " + user.country
            }

            donut_accepteds_text.text = user.accepteds.toString() + "/" + user.intents.toString()

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
            donut.cap = (user.accepteds + user.intents).toFloat()
            donut.submitData(listOf(section_accepteds, section_intents))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }

}