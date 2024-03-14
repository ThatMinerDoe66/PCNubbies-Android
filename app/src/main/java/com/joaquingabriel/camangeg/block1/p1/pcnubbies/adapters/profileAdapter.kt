package com.joaquingabriel.camangeg.block1.p1.pcnubbies.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.R
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.LoginResponse
import com.joaquingabriel.camangeg.block1.p1.pcnubbies.models.UserProfile

class profileAdapter (
    private var userProfiles: MutableList<LoginResponse.UserProfile>,
    private val lifecycleOwner: LifecycleOwner,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<profileAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_profile, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userProfiles.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userProfile = userProfiles[position]
        holder.profileNameTextView.text = userProfile.name
        holder.profileEmailTextView.text = userProfile.email
    }

    interface OnItemClickListener {
        fun onItemClick(userProfile: LoginResponse.UserProfile)
    }


    fun updateProfile(newProfile: LoginResponse.UserProfile) {
        userProfiles.clear()
        userProfiles.add(newProfile)
        notifyDataSetChanged()
        Log.d("ProfileAdapter", "Profile updated. New size: ${userProfiles.size}")
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileNameTextView: TextView = itemView.findViewById(R.id.profile_name)
        val profileEmailTextView: TextView = itemView.findViewById(R.id.profile_email)
    }
}
