package com.example.tindeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var imageView: ImageView
    private lateinit var personName: TextView
    private lateinit var buttonLike: Button
    private lateinit var buttonDislike: Button
    private lateinit var buttonShowFavorites: Button
    private lateinit var progressBar: ProgressBar
    private var currentImageIndex = 0 // Controlar el índice de la imagen actual

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el layout para este fragmento
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Vincular las vistas usando view.findViewById
        imageView = view.findViewById(R.id.personImage)
        personName = view.findViewById(R.id.personName)
        buttonLike = view.findViewById(R.id.buttonLike)
        buttonDislike = view.findViewById(R.id.buttonDislike)
        buttonShowFavorites = view.findViewById(R.id.buttonShowFavorites)
        progressBar = view.findViewById(R.id.progressBar)

        // Inicializa el ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        // Observa el índice de la persona actual
        viewModel.currentPersonIndex.observe(viewLifecycleOwner, Observer { index ->
            val person = viewModel.personList.value?.get(index)
            if (person != null) {
                updateCurrentPerson(person)
            }
        })

        // Botón de "Me gusta"
        buttonLike.setOnClickListener {
            viewModel.likeCurrentPerson()
        }

        // Botón de "No me gusta"
        buttonDislike.setOnClickListener {
            viewModel.dislikePerson()
        }

        // Mostrar la lista de personas que te gustan
        buttonShowFavorites.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, LikedPeopleFragment())
                .addToBackStack(null)
                .commit()
        }

        // Manejo de deslizamiento en la imagen para cambiar entre imágenes
        imageView.setOnTouchListener { _, event ->
            handleTouch(event)
            true
        }
    }

    private fun updateCurrentPerson(person: PersonModel) {
        currentImageIndex = 0 // Reiniciar el índice de la imagen al cambiar de persona
        personName.text = person.name
        updateImageAndProgressBar(person)
    }

    // Método para manejar gestos de deslizamiento (cambiar imágenes)
    private fun handleTouch(event: MotionEvent) {
        val width = imageView.width

        if (event.action == MotionEvent.ACTION_UP) {
            val person = viewModel.personList.value?.get(viewModel.currentPersonIndex.value ?: 0)
            if (person != null) {
                if (event.x < width / 2) {
                    // Deslizar hacia la izquierda (anterior imagen)
                    previousImage(person)
                } else {
                    // Deslizar hacia la derecha (siguiente imagen)
                    nextImage(person)
                }
            }
        }
    }

    // Actualizar imagen y barra de progreso
    private fun updateImageAndProgressBar(person: PersonModel) {
        imageView.setImageResource(person.images[currentImageIndex])
        progressBar.progress = (currentImageIndex + 1) * 33 // Barra dividida en 3 partes
    }

    // Cambiar a la siguiente imagen
    private fun nextImage(person: PersonModel) {
        if (currentImageIndex < person.images.size - 1) {
            currentImageIndex++
            updateImageAndProgressBar(person)
        }
    }

    // Cambiar a la imagen anterior
    private fun previousImage(person: PersonModel) {
        if (currentImageIndex > 0) {
            currentImageIndex--
            updateImageAndProgressBar(person)
        }
    }
}


