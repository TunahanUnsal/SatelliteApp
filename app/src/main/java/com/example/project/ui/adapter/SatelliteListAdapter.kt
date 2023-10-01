package com.example.project.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.project.R
import com.example.project.repository.satelliteService.model.SatelliteModel
import com.example.project.ui.detail.DetailActivity


class SatelliteListAdapter(
    private val coinList: ArrayList<SatelliteModel>,
    private val activity: Activity
) :
    RecyclerView.Adapter<SatelliteListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_satellite, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(coinList[position], activity)
        holder.itemView.findViewById<CardView>(R.id.view_general).startAnimation(
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.item_animation_fall_down
            )
        )
    }

    override fun getItemCount(): Int {
        return coinList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindItems(satellite: SatelliteModel, activity: Activity) {

            Log.i("TAG", "bindItems: $satellite")

            val nameTextView = itemView.findViewById<TextView>(R.id.name)
            val stateTextView = itemView.findViewById<TextView>(R.id.state)
            val image = itemView.findViewById<ImageView>(R.id.image)

            nameTextView.text = satellite.name.toString()

            if (satellite.active == true) {
                stateTextView.text = "active"
                image.setImageResource(R.drawable.rocket_active)
            } else {
                stateTextView.text = "passive"
                image.setImageResource(R.drawable.rocket_passive)
            }

            itemView.setOnClickListener {
                val intent = Intent(activity, DetailActivity::class.java).setAction("")
                intent.putExtra("id", satellite.id.toString())
                intent.putExtra("name", satellite.name.toString())
                activity.startActivity(intent)
            }


        }
    }

}