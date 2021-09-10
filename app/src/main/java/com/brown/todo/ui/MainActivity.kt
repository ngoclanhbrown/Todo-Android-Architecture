package com.brown.todo.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.brown.todo.R
import dev.chrisbanes.insetter.applyInsetter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpEdgeToEdge()
    }


    private fun setUpEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val rootView = window.decorView.findViewById<View>(android.R.id.content)
        rootView.applyInsetter {
            type(statusBars = true, navigationBars = true) {
                margin()
            }
        }
    }

}
