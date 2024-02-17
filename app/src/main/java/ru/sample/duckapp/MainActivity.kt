package ru.sample.duckapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.sample.duckapp.domain.Duck
import ru.sample.duckapp.infra.Api

class MainActivity : AppCompatActivity() {

    private lateinit var duckView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        duckView = findViewById(R.id.DuckView)

        findViewById<Button>(R.id.button)
            .setOnClickListener {
                makeRequest()
            }




        //val call: Call<Duck> =



    }

    fun makeRequest(){
        Api.ducksApi.getRandomDuck().enqueue(object : Callback<Duck> {

            override fun onResponse(call: Call<Duck>, response: Response<Duck>) {
                if (response.isSuccessful) {
                    val duck: Duck? = response.body()

                    duck?.url?.let { setDuckImage(it) };
                } else {
                    println("Request failed with code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Duck>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }

    fun setDuckImage(url: String){
        Picasso.get().load(url).placeholder(R.drawable.placeholder)
            .error(R.drawable.owl)
            .into(duckView)
    }
}