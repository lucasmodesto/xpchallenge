package br.com.xpchallenge.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.xpchallenge.domain.entity.Character
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_character.view.*

class CharacterAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data = mutableListOf<Character>()

    var onFavoriteItemClick: (Character) -> Unit = {}
    var onItemClick: (Character) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        with(holder.itemView) {
            item_character_name_textview?.text = item.name
            Picasso.get().load(item.imageUrl).resize(480, 640).centerCrop().into(item_character_imageview)
            val favoriteDrawable = if (item.isFavorite) ContextCompat.getDrawable(
                context,
                R.drawable.ic_favorite_on
            ) else ContextCompat.getDrawable(context, R.drawable.ic_favorite_off)

            item_character_favorite_imageview.background = favoriteDrawable
            item_character_favorite_imageview?.setOnClickListener {
                item.isFavorite = !item.isFavorite
                onFavoriteItemClick.invoke(item)
                notifyDataSetChanged()
            }
            setOnClickListener {
                onItemClick(item)
            }
        }
    }

    fun update(data: List<Character>) {
        val currentSize = data.size
        this.data.addAll(data)
        this.notifyItemRangeChanged(currentSize, data.size)
    }

    fun clear() {
        this.data.clear()
        this.notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)


}