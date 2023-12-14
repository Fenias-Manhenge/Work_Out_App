package com.example.work_out

import android.app.Dialog
import android.content.Intent
import android.graphics.Outline
import android.graphics.Rect
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.TypedValue
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.work_out.databinding.ExerciseScreenBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Locale

class ExerciseScreen : AppCompatActivity(), TextToSpeech.OnInitListener {

    inner class RecyclerViewDecoration(private val offset: Int): RecyclerView.ItemDecoration(){
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.right = offset
        }
    }

    //
    // Global variables
    //
    private val binding by lazy { ExerciseScreenBinding.inflate(layoutInflater) } // View Binding
    val ivWorkOut by lazy { ImageView(this) }
    val rvExerciseNumber by lazy { RecyclerView(this) }
    private val tts by lazy { TextToSpeech(this, this) } // Text to Speech
    private val data = ExerciseData.dataList()
    private var position = 0
    var myConstraintLayout: ConstraintLayout? = null
    val set = ConstraintSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        myConstraintLayout = binding.exerciseScreen

        val typedValue = TypedValue()
        val theme = theme
        theme.resolveAttribute(com.google.android.material.R.attr.colorSurfaceContainerHigh, typedValue, true)
        val color: Int = typedValue.data

        window.statusBarColor = color

        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolBar.setNavigationOnClickListener {
            onBackPressed()
            timerPrepare.cancel()

            backDialog()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                backDialog()
            }
        })

        timerPrepare.start()
    }

    private val timerPrepare = object : CountDownTimer(4000, 1000){
        override fun onTick(remaining: Long) {
            val seconds = (remaining / 1000)
            binding.tvSeconds.text = seconds.toString()
            binding.pbSeconds.progress = seconds.toInt()
            binding.tvExerciseName.text = data[position].name
        }

        override fun onFinish() {
            timer()

            data[position].isSelected = true
            ExerciseAdapter(data).notifyItemChanged(position)

            myConstraintLayout?.removeView(binding.tvUpComingExercise)
            myConstraintLayout?.removeView(binding.tvExerciseName)

            ivWorkOut.apply {
                id = View.generateViewId()
                scaleType = ImageView.ScaleType.FIT_XY
                setImageResource(data[position].image)
                layoutParams = LayoutParams(950, 1200)

                outlineProvider = object : ViewOutlineProvider() {
                    override fun getOutline(view: View?, outline: Outline?) {
                        val cornerRadius = resources.getDimension(R.dimen.corner_radius) // Adjust as needed
                        outline?.setRoundRect(0, 0, view!!.width, view.height, cornerRadius)
                    }
                }
                clipToOutline = true
            }

            rvExerciseNumber.apply {
                id = View.generateViewId()
                layoutManager = LinearLayoutManager(this@ExerciseScreen, RecyclerView.HORIZONTAL, false)
                layoutParams = LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
                )
                addItemDecoration(RecyclerViewDecoration(15))
                adapter = ExerciseAdapter(data)
            }
            binding.tvExplainText.text = data[position].name

            position++

            if (position >= data.size)
                cancel()

            myConstraintLayout?.addView(ivWorkOut)
            myConstraintLayout?.addView(rvExerciseNumber)

            set.clone(myConstraintLayout)

            myConstraintLayout?.let {
                set.apply {
                    connect(
                        ivWorkOut.id, ConstraintSet.START,
                        it.id, ConstraintSet.START
                    )
                    connect(
                        ivWorkOut.id, ConstraintSet.END,
                        it.id, ConstraintSet.END
                    )
                    connect(
                        ivWorkOut.id, ConstraintSet.TOP,
                        binding.barLayout.id, ConstraintSet.BOTTOM
                    )
                    connect(
                        ivWorkOut.id, ConstraintSet.BOTTOM,
                        binding.tvExplainText.id, ConstraintSet.TOP
                    )
                    connect(
                        binding.tvExplainText.id, ConstraintSet.BOTTOM,
                        binding.frameLayout.id, ConstraintSet.TOP
                    )
                    connect(
                        binding.frameLayout.id, ConstraintSet.BOTTOM,
                        it.id, ConstraintSet.BOTTOM, 200
                    )
                    connect(
                        rvExerciseNumber.id, ConstraintSet.TOP,
                        binding.frameLayout.id, ConstraintSet.BOTTOM, 50
                    )
                    connect(
                        rvExerciseNumber.id, ConstraintSet.START,
                        binding.frameLayout.id, ConstraintSet.START
                    )
                    connect(
                        rvExerciseNumber.id, ConstraintSet.END,
                        binding.frameLayout.id, ConstraintSet.END
                    )
                }
            }
            set.applyTo(myConstraintLayout)
        }
    }

    // Second timer
    private fun timer() {
        object : CountDownTimer(10000, 1000) {
            override fun onTick(remaining: Long) {
                val seconds = (remaining / 1000)
                binding.tvSeconds.text = seconds.toString()
                binding.pbSeconds.progress = seconds.toInt()
            }

            override fun onFinish() {
                rvExerciseNumber.addItemDecoration(RecyclerViewDecoration(-15))

                data[position - 1].isSelected = false
                data[position - 1].isCompleted = true
                ExerciseAdapter(data).notifyItemChanged(position - 1)

                myConstraintLayout?.apply {
                    removeView(ivWorkOut)
                    removeView(rvExerciseNumber)
                    addView(binding.tvUpComingExercise)
                    addView(binding.tvExerciseName)
                }

                binding.tvExplainText.text = "Get ready for"

                set.apply {
                    clone(myConstraintLayout)
                    setMargin(binding.frameLayout.id, ConstraintSet.BOTTOM, 850)
                    applyTo(myConstraintLayout)
                }

                if(position >= data.size - 7)
                    startActivity(Intent(this@ExerciseScreen, Finish::class.java))

                timerPrepare.start()
            }
        }.start()
    }

    //
    // Text to Speech section
    //
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA)
                Toast.makeText(this, "Language not support!", Toast.LENGTH_LONG).show()
        } else
            Toast.makeText(this, "Initialization failed", Toast.LENGTH_LONG).show()
    }

    private fun textToSpeech(text: String){
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    //
    // Media Player Section
    //

    private fun backDialog(){
        MaterialAlertDialogBuilder(this)
            .setCancelable(false)
            .setTitle("Are you Sure")
            .setMessage("This will stop your workout!")
            .setNeutralButton("Cancel"){ dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Yes"){ dialog, _ ->
                this.finish()
                dialog.dismiss()
            }
    }

}