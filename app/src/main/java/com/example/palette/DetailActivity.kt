package com.example.palette

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.palette.graphics.Palette
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.transition.TransitionInflater

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable window content transitions
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Set up enter transition
        val enterTransition = TransitionInflater.from(this)
            .inflateTransition(R.transition.detail_enter_transition)
        window.enterTransition = enterTransition

        // Set up shared element transition
        val sharedElementTransition = TransitionInflater.from(this)
            .inflateTransition(R.transition.shared_element_transition)
        window.sharedElementEnterTransition = sharedElementTransition

        val toolbar = findViewById<Toolbar>(R.id.detailToolbar)
        setSupportActionBar(toolbar)

        val imageView = findViewById<ImageView>(R.id.detailImage)
        val colorsContainer = findViewById<LinearLayout>(R.id.colorsContainer)

        val imageRes = intent.getIntExtra("imageRes", 0)
        imageView.setImageResource(imageRes)

        // Decodifica el bitmap para generar la paleta
        val bitmap = BitmapFactory.decodeResource(resources, imageRes)

        Palette.from(bitmap).generate { palette ->
            palette?.let {
                val swatches = listOf(
                    "Light Vibrant" to it.getLightVibrantColor(0xFFE1BEE7.toInt()),
                    "Muted" to it.getMutedColor(0xFF8D6E63.toInt()),
                    "Dark Muted" to it.getDarkMutedColor(0xFF5D4037.toInt()),
                    "Light Muted" to it.getLightMutedColor(0xFFD7CCC8.toInt())
                )

                colorsContainer.removeAllViews()
                for ((name, color) in swatches) {
                    val view = layoutInflater.inflate(R.layout.color_item, colorsContainer, false)
                    view.findViewById<View>(R.id.colorPreview).setBackgroundColor(color)
                    view.findViewById<TextView>(R.id.colorName).text = name
                    colorsContainer.addView(view)
                }

                // Cambia color de la toolbar según el dominante
                val dominant = it.getVibrantColor(getColor(R.color.purple_500))
                toolbar.setBackgroundColor(dominant)
            }
        }

    }
}
