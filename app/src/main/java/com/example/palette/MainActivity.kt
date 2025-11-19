package com.example.palette

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityOptionsCompat
import androidx.palette.graphics.Palette
import android.transition.TransitionInflater

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable window content transitions
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Set up exit transition
        val exitTransition = TransitionInflater.from(this)
            .inflateTransition(R.transition.main_exit_transition)
        window.exitTransition = exitTransition

        val toolbar = findViewById<Toolbar>(R.id.appbar)
        setSupportActionBar(toolbar)

//        val statusbar = findViewById<StatusBar>(R.id.statusbar)
//        setSupportActionBar(statusbar)

        // Carga la imagen desde resources
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.image1)

        // Genera la paleta de colores
        Palette.from(bitmap).generate { palette ->
            palette?.let {
                // Escoge un color (puedes probar otros como getMutedColor, getDarkVibrantColor, etc.)
                val colorVibrante = it.getVibrantColor(getColor(R.color.purple_500))
                val colorVibranteDark = it.getDarkVibrantColor(getColor(R.color.purple_500))
                val colorMutante = it.getMutedColor(getColor(R.color.purple_500))
                // Aplica el color a la toolbar
                toolbar.setBackgroundColor(colorVibrante)
//                statusbar.setBackgroundColor(colorVibranteDark)

            }
        }

        val items = ArrayList<Tarjeta>()
        items.add(Tarjeta(R.drawable.image1))
        items.add(Tarjeta(R.drawable.image2))
        items.add(Tarjeta(R.drawable.image3))
        items.add(Tarjeta(R.drawable.image4))
        items.add(Tarjeta(R.drawable.image5))
        items.add(Tarjeta(R.drawable.image6))
        items.add(Tarjeta(R.drawable.image7))
        items.add(Tarjeta(R.drawable.image8))

        val recView = findViewById<RecyclerView>(R.id.recview)
        recView.layoutManager = LinearLayoutManager(this)
        recView.adapter = CardsAdapter(items) { imageView, item ->

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("imageRes", item.imageRes)

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                imageView,  // la vista compartida
                "imageTransition"
            )

            startActivity(intent, options.toBundle())
        }
    }
}