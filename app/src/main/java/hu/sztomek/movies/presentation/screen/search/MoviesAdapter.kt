package hu.sztomek.movies.presentation.screen.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import hu.sztomek.movies.R
import hu.sztomek.movies.presentation.model.SearchItemUiModel
import kotlinx.android.synthetic.main.item_seach_result.view.*

class MoviesAdapter(var clickListener: ((item: SearchItemUiModel) -> Unit)? = null) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val data = mutableListOf<SearchItemUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setList(newData: List<SearchItemUiModel>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_seach_result, parent, false)) {

        fun bind(item: SearchItemUiModel) {
            Glide.with(itemView)
                    .setDefaultRequestOptions(
                            RequestOptions()
                                    .error(R.drawable.ic_broken)
                    )
                    .load(item.posterUrl)
                    .into(itemView.ivPoster)
            itemView.tvTitle.text = item.title
            itemView.tvRating.text = item.rating
            itemView.tvBudget.text = item.budget

            itemView.setOnClickListener {
                clickListener?.invoke(item)
            }
        }

    }
}