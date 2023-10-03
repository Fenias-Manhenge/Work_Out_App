package com.example.work_out

import android.os.Bundle
import android.os.CountDownTimer
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.example.work_out.databinding.ExerciseScreenBinding

class ExerciseScreen : AppCompatActivity() {
    private val binding by lazy { ExerciseScreenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val typedValue = TypedValue()
        val theme = theme
        theme.resolveAttribute(com.google.android.material.R.attr.colorSurfaceContainerHigh, typedValue, true)
        val color: Int = typedValue.data

        window.statusBarColor = color

        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolBar.setNavigationOnClickListener {

            onBackPressed()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                finish()
            }
        })

        timer.start()
    }

    private val timer = object : CountDownTimer(2000, 1000){
        override fun onTick(remaining: Long) {
            val seconds = (remaining / 1000).toString()
            binding.tvSeconds.text = seconds
            binding.pbSeconds.progress = seconds.toInt()
        }

        override fun onFinish() {
            val myConstraintLayoutID = binding.exerciseScreen

            val ivWorkOut = ImageView(this@ExerciseScreen)
            ivWorkOut.background = AppCompatResources.getDrawable(this@ExerciseScreen, R.drawable.image_view_workout)
            ivWorkOut.id = View.generateViewId()

            val ivLayoutParams = ConstraintLayout.LayoutParams(900, 1200)
            ivWorkOut.layoutParams = ivLayoutParams

            myConstraintLayoutID.addView(ivWorkOut)

            val set = ConstraintSet()
            set.clone(myConstraintLayoutID)

            set.connect(
                ivWorkOut.id, ConstraintSet.START,
                myConstraintLayoutID.id, ConstraintSet.START
            )
            set.connect(
                ivWorkOut.id, ConstraintSet.END,
                myConstraintLayoutID.id, ConstraintSet.END
            )
            set.connect(
                ivWorkOut.id, ConstraintSet.TOP,
                binding.barLayout.id, ConstraintSet.BOTTOM
            )
            set.connect(
                ivWorkOut.id, ConstraintSet.BOTTOM,
                binding.tvExplainText.id, ConstraintSet.TOP
            )
            set.connect(
                binding.frameLayout.id, ConstraintSet.BOTTOM,
                myConstraintLayoutID.id, ConstraintSet.BOTTOM, 100
            )

            set.applyTo(myConstraintLayoutID)
        }
    }
}