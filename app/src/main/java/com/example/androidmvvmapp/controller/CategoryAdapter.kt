package com.example.androidmvvmapp.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmvvmapp.R
import com.example.androidmvvmapp.ui.jokes.JokesFragment

/**
 * Adapter that shows various categories of jokes in the recycler view
 */
class CategoryAdapter(
    /**
     * Stores the reference of JokesFragment
     */
    private val baseFragment: JokesFragment,
    /**
     * Stores an array list of categories
     */
    private val categoryArrayList: ArrayList<String>
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        //Sets category text on categoryTextView
        holder.getCategoryTextView().text = categoryArrayList[position]
        //Sets on click listener on categoryTextView
        holder.getCategoryCardView().setOnClickListener {
            //Updates UI
            baseFragment.updateUI(categoryArrayList[position])
        }
    }

    /**
     * Returns size of the jokes list
     */
    override fun getItemCount(): Int {
        return categoryArrayList.size
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * Stores the reference of category cardview
         */
        private val categoryCardView = itemView.findViewById<CardView>(R.id.card_category)

        /**
         * Stores the reference of category textview
         */
        private val categoryTextView = itemView.findViewById<TextView>(R.id.text_category)

        /**
         * Returns categoryCardView
         */
        fun getCategoryCardView(): CardView {
            return categoryCardView
        }

        /**
         * Returns categoryTextView
         */
        fun getCategoryTextView(): TextView {
            return categoryTextView
        }
    }
}