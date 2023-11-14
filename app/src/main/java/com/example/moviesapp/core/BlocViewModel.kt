package com.example.moviesapp.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class BlocViewModel<E, S>(private val initialState: S) : ViewModel() {
    private val stateFlow = MutableStateFlow<S>(this.initialState)
    private val eventFlow = MutableSharedFlow<E>()
    val state: StateFlow<S> get() = stateFlow
    val event: SharedFlow<E> get() = eventFlow

    protected inline fun <reified Event : E> on(noinline callback: suspend (E) -> Unit) {
        event.filterIsInstance<Event>().onEach { callback(it) }.launchIn(viewModelScope)
    }

    protected fun setState(state: S) {
        stateFlow.value = state
    }

    fun addEvent(event: E) {
        viewModelScope.launch {
            eventFlow.emit(event)
        }
    }
}