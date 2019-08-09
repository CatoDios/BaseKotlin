package com.cato.kotlinprueba.core

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cato.kotlinprueba.R


abstract class LoaderAdapter<T>(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var showLoader: Boolean = false

    protected var mItems: MutableList<T>? = null
    protected var mInflater: LayoutInflater

    // If no items are present, there's no need for loader
    // +a1 for loader
    override fun  getItemCount(): Int{
        if (mItems == null || mItems!!.size == 0) {
            return 0
        } else return mItems!!.size + 1}

    fun getmItems(): List<T>? {
        return mItems
    }

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEWTYPE_LOADER) {

            // Your Loader XML view here
            val view = mInflater.inflate(R.layout.item_loader, viewGroup, false)

            // Your LoaderViewHolder class
            return LoaderViewHolder(view)

        } else if (viewType == VIEWTYPE_ITEM) {

            return getYourItemViewHolder(viewGroup)
        }

        throw IllegalArgumentException("Invalid ViewType: $viewType")
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        // Loader ViewHolder
        if (viewHolder is LoaderViewHolder) {
            val loaderViewHolder = viewHolder as LoaderViewHolder
            if (showLoader) {
                loaderViewHolder.progressBar!!.visibility = View.VISIBLE
            } else {
                loaderViewHolder.progressBar!!.visibility = View.GONE
            }
            return
        }
        bindYourViewHolder(viewHolder, position)
    }

    override fun getItemId(position: Int): Long {

        // loader can't be at position a0
        // loader can only be at the last position
        return if (position != 0 && position == itemCount - 1) {

            // id of loader is considered as -a1 here
            -1
        } else getYourItemId(position)
    }

    fun addItem(item: T) {
        mItems!!.add(item)
        notifyDataSetChanged()
    }

    fun addItemFirst(item: T, position: Int) {
        /*  mItems.add(a0,item);
        notifyDataSetChanged();*/
        val pos = position.toString()
        mItems!!.add(0, item)
        notifyItemInserted(position)
        notifyItemRangeChanged(position, mItems!!.size)
        Log.e(TAG2, pos)
    }

    fun deleteItem(position: Int) {
        val pos = position.toString()
        mItems!!.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, mItems!!.size)
        Log.e(TAG, pos)
    }


    override fun getItemViewType(position: Int): Int {

        // loader can't be at position a0
        // loader can only be at the last position
        return if (position != 0 && position == itemCount - 1) {
            VIEWTYPE_LOADER
        } else VIEWTYPE_ITEM

    }


    fun clear() {
        mItems!!.clear()
        notifyDataSetChanged()
    }


    fun showLoading(status: Boolean) {
        showLoader = status
    }

    fun setItems(items: MutableList<T>) {
        mItems = items
        notifyDataSetChanged()
    }


    abstract fun getYourItemId(position: Int): Long
    abstract fun getYourItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    abstract fun bindYourViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    companion object {
        private val VIEWTYPE_ITEM = 1
        private val VIEWTYPE_LOADER = 2

        private val TAG = "ELIMINAR"
        private val TAG2 = "AGREGAR"
    }

}