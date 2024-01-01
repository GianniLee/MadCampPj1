package com.example.madcamppj1

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MultiImageAdapter(private val items: ArrayList<Uri>, val context: Context) :
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedVIEW =
            LayoutInflater.from(parent.context).inflate(R.layout.multi_image_item, parent, false)
        return ViewHolder(inflatedVIEW)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        Glide.with(context).load(item)
            .override(600, 600)
            .into(holder.image)


    }

    override fun getItemCount(): Int {
        return items.size
    }

}
class ViewHolder(v:View) :RecyclerView.ViewHolder(v) {
    private var view:View = v
    var image = v.findViewById<ImageView>(R.id.image)

    fun bind(listener:View.OnClickListener,item:String) {
        view.setOnClickListener(listener)
    }
}