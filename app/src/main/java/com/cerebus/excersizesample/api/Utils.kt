package com.cerebus.excersizesample.api

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

fun <T> Fragment.subscribeToHotFlow(lifecycleState: Lifecycle.State, observable: SharedFlow<T>, action: (data: T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(lifecycleState) {
            observable.collect { data ->
                action.invoke(data)
            }
        }
    }
}
