package ru.sample.duckapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.sample.duckapp.domain.Duck
import ru.sample.duckapp.infra.Api
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    private lateinit var duckView: ImageView
    private lateinit var errorField: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        duckView = findViewById(R.id.DuckView)
        errorField = findViewById(R.id.errorField)

        findViewById<Button>(R.id.button)
            .setOnClickListener {
                clearError()
                var fieldText = findViewById<EditText>(R.id.duckId).text;
                if (fieldText.toString().equals("")){
                    makeRequest()
                } else {
                    var id: Int = Integer.parseInt(fieldText.toString())
                    makeRequest(id)
                }

            }

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

    fun makeRequest(id: Int){
        Api.ducksApi.getDuckById(id).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        // display the image data in a ImageView or save it
                        val bitmap = BitmapFactory.decodeStream(response.body()?.byteStream())
                        duckView.setImageBitmap(bitmap);
                    }
                } else {
                    errorField.text = "Утки для картинки с таким индеком не существует \n Получи сову"
                    Picasso.get().load(R.drawable.owl)
                        .placeholder(R.drawable.placeholder)
                        .into(duckView)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })}

    fun clearError(){
        errorField.text = ""
    }
    fun setDuckImage(url: String){
        Picasso.get().load(url).placeholder(R.drawable.placeholder)
            .error(R.drawable.owl)
            .into(duckView)
    }
}