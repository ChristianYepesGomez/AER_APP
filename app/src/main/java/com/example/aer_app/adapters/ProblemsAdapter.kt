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
import com.example.aer_app.models.Problems
import com.example.aer_app.models.Users

class ProblemsAdapter(
    private val problem_data: MutableList<Problems>,
    private val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<ProblemsAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


        fun bind(problem: Problems) {
            val id = view.findViewById<TextView>(R.id.id_problem_recycler)
            val title = view.findViewById<TextView>(R.id.title_problem_recycler)
            val percentage_difficulty =
                view.findViewById<TextView>(R.id.percentage_difficulty_problem_recycler)

            println(problem)
            id.text = problem.id_problem.toString()
            title.text = problem.title
            percentage_difficulty.text = problem.percentage_users_completed.toString() + "%"

            if (problem.percentage_users_completed >= 80) {
                percentage_difficulty.setTextColor((Color.GREEN))
            } else if (problem.percentage_users_completed in 61..79) {
                percentage_difficulty.setTextColor(Color.parseColor("#e5de00"))
            } else if (problem.percentage_users_completed in 30..60) {
                percentage_difficulty.setTextColor(Color.parseColor("#FC5800"))
            } else {
                percentage_difficulty.setTextColor((Color.RED))
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_problems, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return problem_data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val problem = problem_data[position]
        holder.itemView.setOnClickListener {
            onClickListener.onClick(problem)
        }
        holder.bind(problem_data[position])

    }

    class OnClickListener(val clickListener: (problem: Problems) -> Unit) {
        fun onClick(problem: Problems) = clickListener(problem)
    }

}