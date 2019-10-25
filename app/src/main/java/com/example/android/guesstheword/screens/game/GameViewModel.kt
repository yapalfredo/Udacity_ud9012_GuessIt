package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//extends ViewModel
class GameViewModel : ViewModel() {


    // -------------------  Encapsulations required ------------------------
    //LiveData Variables

    // The current word
    private val _word = MutableLiveData<String>() //internal
    val word: LiveData<String>                    //external
        get() = _word

    // The current score
    private val _score = MutableLiveData<Int>()   //internal
    val score: LiveData<Int>                      //external
        get() = _score

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished
    //----------------------------------------------------------------------


    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            _eventGameFinished.value = true
        } else {
            _word.value = wordList.removeAt(0)
        }
    }

    /** Methods for buttons presses **/
    fun onSkip() {
        //When using MutableLiveData variables, you reference them by adding ".value" after variable name
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    init {
        Log.i("GameViewModel", "GameViewModel created!!!")
        resetList()
        nextWord()
        _score.value = 0
        _eventGameFinished.value = false
    }

    override fun onCleared() {
        super.onCleared()

        Log.i("GameViewModel", "GameViewModel destroyed!!!")
    }

    fun onGameFinishComplete() {
        _eventGameFinished.value = false
    }
}