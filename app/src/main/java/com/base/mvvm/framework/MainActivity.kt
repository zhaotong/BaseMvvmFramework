package com.base.mvvm.framework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.base.mvvm.libcommon.http.DataResult
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.ParameterizedType

class MainActivity : AppCompatActivity() {

    var viewModel:MainViewModel?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener {
//            test()
            load()
        }

        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)




    }


    private fun load(){
        viewModel?.load()?.observe(this, Observer {
            if (it is DataResult.Success){

            }
            Toast.makeText(this,it.toString(),Toast.LENGTH_LONG).show()
        })
    }


    private fun test() {
        val test = TestType<String>()
        test.getType()

        val genericSuperclass = test.javaClass.genericSuperclass

        if (genericSuperclass is ParameterizedType) {
            genericSuperclass.actualTypeArguments
        }

        Log.d("TAG", "test: ")
    }
}