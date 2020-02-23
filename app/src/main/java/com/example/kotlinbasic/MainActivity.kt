package com.example.kotlinbasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var mModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupViewModel()
        rxKotlinExample()
    }
    fun rxKotlinExample(){
        val list = listOf("Alpha","Beta","Gamma","Delta","Epsilon")

        list.toObservable()
            .filter{it.length >= 5}
            .subscribeBy(
                onNext = { Log.d("My Tag: ",it)},
                onError = {Log.d("My Tag: ",it.message)},
                onComplete = {Log.d("My Tag: ","Complete!")}
            )



    }
    fun setupViewModel() {
        mModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val randomNumberObserver = Observer<Int> { newNumber ->
            // Update the ui with current data
            textView.text = "Current Number : $newNumber"
        }

        mModel.currentRandomNumber.observe(this, randomNumberObserver)

        button.setOnClickListener {
            mModel.currentRandomNumber.value = Random().nextInt(50)
        }

        val sum = {a: Int, b:Int -> a+b}//shorthand lambda, infer type

        val type_sum:(Int, Int) -> Int = {a,b -> a+b}//lambda, declared types

        Log.d("TAG: ","Sum: "+type_sum(2,3))
        //passing anonymus funtion as parameter

        doArithmetic(type_sum)

    }
    fun doArithmetic(operation: (Int,Int) -> Int){
        val first = 0;
        val second = 9;
        Log.d("TAG: ","Result: "+operation(0,9))


        var user = User()

        user.name = "Maria"

        user transformName "Sanchez"

        Log.d("TAG: ", user.name)
    }

    class User{
        var name:String?=null

        init {
            name = ""
        }

        infix fun transformName(str:String) {name = name+" "+str}
    }
}


