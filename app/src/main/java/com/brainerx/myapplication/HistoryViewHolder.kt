package com.brainerx.myapplication

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_history.view.*

class HistoryViewHolder(itemView : View):RecyclerView.ViewHolder(itemView) {
    val operation = itemView.operation
}