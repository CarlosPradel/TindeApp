package com.example.tindeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _personList = MutableLiveData<List<PersonModel>>()
    val personList: LiveData<List<PersonModel>> get() = _personList

    private val _currentPersonIndex = MutableLiveData<Int>()
    val currentPersonIndex: LiveData<Int> get() = _currentPersonIndex

    private val _currentImageIndex = MutableLiveData<Int>() // Índice de la imagen actual
    val currentImageIndex: LiveData<Int> get() = _currentImageIndex

    private val _likedPeople = MutableLiveData<MutableList<PersonModel>>()
    val likedPeople: LiveData<MutableList<PersonModel>> get() = _likedPeople

    init {
        // Lista inicial de personas
        _personList.value = listOf(
            PersonModel("Mamen Saavedra", listOf(R.drawable.mamen1, R.drawable.mamen2, R.drawable.mamen3)),
            PersonModel("Juan Carlos Medrano", listOf(R.drawable.medrano1, R.drawable.medrano2, R.drawable.medrano3)),
            PersonModel("Percy Fernandez", listOf(R.drawable.percy1, R.drawable.percy2, R.drawable.percy3)),
            PersonModel("Jhonny Fernandez", listOf(R.drawable.jhonny1, R.drawable.jhonny2, R.drawable.jhonny3)),
            PersonModel("Samuel Doria Medina", listOf(R.drawable.doria1, R.drawable.doria2, R.drawable.doria3)),
            PersonModel("Fernando Camacho", listOf(R.drawable.camacho1, R.drawable.camacho2, R.drawable.camacho3)),
            PersonModel("Tuto Quiroga", listOf(R.drawable.tuto1, R.drawable.tuto2, R.drawable.tuto3)),
            PersonModel("Carlos Mesa", listOf(R.drawable.mesa1, R.drawable.mesa2, R.drawable.mesa3)),
            PersonModel("Evo Morales", listOf(R.drawable.evo1, R.drawable.evo2, R.drawable.evo3)),
            PersonModel("Lucho Arce", listOf(R.drawable.arce1, R.drawable.arce2, R.drawable.arce3)),
            // Añadir más personas según necesites
        )
        _currentPersonIndex.value = 0
        _currentImageIndex.value = 0 // Empieza con la primera imagen
        _likedPeople.value = mutableListOf()
    }

    // Método para dar "Me gusta" a la persona actual
    fun likeCurrentPerson() {
        val currentPerson = _personList.value?.get(_currentPersonIndex.value ?: 0)
        currentPerson?.let {
            val liked = _likedPeople.value ?: mutableListOf()
            if (!liked.contains(it)) {
                liked.add(it)
                _likedPeople.value = liked
            }
        }
        nextPerson() // Cambia a la siguiente persona después de dar "Me gusta"
    }

    // Método para avanzar a la siguiente persona
    fun nextPerson() {
        val currentIndex = _currentPersonIndex.value ?: 0
        if (currentIndex < _personList.value!!.size - 1) {
            _currentPersonIndex.value = currentIndex + 1
        } else {
            _currentPersonIndex.value = 0 // Reinicia si llega al final de la lista
        }
        _currentImageIndex.value = 0 // Reinicia la imagen a la primera cuando cambia de persona
    }

    // Método para "No me gusta" (similar a nextPerson)
    fun dislikePerson() {
        nextPerson() // Avanza a la siguiente persona cuando presionas "No me gusta"
    }

    // Métodos para controlar las imágenes
    fun nextImage() {
        val currentIndex = _currentImageIndex.value ?: 0
        val person = _personList.value?.get(_currentPersonIndex.value ?: 0)
        person?.let {
            if (currentIndex < it.images.size - 1) {
                _currentImageIndex.value = currentIndex + 1
            }
        }
    }

    fun previousImage() {
        val currentIndex = _currentImageIndex.value ?: 0
        if (currentIndex > 0) {
            _currentImageIndex.value = currentIndex - 1
        }
    }
}

