package com.example.androidmvvmapp.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmvvmapp.R
import com.example.androidmvvmapp.data.model.Joke

/**
 * Adapter that shows jokes in recycler view
 */
class JokesAdapter(
    /**
     * Stores reference of the context
     */
    private val context: Context,
    /**
     * Stores reference of jokes array list
     */
    private val jokesArrayList: ArrayList<Joke>
) :
    RecyclerView.Adapter<JokesAdapter.JokesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokesViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_joke, parent, false)
        return JokesViewHolder(view)
    }

    override fun onBindViewHolder(holder: JokesViewHolder, position: Int) {
        //Assigns current joke from jokes array list
        val currentJoke = jokesArrayList[position]
        var joke = ""
        if (currentJoke.type == "single") {
            joke = currentJoke.joke
        } else {
            joke = currentJoke.setup + "\n" + currentJoke.delivery
        }
        //Sets joke text in jokeTextView
        holder.getJokeTextView().text = joke
    }

    /**
     * Returns size of the jokes list
     */
    override fun getItemCount(): Int {
        return jokesArrayList.size
    }

    class JokesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * Stores reference of jokeTextView that shows joke text
         */
        private val jokeTextView = itemView.findViewById<TextView>(R.id.text_joke)

        /**
         * Returns jokeTextView
         */
        fun getJokeTextView(): TextView {
            return jokeTextView
        }
    }
}
