package com.example.gameofthronesviewer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CharacterAdapter(private val context: Context, private val characterList: List<Character>) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.character_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characterList[position]
        holder.bind(character)

        holder.imageView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Character at position $position clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewCharacter)
        private val tvFullName: TextView = itemView.findViewById(R.id.tvFullNameCharacter)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitleCharacter)
        private val tvFamily: TextView = itemView.findViewById(R.id.tvFamilyCharacter)

        fun bind(character: Character) {
            Glide.with(context)
                .load(character.imageUrl)
                .fitCenter()
                .into(imageView)

            tvFullName.text = character.fullName
            tvTitle.text = character.title
            tvFamily.text = character.family
        }
    }
}

