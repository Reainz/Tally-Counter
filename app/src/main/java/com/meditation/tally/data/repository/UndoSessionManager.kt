package com.meditation.tally.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UndoSessionManager {
    private val _canUndo = MutableStateFlow(false)
    val canUndo: StateFlow<Boolean> = _canUndo.asStateFlow()

    fun markIncrement() {
        _canUndo.value = true
    }

    fun clear() {
        _canUndo.value = false
    }
}

