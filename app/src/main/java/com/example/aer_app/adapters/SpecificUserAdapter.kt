//package com.example.aer_app.adapters
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.aer_app.R
//import com.example.aer_app.models.Problems
//import com.example.aer_app.models.Users
//
//class SpecificUserAdapter(
//    private val user_data: MutableList<Users>,
//    private val problem_size: MutableList<Int>,
//    private val onClickListener: OnClickListener
//) :
//    RecyclerView.Adapter<SpecificUserAdapter.MyViewHolder>() {
//
//    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
//
//
//        fun bind(problem: Problems) {
//            val id = view.findViewById<TextView>(R.id.id_problem_user)
//            val title = view.findViewById<TextView>(R.id.title_problem_user)
//            val state = view.findViewById<TextView>(R.id.state_user_problem)
//
//            //id.text = problem.id_problem.toString()
//            //title.text = problem.title
//            //state.text = problem.
//
//
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false)
//        return MyViewHolder(v)
//    }
//
//    override fun getItemCount(): Int {
//        return user_data.size
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val user = user_data[position]
//        holder.itemView.setOnClickListener {
//            onClickListener.onClick(user)
//        }
//        holder.bind(user_data[position], problem_size[0])
//
//    }
//
//    class OnClickListener(val clickListener: (user: Users) -> Unit) {
//        fun onClick(user: Users) = clickListener(user)
//    }
//
//}