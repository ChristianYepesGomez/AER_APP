package com.example.aer_app.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.futured.donut.DonutProgressView
import app.futured.donut.DonutSection
import com.example.aer_app.R
import com.example.aer_app.models.Institutions
import com.example.aer_app.models.Users

class InstitutionsAdapter(
    private val institution_data: MutableList<Institutions>,
    private val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<InstitutionsAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


        fun bind(institution: Institutions) {

            val name = view.findViewById<TextView>(R.id.name_institution_item)
            val users = view.findViewById<TextView>(R.id.number_users_institution)
            val solved = view.findViewById<TextView>(R.id.solved_problems_institutions)

            users.text = institution.users.size.toString()
            name.text = institution.name
            solved.text = institution.problems_solved.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_institutions, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return institution_data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val institution = institution_data[position]
        holder.itemView.setOnClickListener {
            onClickListener.onClick(institution)
        }
        holder.bind(institution_data[position])

    }

    class OnClickListener(val clickListener: (institution: Institutions) -> Unit) {
        fun onClick(institution: Institutions) = clickListener(institution)
    }

}