package com.example.leo.mygame

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.leo.mygame.dummy.DataContent
import com.example.leo.mygame.dummy.DataContent.DummyItem

/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class HistoryModule : Fragment() {
    private var mColumnCount: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mColumnCount = arguments?.getInt(ARG_COLUMN_COUNT) ?: mColumnCount
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_history_module, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            if (mColumnCount <= 1) {
                view.layoutManager = LinearLayoutManager(context)
            } else {
                view.layoutManager = GridLayoutManager(context, mColumnCount)
            }
            val data = DataSaver.readScore(context)
            val collection = data.map { DummyItem(it.second.toTime(),it.first.toScore()) }
            DataContent.items.addAll(collection)
            view.adapter = HistoryItemAdapter(DataContent.items)
        }
        return view
    }

    companion object {

        private const val ARG_COLUMN_COUNT = "column-count"

        fun newInstance(columnCount: Int): HistoryModule {
            val fragment = HistoryModule()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
