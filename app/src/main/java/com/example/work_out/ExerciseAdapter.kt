package com.example.work_out

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(private val data: List<Exercise>): RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {

    inner class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        val exerciseNumber: TextView = item.findViewById(R.id.exerciseNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = holder.exerciseNumber
        exercise.text = data[position].id.toString()

        when{
            data[position].isSelected ->{
                exercise.background = ContextCompat.getDrawable(
                    holder.itemView.context, R.drawable.item_view_background_border
                )
                exercise.setTextColor(Color.WHITE)
            }
            data[position].isCompleted ->{
                exercise.background = ContextCompat.getDrawable(
                    holder.itemView.context, R.drawable.item_view_background_completed
                )
                exercise.setTextColor(Color.WHITE)
            }
            else -> {
                holder.exerciseNumber.background = ContextCompat.getDrawable(
                    holder.itemView.context, R.drawable.item_view_background
                )
                exercise.setTextColor(Color.WHITE)
            }
        }
    }
}