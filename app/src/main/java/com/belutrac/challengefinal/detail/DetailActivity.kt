package com.belutrac.challengefinal.detail

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.belutrac.challengefinal.R
import com.belutrac.challengefinal.Team
import com.belutrac.challengefinal.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class DetailActivity : AppCompatActivity() {
    companion object {
        const val TEAM_KEY = "team key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        val extra = intent?.extras
        val teamInfo = extra?.getParcelable<Team>(TEAM_KEY)

        if (teamInfo == null) {
            finish()
        }

        binding.tvName.text = teamInfo?.name
        binding.tvFormedYear.text = getString(R.string.founded_in_year, teamInfo?.formedYear)
        binding.tvDescription.text = teamInfo?.description ?: ""
        binding.tvStadiumCapacity.text = getString(
            R.string.capacity_text,
            teamInfo?.stadiumCapacity
        )
        binding.tvStadiumName.text = teamInfo?.stadiumName ?: ""
        binding.tvStadiumLocation.text = teamInfo?.location ?: ""

        if (teamInfo?.website?.isNotBlank() == true) {
            binding.webOficial.setOnClickListener {
                val uri = Uri.parse("https://".plus(teamInfo.website))
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        } else {
            binding.webOficial.visibility = View.INVISIBLE
        }

        teamInfo?.imgUrl?.let { loadImage(binding.imgTeam, it) }
        setContentView(binding.root)
    }

    fun loadImage(imageView: ImageView, imgUrl: String) {
        Glide.with(this).load(imgUrl).listener(object : RequestListener<Drawable> {
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