package ru.ashilkin.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val MAPKIT_API_KEY = ""
    private val TARGET_LOCATION: Point = Point(50.35848, 36.353309)


    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        MapKitFactory.setApiKey(MAPKIT_API_KEY)
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_main)

        super.onCreate(savedInstanceState)

        mapview.getMap().move(
            CameraPosition(TARGET_LOCATION, 14.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 5f),
            null
        )

        mercatorView.setZOrderOnTop(true)

        button.setOnClickListener {
            i++
            text.text = "Click count = $i"
        }
    }

    override fun onPause() {
        super.onPause()
        mercatorView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mercatorView.onResume()
    }

    override fun onStop() {
        mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapview.onStart()
    }


}
