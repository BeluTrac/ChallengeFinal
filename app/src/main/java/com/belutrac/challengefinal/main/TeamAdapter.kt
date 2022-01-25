package com.belutrac.challengefinal.main

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.belutrac.challengefinal.R
import com.belutrac.challengefinal.Team
import com.belutrac.challengefinal.databinding.TeamListItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

private val TAG = TeamAdapter::class.java.simpleName

class TeamAdapter (val context: Context) : ListAdapter<Team, TeamAdapter.TeamViewHolder>(
    DiffCallback
) {
    companion object DiffCallback : DiffUtil.ItemCallback<Team>() {
        override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem == newItem
        }
    }


    lateinit var onItemClickListener: (Team) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding = TeamListItemBinding.inflate(LayoutInflater.from(parent.context))
        return TeamViewHolder( binding) //Devuelvo un objeto viewHolder con los elementos del item
    }

    override fun onBindViewHolder(
        holder: TeamViewHolder,
        position: Int
    ) { //Seteo los valores del item, para cada uno se ejecuta
        val team: Team = getItem(position)
        holder.bind(team)
    }

    inner class TeamViewHolder( private val binding: TeamListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(team: Team) {
            binding.tvName.text = team.name
            loadImage(binding.imgItem, team.imgUrl )
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                if (::onItemClickListener.isInitialized) //No entiendo que hace esto
                {
                    onItemClickListener(team)
                } else {
                    Log.d(TAG, "OnItemClickListener not initialized")
                }
            }
        }

        fun loadImage(imageView: ImageView, imgUrl: String)
        {
            Glide.with(context).load(imgUrl).listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    imageView.setImageResource(R.drawable.ic_baseline_image_not_supported_24)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            }).error(R.drawable.ic_baseline_image_not_supported_24).into(imageView)
        }
    }
}