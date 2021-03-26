package com.hashim.instagram.ui.home.post.likeduser

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hashim.instagram.R
import com.hashim.instagram.data.model.Post
import com.hashim.instagram.databinding.ActivityLikedUserBinding
import com.hashim.instagram.di.component.ActivityComponent
import com.hashim.instagram.ui.base.BaseActivity
import javax.inject.Inject

class LikedUserActivity : BaseActivity<LikedUserViewModel>() {

    lateinit var binding: ActivityLikedUserBinding


    override fun provideLayoutId(): View {
        binding = ActivityLikedUserBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    @Inject
    lateinit var likedUserAdapter: LikedUserAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager


    override fun setupView(savedInstanceState: Bundle?) {

        if(intent.hasExtra("data")){
            intent.getParcelableArrayListExtra<Post.User>("data").run {
                this?.let { viewModel.loadData(it) }
            }
        }

        binding.rvLike.apply {
            layoutManager = linearLayoutManager
            adapter = likedUserAdapter

        }
    }


    override fun setupObservers() {
        super.setupObservers()

        viewModel.likedUsers.observe(this, {
            likedUserAdapter.appendData(it.data!!)
            binding.tvLikesNumber.text = getString(R.string.user_post_likes_label,it.data.size)
        })


    }
}
