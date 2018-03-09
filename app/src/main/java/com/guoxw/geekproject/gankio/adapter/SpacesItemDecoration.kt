package com.guoxw.geekproject.gankio.adapter

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * @auther guoxw
 * @date 2018/3/8 0008
 * @package com.guoxw.geekproject.gankio.adapter
 * @desciption
 */
class SpacesItemDecoration(val space: Int) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
//        super.getItemOffsets(outRect, view, parent, state)

        outRect!!.bottom = space
        outRect.left = space
        outRect.right = space

        if (parent!!.getChildAdapterPosition(view) == 0) {
            outRect.top = space
        }

    }
}