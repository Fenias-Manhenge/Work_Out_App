package com.example.work_out

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.work_out.databinding.BmiBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class Bmi : AppCompatActivity() {

    private val binding by lazy { BmiBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.tbBMI)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.tbBMI.setNavigationOnClickListener {
            showADialog()
        }

        binding.toggleMetrics.check(binding.btnMetricUnit.id)
        adjustViews(R.layout.metric_unit)

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showADialog()
            }
        })

        binding.toggleMetrics.addOnButtonCheckedListener{_, checkedID, isChecked ->
            if (isChecked){
                when(checkedID){
                    binding.btnMetricUnit.id -> adjustViews(R.layout.metric_unit)
                    binding.btnUsUnit.id -> adjustViews(R.layout.us_metrics)
                }
            }
        }
    }

    private fun showADialog(){
        MaterialAlertDialogBuilder(this)
            .setCancelable(false)
            .setTitle("Back")
            .setMessage("Are you sure you want back")
            .setPositiveButton("Yes"){dialog, _ ->
                this.finish()
                dialog.dismiss()
            }
            .setNegativeButton("No"){dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun inflateLayout(layoutResID: Int): View {
        return LayoutInflater.from(this).inflate(layoutResID, null)
    }

    private fun adjustViews(layoutResID: Int){
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )

        val view = inflateLayout(layoutResID)
        view.layoutParams = layoutParams
        binding.mConstraintLayoutMetrics.removeAllViews()
        binding.mConstraintLayoutMetrics.addView(view)
    }
}