package br.com.xpchallenge.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import br.com.xpchallenge.presentation.model.CharacterViewObject
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_character.view.*

class CharacterAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data = mutableListOf<CharacterViewObject>()

    var onFavoriteItemClick: (CharacterViewObject) -> Unit = {}
    var onItemClick: (CharacterViewObject) -> Unit = {}
    var onLastItemBindListener: () -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if ((position >= itemCount - 1)) onLastItemBindListener.invoke()

        val item = data[position]
        with(holder.itemView) {
            item_character_name_textview?.text = item.name
            val circularProgressDrawable = CircularProgressDrawable(context).apply {
                strokeWidth = 2f
                centerRadius = 24f
                start()
            }

            Picasso.get()
                .load(item.imageUrl)
                .placeholder(circularProgressDrawable)
                .resize(480, 640).centerCrop()
                .into(item_character_imageview)
            val favoriteDrawable = if (item.isFavorite) ContextCompat.getDrawable(
                context,
                R.drawable.ic_favorite_on
            ) else ContextCompat.getDrawable(context, R.drawable.ic_favorite_off)

            item_character_favorite_imageview.background = favoriteDrawable
            item_character_favorite_imageview?.setOnClickListener {
                onFavoriteItemClick.invoke(item)
            }
            setOnClickListener {
                onItemClick(item)
            }
        }
    }

    fun update(data: List<CharacterViewObject>) {
        val currentSize = data.size
        this.data.addAll(data)
        this.notifyItemRangeChanged(currentSize, data.size)
    }

    fun clear() {
        this.data.clear()
        this.notifyDataSetChanged()
    }

    fun updateFavorites(data: List<CharacterViewObject>) {
        this.data.forEach {
            it.isFavorite = false
        }
        data.forEach { updatedItem ->
            val itemOnList = this.data.find { it.id == updatedItem.id }
            itemOnList?.isFavorite = updatedItem.isFavorite
        }
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
