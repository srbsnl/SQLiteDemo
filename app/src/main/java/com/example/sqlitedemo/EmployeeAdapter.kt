package com.example.sqlitedemo

import android.annotation.SuppressLint
import android.os.Build
import android.os.Build.VERSION_CODES
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

class EmployeeAdapter :RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {

    private var empList: ArrayList<EmployeeModel> = ArrayList()
    private var onClickItem: ((EmployeeModel) -> Unit)? = null

    fun setOnClickItem(callback: (EmployeeModel) -> Unit) {
        this.onClickItem = callback
    }

    fun addItems(items: ArrayList<EmployeeModel>) {
        this.empList = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EmployeeViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_emp, parent, false)
    )


    override fun getItemCount(): Int {
        return empList.size
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val emp = empList[position]
        holder.bindView(emp)
        holder.itemView.setOnClickListener { onClickItem?.invoke(emp) }
    }

    class EmployeeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
        private var btnDelete = view.findViewById<Button>(R.id.btnDelete)


        fun bindView(emp: EmployeeModel) {
            id.text = emp.id.toString()
            name.text = emp.name.toString()
            email.text = emp.email.toString()
        }
    }
}


