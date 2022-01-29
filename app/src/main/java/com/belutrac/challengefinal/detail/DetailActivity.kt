package com.belutrac.challengefinal.detail

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (teamInfo == null) {
            finish()
        }

        binding.tvName.text = teamInfo?.name

        if (teamInfo?.formedYear == "0") {
            binding.tvFormedYear.visibility = View.GONE
        } else {
            binding.tvFormedYear.text = getString(R.string.founded_in_year, teamInfo?.formedYear)
        }

        binding.tvDescription.text = teamInfo?.description ?: ""

        binding.tvStadiumCapacity.text =
            if (teamInfo?.stadiumCapacity == "0") getString(R.string.Data_not_available) else getString(
                R.string.capacity_text,
                teamInfo?.stadiumCapacity
            )
        binding.tvStadiumName.text =
            if (teamInfo?.stadiumName.isNullOrBlank()) getString(R.string.Data_not_available) else teamInfo?.stadiumName

        if (teamInfo?.location.isNullOrBlank()) {
            binding.tvStadiumLocation.visibility = View.GONE
        } else {
            binding.tvStadiumLocation.text = teamInfo?.location
        }

        teamInfo?.run {
            setUrlOnClick(teamInfo.website, binding.websiteImgbtn)
            setUrlOnClick(teamInfo.facebookUrl, binding.facebookImgbtn)
            setUrlOnClick(teamInfo.twitterUrl, binding.twitterImgbtn)
            setUrlOnClick(teamInfo.instagramUrl, binding.instagramImgbtn)
        }

        teamInfo?.imgUrl?.let { loadImage(binding.imgTeam, it) }
        setContentView(binding.root)
    }

    private fun loadImage(imageView: ImageView, imgUrl: String) {
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

    private fun setUrlOnClick(url: String, imgBtn: ImageButton) {
        if (url.isNotBlank()) {
            imgBtn.setOnClickListener {
                val uri = Uri.parse("https://".plus(url))
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        } else {
            imgBtn.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }

}