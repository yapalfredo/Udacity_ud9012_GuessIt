package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

//extends ViewModel
class GameViewModel : ViewModel() {

    companion object {
        //These will represent different important times in the game, such as the duration of the game

        //This is when the game is finished
        private const val DONE = 0L

        //This is the number in milliseconds in a second
        private const val ONE_SECOND = 1000L

        //This is the total duration of the game
        private const val COUNTDOWN_TIME = 60000L
    }

    private val timer: CountDownTimer


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

    // The current time
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    val currentTimeString = Transformations.map(currentTime, {
        DateUtils.formatElapsedTime(it)
    })

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
            resetList()
        }
        _word.value = wordList.removeAt(0)

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

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(p0: Long) {
                _currentTime.value = p0 / ONE_SECOND
            }

            override fun onFinish() {
                _currentTime.value = DONE
                _eventGameFinished.value = true
            }
        }
        timer.start()
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()

        Log.i("GameViewModel", "GameViewModel destroyed!!!")
    }

    fun onGameFinishComplete() {
        _eventGameFinished.value = false
    }
}