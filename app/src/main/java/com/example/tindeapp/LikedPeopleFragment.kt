package com.example.tindeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LikedPeopleFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: PersonAdapter
    private lateinit var recyclerViewLikedPeople: RecyclerView // Declara el RecyclerView aquí

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        return inflater.inflate(R.layout.fragment_liked_people, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        // Vincula el RecyclerView
        recyclerViewLikedPeople = view.findViewById(R.id.recyclerViewLikedPeople)

        // Configura el RecyclerView
        adapter = PersonAdapter(emptyList())
        recyclerViewLikedPeople.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewLikedPeople.adapter = adapter

        // Observa la lista de personas que te gustan
        viewModel.likedPeople.observe(viewLifecycleOwner) { likedPeople ->
            adapter.updateList(likedPeople)
        }
    }
}
