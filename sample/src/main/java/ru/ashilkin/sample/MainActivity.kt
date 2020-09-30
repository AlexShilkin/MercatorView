package ru.ashilkin.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mercatorView.setZOrderOnTop(true)
        button.setOnClickListener {
            i++
            text.text = "Click count = $i"
        }
        mercatorView
    }

    override fun onPause() {
        super.onPause()
        mercatorView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mercatorView.onResume()
    }

}
