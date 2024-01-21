package com.example.work_out

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.work_out.databinding.FinishBinding

class Finish : AppCompatActivity() {

    private val binding by lazy { FinishBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(binding.root)

        binding.btnFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }
    }
}