package br.com.xpchallenge.characterdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.xpchallenge.presentation.model.CharacterViewObject
import br.com.xpchallenge.presentation.adapter.MediaAdapter
import br.com.xpchallenge.presentation.model.MediaViewObject
import br.com.xpchallenge.presentation.core.BaseActivity
import br.com.xpchallenge.presentation.extensions.setIsVisible
import br.com.xpchallenge.presentation.recyclerview.HorizontalSpaceItemDecoration
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_character_detail.*
import javax.inject.Inject


@AndroidEntryPoint
class CharacterDetailActivity : BaseActivity(), CharacterDetailContract.View {

    companion object {
        private const val EXTRA_CHARACTER = "extraCharacter"

        fun newIntent(
            context: Context,
            character: CharacterViewObject
        ): Intent {
            return Intent(context, CharacterDetailActivity::class.java).apply {
                putExtra(EXTRA_CHARACTER, character)
            }
        }
    }

    private lateinit var character: CharacterViewObject

    @Inject
    lateinit var presenter: CharacterDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)
        supportActionBar?.run {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        character = intent?.extras?.getParcelable(EXTRA_CHARACTER)
            ?: throw Exception("Required character intent extra")

        presenter.run {
            attach(this@CharacterDetailActivity)
            start(character)
            subscribeToFavorites()
            loadComics(character.id)
            loadSeries(character.id)
        }

        character_detail_favorite_button?.setOnClickListener {
            presenter.toggleFavorite(character)
        }
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun showDescription(hasDescription: Boolean, description: String) {
        character_detail_description_text_view?.setIsVisible(hasDescription)
        character_detail_description_text_view?.text = description
    }

    override fun showImage(imageUrl: String) {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels
        val imageHeight = screenHeight / 3

        Picasso.get().load(imageUrl).resize(screenWidth, imageHeight).centerCrop()
            .into(character_detail_image_view)
    }

    override fun showName(name: String) {
        supportActionBar?.run {
            title = name
        }
    }

    override fun updateFavorites(characters: List<CharacterViewObject>) {
        val item = characters.find { it.id == character.id }
        val isFavorite = item?.isFavorite ?: false
        val image = ContextCompat.getDrawable(
            this,
            if (isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
        )
        character_detail_favorite_button?.background = image
    }

    override fun showComics(comics: List<MediaViewObject>) {
        val mediaAdapter = MediaAdapter(comics)
        character_detail_comics_recycler_view?.run {
            this.adapter = mediaAdapter
            layoutManager = LinearLayoutManager(
                baseContext,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            addItemDecoration(HorizontalSpaceItemDecoration(rightSpacing = R.dimen.default_horizontal_margin))
            isNestedScrollingEnabled = false
        }
    }

    override fun showSeries(series: List<MediaViewObject>) {
        val mediaAdapter = MediaAdapter(series)

        character_detail_series_recycler_view?.run {
            adapter = mediaAdapter
            layoutManager = LinearLayoutManager(
                baseContext,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            isNestedScrollingEnabled = false
            addItemDecoration(HorizontalSpaceItemDecoration(rightSpacing = R.dimen.default_horizontal_margin))
        }
    }

    override fun showComicsLoading() {
        character_detail_comics_progressbar?.setIsVisible(true)
    }

    override fun hideComicsLoading() {
        character_detail_comics_progressbar?.setIsVisible(false)
    }

    override fun showSeriesLoading() {
        character_detail_series_progressbar?.setIsVisible(true)
    }

    override fun hideSeriesLoading() {
        character_detail_series_progressbar?.setIsVisible(false)
    }

    override fun showComicsEmptyState() {
        character_detail_comics_emptystate_view?.setIsVisible(true)
    }

    override fun hideComicsEmptyState() {
        character_detail_comics_emptystate_view?.setIsVisible(false)
    }

    override fun showSeriesEmptyState() {
        character_detail_series_emptystate_view?.setIsVisible(true)
    }

    override fun hideSeriesEmptyState() {
        character_detail_series_emptystate_view?.setIsVisible(false)
    }

    override fun showComicsErrorState(messageId: Int, retryAction: () -> Unit) {
        character_detail_comics_error_textview?.text = getString(messageId)
        character_detail_comics_error_view?.setIsVisible(true)
        character_detail_comics_retry_button?.setOnClickListener {
            retryAction.invoke()
        }
    }

    override fun hideComicsErrorState() {
        character_detail_comics_error_view?.setIsVisible(false)
    }

    override fun showSeriesErrorState(messageId: Int, retryAction: () -> Unit) {
        character_detail_series_error_textview?.text = getString(messageId)
        character_detail_series_error_view?.setIsVisible(true)
        character_detail_series_retry_button?.setOnClickListener {
            retryAction.invoke()
        }
    }

    override fun hideSeriesErrorState() {
        character_detail_series_error_view?.setIsVisible(false)
    }

    override fun showEmptyFavorites() {
        // Not applied to this view as it just need to update favorites
    }

    override fun hideEmptyFavorites() {
        // Not applied to this view as it just need to update favorites
    }


}