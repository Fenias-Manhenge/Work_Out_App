package com.example.work_out

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.work_out.databinding.BmiBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow

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
            showDialog()
        }

        binding.toggleMetrics.check(binding.btnMetricUnit.id)
        adjustViews(R.layout.metric_unit)

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showDialog()
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

    private fun showDialog(){
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

        val tvBmiValue = view.findViewById<TextView>(R.id.tvBmiValue)
        val tvBmiStatus = view.findViewById<TextView>(R.id.tvBmiStatus)
        val tvBmiDescription = view.findViewById<TextView>(R.id.tvBmiDescription)
        val etHeight = view.findViewById<EditText>(R.id.et_height)
        val etWeight = view.findViewById<EditText>(R.id.et_weight)
        val btnCalculate = view.findViewById<Button>(R.id.btnCalculate)

        btnCalculate.setOnClickListener{
            if (validateMetricUnit(etHeight, etWeight)) {
                val height = etHeight.text.toString().toFloat() / 100
                val weight = etWeight.text.toString().toFloat()

                val bmi = weight / height.pow(2)

                displayBmiResult(bmi, tvBmiValue, tvBmiStatus, tvBmiDescription)
            }
        }

        view.layoutParams = layoutParams
        binding.mConstraintLayoutMetrics.removeAllViews()
        binding.mConstraintLayoutMetrics.addView(view)
    }

    private fun validateMetricUnit(etHeight: EditText, etWeight: EditText): Boolean{
        var validate = true

        if(etHeight.text.toString().isEmpty())
            validate = false
        else if(etWeight.text.toString().isEmpty())
            validate = false

        return validate
    }

    private fun displayBmiResult(
        bmi: Float,
        tvBmiValue: TextView,
        tvBmiStatus: TextView,
        tvBmiDescription: TextView
    ){
        val bmiLabel: String
        val bmiDescription: String

        when {
            bmi <= 15 -> {
                bmiLabel = resources.getString(R.string.bmiLabelVerySeverelyUnderweight)
                bmiDescription = resources.getString(R.string.bmiDescriptionUnderWeight)
            }
            bmi <= 16 -> {
                bmiLabel = resources.getString(R.string.bmiLabelSeverelyUnderweight)
                bmiDescription = resources.getString(R.string.bmiDescriptionUnderWeight)
            }
            bmi <= 18.5 -> {
                bmiLabel = resources.getString(R.string.bmiLabelUnderweight)
                bmiDescription = resources.getString(R.string.bmiDescriptionUnderWeight)
            }
            bmi <= 25 -> {
                bmiLabel = resources.getString(R.string.bmiLabelNormal)
                bmiDescription = resources.getString(R.string.bmiDescriptionNormal)
            }
            bmi <= 30 -> {
                bmiLabel = resources.getString(R.string.bmiLabelOverWeight)
                bmiDescription = resources.getString(R.string.bmiDescriptionObese)
            }
            bmi <= 35 -> {
                bmiLabel = resources.getString(R.string.bmiLabelModeratelyObese)
                bmiDescription = resources.getString(R.string.bmiDescriptionObese)
            }
            bmi <= 40 -> {
                bmiLabel = resources.getString(R.string.bmiLabelSeverelyObese)
                bmiDescription = resources.getString(R.string.bmiDescriptionSeverelyObese)
            }
            else -> {
                bmiLabel = resources.getString(R.string.bmiLabelVerySeverelyObese)
                bmiDescription = resources.getString(R.string.bmiDescriptionSeverelyObese)
            }
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        tvBmiValue.text = bmiValue
        tvBmiStatus.text = bmiLabel
        tvBmiDescription.text = bmiDescription
    }
}