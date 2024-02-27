package com.example.work_out.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.work_out.R

class HistoryAdapter(private val data: List<DateTimeEntity>): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        val id: TextView = item.findViewById(R.id.tvID)
        val date: TextView = item.findViewById(R.id.tvDate)
        val time: TextView = item.findViewById(R.id.tvTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.id.text = data[position].id.toString()
        holder.date.text = data[position].date
        holder.time.text = data[position].time
    }
}