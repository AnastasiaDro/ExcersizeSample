package com.cerebus.excersizesample

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle

class ExcersizeFragment : Fragment(R.layout.your_excersize_fragment) {
    private val viewModel: YouExcersizeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getExcersize()
    }

    private fun subscribeToViewModel() {
        subscribeToHotFlow(Lifecycle.State.CREATED, viewModel.excersizeSharedFlow) { excersize ->
            showExcersize(excersize)
        }
        subscribeToHotFlow(Lifecycle.State.STARTED, viewModel.excersizeUpdateStateSharedFlow) { action ->
            //TODO если успех (полный и неполный) - закрываем карточку
        }
    }

    private fun showExcersize(excersize: Excersize) {
        //TODO тут показываете
    }

    // TODO
    // Какая-то функция, которая обрабатывает нажатия/касания, возможно переопределение касаний
    private fun something() {
        //TODO - обработка x и y - координаты касания, isTarget - попал в цель или нет (в плане шариков прям так может быть)
        viewModel.screenWasTouched(x = 0f, y = 0f, isTarget = true)
    }
}