package com.rafly_304.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rafly_304.myapplication.databinding.ActivityIntnetBinding

class IntnetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntnetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityIntnetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent=intent
        val text = intent.getIntExtra("text", 0)

        if (text != 0) {

            val text = getString(text)
            binding.getString.text=text
        }

    }
}