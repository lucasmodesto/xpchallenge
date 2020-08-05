package br.com.xpchallenge.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import br.com.xpchallenge.presentation.model.MediaViewObject
import br.com.xpchallenge.presentation.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_media.view.*

class MediaAdapter(private val data: List<MediaViewObject>) : RecyclerView.Adapter<MediaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_media, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        with(holder.itemView) {
            val circularProgressDrawable = CircularProgressDrawable(context).apply {
                strokeWidth = 5f
                centerRadius = 30f
                start()
            }

            Picasso.get()
                .load(item.imageUrl)
                .placeholder(circularProgressDrawable)
                .fit()
                .into(item_media_imageview)

            item_media_name_textview?.text = item.name
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}