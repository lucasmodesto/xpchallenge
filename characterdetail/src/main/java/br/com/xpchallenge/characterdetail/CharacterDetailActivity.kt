package br.com.xpchallenge.characterdetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat
import br.com.xpchallenge.presentation.CharacterViewObject
import br.com.xpchallenge.ui.core.BaseActivity
import br.com.xpchallenge.ui.extensions.setVisibility
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

    @Inject
    lateinit var presenter: CharacterDetailContract.Presenter

    lateinit var character: CharacterViewObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)
        supportActionBar?.run {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        character =
            intent?.extras?.getParcelable(EXTRA_CHARACTER)
                ?: throw Exception("Required character intent extra")
        presenter.attach(this)
        presenter.start(character)
        presenter.subscribeToFavorites()

        character_detail_favorite_button?.setOnClickListener {
            presenter.toggleFavorite(character)
        }
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    override fun showDescription(hasDescription: Boolean, description: String) {
        character_detail_description_text_view?.setVisibility(hasDescription)
        character_detail_description_text_view?.text = description
    }

    override fun showImage(isImageAvailable: Boolean, imageUrl: String) {
        character_detail_image_view.setVisibility(isImageAvailable)
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

//    override fun showFavorite(character: CharacterViewObject) {
//        val image = ContextCompat.getDrawable(
//            this,
//            if (character.isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
//        )
//        character_detail_favorite_button?.background = image
//    }

    override fun updateFavorites(characters: List<CharacterViewObject>) {
        val item = characters.find { it.id == character.id }
        val isFavorite = item?.isFavorite ?: false
        val image = ContextCompat.getDrawable(
            this,
            if (isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off
        )
        character_detail_favorite_button?.background = image
    }

}