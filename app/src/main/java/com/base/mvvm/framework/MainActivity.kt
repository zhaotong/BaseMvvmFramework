package com.base.mvvm.framework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.ParameterizedType

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener {
            test()
        }

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