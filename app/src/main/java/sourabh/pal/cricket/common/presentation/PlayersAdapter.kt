package sourabh.pal.cricket.common.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sourabh.pal.cricket.common.utils.setImage
import sourabh.pal.cricket.databinding.RecyclerViewPlayerItemBinding
import sourabh.pal.cricket.common.presentation.model.UIPlayer

class PlayersAdapter: ListAdapter<UIPlayer, PlayersAdapter.PlayersViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersViewHolder {
        val binding = RecyclerViewPlayerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayersViewHolder, position: Int) {
        val item: UIPlayer = getItem(position)
        holder.bind(item)
    }

    inner class PlayersViewHolder(
        private val binding: RecyclerViewPlayerItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UIPlayer) {
            binding.name.text = item.name
            binding.photo.setImage(item.photo)
        }
    }
}

private val ITEM_COMPARATOR = object: DiffUtil.ItemCallback<UIPlayer>(){
    override fun areItemsTheSame(oldItem: UIPlayer, newItem: UIPlayer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UIPlayer, newItem: UIPlayer): Boolean {
        return oldItem == newItem
    }

}