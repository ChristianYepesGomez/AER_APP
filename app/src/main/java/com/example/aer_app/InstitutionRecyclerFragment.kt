package com.example.aer_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aer_app.adapters.InstitutionsAdapter
import com.example.aer_app.databinding.FragmentInstitutionRecyclerBinding
import com.example.aer_app.models.Institutions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InstitutionRecyclerFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: InstitutionsAdapter
    private var _binding: FragmentInstitutionRecyclerBinding? = null
    private val binding get() = _binding!!
    private var institution_list = mutableListOf<Institutions>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInstitutionRecyclerBinding.inflate(inflater, container, false)
        manager = LinearLayoutManager(context);
        initRecycler()
        getInstitutionData()

        return binding.root
    }

    private fun initRecycler() {
        recyclerView = binding.recyclerViewProblems.apply {
            myAdapter = InstitutionsAdapter(institution_list, InstitutionsAdapter.OnClickListener {
                val bundle = Bundle()
                bundle.putString("id", it.id.toString())
                val transaction = parentFragmentManager.beginTransaction()
                val fragment = InstitutionFragment()
                fragment.arguments = bundle
                transaction.addToBackStack(null)
                transaction.replace(R.id.frame_layout, fragment)
                transaction.commit()
            })
            layoutManager = manager
            adapter = myAdapter
        }
    }

    fun getInstitutionData() {
        CoroutineScope(Dispatchers.IO).launch {
            Api.retrofitService.getInstitutionsData()
                .enqueue(object : Callback<MutableList<Institutions>> {
                    override fun onResponse(
                        call: Call<MutableList<Institutions>>,
                        response: Response<MutableList<Institutions>>
                    ) {

                        if (response.isSuccessful) {
                            institution_list.clear()
                            institution_list.addAll(response.body()!!)
                            myAdapter.notifyDataSetChanged()

                        }
                    }

                    override fun onFailure(call: Call<MutableList<Institutions>>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
        }

    }

    companion object {


    }
}