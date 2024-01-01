package com.example.madcamppj1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ProfileAdapter(context: Context, resource: Int, profiles: List<Profile>) :
    ArrayAdapter<Profile>(context, resource, profiles) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        val viewHolder: ViewHolder

        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false)
            viewHolder = ViewHolder(
                itemView.findViewById(R.id.profileImage),
                itemView.findViewById(R.id.textViewName),
                itemView.findViewById(R.id.textViewPhoneNumber)
            )
            itemView.tag = viewHolder
        } else {
            viewHolder = itemView.tag as ViewHolder
        }

        viewHolder.profileImage.setImageResource(R.drawable.person)

        val profile = getItem(position) // Use getItem(position) to retrieve the profile

        // Check if profile is not null before accessing its properties
        if (profile != null) {
            // Set profile data to views
            viewHolder.name.text = profile.name
            viewHolder.phoneNumber.text = profile.phoneNumber

            // Load profile image based on profileImageResId
            val imageResId = getDrawableResourceId(profile.profileImageResId)
            viewHolder.profileImage.setImageResource(imageResId)
        }

        return itemView!!
    }

    private data class ViewHolder(
        val profileImage: ImageView,
        val name: TextView,
        val phoneNumber: TextView
    )

    private fun getDrawableResourceId(imageName: String): Int {
        return context.resources.getIdentifier(
            imageName, "drawable",
            context.packageName
        )
    }
}