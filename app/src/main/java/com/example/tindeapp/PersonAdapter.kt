package com.example.tindeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PersonAdapter(
    private var people: List<PersonModel>
) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    fun updateList(newList: List<PersonModel>) {
        people = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_person, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = people[position]
        holder.bind(person)
    }

    override fun getItemCount(): Int = people.size

    class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.personName)
        private val personImageView: ImageView = itemView.findViewById(R.id.personImage)

        fun bind(person: PersonModel) {
            nameTextView.text = person.name
            personImageView.setImageResource(person.images[0]) // Muestra la primera imagen
        }
    }
}
