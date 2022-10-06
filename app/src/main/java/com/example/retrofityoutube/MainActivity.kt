package com.example.retrofityoutube


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofityoutube.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var myAdapter: MyAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var binding: ActivityMainBinding

     val BASE_URL = "https://jsonplaceholder.typicode.com/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.recyclerviewUsers.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerviewUsers.layoutManager = linearLayoutManager
            getMyData()
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object: Callback<List<MyDataItem>?>{
            override fun onResponse(
                call: Call<List<MyDataItem>?>,
                response: Response<List<MyDataItem>?>
            ) {
               val responseBody = response.body()!!

                myAdapter = MyAdapter(baseContext,responseBody)
                myAdapter.notifyDataSetChanged()
                binding.recyclerviewUsers.adapter = myAdapter
            }

            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure" + t.message)
            }

        })
    }
}