package id.idham.moviecatalogue.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by idhammi on 1/25/2020.
 */

class RecyclerAdapter<T : Any>(
    private val diffCallback: DiffCallback,
    @LayoutRes val holderResId: Int,
    @IdRes val specificResViewId: Int? = null,
    private val onBind: (T, View) -> Unit,
    private val itemListener: (T, pos: Int, View) -> Unit = { _, _, _ -> kotlin.run {} },
    private val specificViewListener: (T, pos: Int, View) -> Unit = { _, _, _ -> kotlin.run {} }
) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder<T>>() {

    private val listData = mutableListOf<T>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder<T> {
        val itemView = LayoutInflater.from(p0.context).inflate(holderResId, p0, false)
        var specificView: View? = null
        specificResViewId?.let {
            specificView = itemView?.findViewById(it)
        }
        return MyViewHolder(itemView, specificView)
    }

    override fun onBindViewHolder(p0: MyViewHolder<T>, p1: Int) {
        p0.bindView(listData[p0.adapterPosition], onBind, itemListener, specificViewListener)
    }

    override fun getItemCount(): Int = listData.size

    fun setData(datas: List<T>) {
        calculateDiff(datas)
    }

    private fun calculateDiff(newDatas: List<T>) {
        diffCallback.setList(listData, newDatas)
        val result = DiffUtil.calculateDiff(diffCallback)
        with(listData) {
            clear()
            addAll(newDatas)
        }
        result.dispatchUpdatesTo(this)
    }

    class MyViewHolder<T : Any>(itemView: View, private val specificView: View? = null) :
        RecyclerView.ViewHolder(itemView) {

        fun bindView(
            item: T,
            onBind: (T, View) -> Unit,
            itemListener: (T, pos: Int, View) -> Unit,
            specificViewListener: (T, pos: Int, View) -> Unit
        ) {
            with(itemView) {
                onBind.invoke(item, this)
                setOnClickListener {
                    itemListener.invoke(item, adapterPosition, this)
                }
            }
            specificView?.setOnClickListener {
                specificViewListener.invoke(item, adapterPosition, itemView)
            }
        }

    }

}