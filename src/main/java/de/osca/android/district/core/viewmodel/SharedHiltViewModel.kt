package de.osca.android.district.core.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import de.osca.android.district.core.navigation.LocalNavigationController

/**
 * Allows the use of a Singleton instance of a specified [ViewModel]
 *
 * This function is only allowed to be used in subcomposables of the Dashboard (e.g. widgets in lower navigation hierarchy)
 */
@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
inline fun <reified VM : ViewModel> sharedHiltViewModel(key: String? = null): VM {
    val context = LocalNavigationController.current.context
    if (context is ViewModelStoreOwner) {
        return hiltViewModel(context, key)
    }
    return hiltViewModel(key = key)
}
