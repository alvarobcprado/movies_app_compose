package com.example.moviesapp.core

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class ActionBlocViewModel<S, A, E>(private val initialState: S) :
    BlocViewModel<S, E>(initialState) {
    private val actionFlow = MutableSharedFlow<A>()
    val action: SharedFlow<A> get() = actionFlow
    protected fun emitAction(action: A) {
        actionFlow.tryEmit(action)
    }
}