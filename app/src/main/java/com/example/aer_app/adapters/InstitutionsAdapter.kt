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
    private val user_data: MutableList<Users>,
    private val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<InstitutionsAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


        fun bind(user: Users) {




        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false)
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
        holder.bind(user_data[position])

    }

    class OnClickListener(val clickListener: (user: Users) -> Unit) {
        fun onClick(user: Users) = clickListener(user)
    }

}