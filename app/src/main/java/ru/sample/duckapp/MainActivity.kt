package ru.sample.duckapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var duckView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        duckView = findViewById(R.id.DuckView)

        Picasso.get().load(R.drawable.placeholder).placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(duckView)
    }
}