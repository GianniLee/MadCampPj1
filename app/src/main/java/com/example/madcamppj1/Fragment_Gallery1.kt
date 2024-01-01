package com.example.w1

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import android.widget.*
import com.example.madcamppj1.R

class Fragment_Gallery1 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragement_gallery1, container, false)

        val gv = view.findViewById<GridView>(R.id.gridView)
        val gAdapter = MyGridAdapter(requireContext())

        gv.adapter = gAdapter
        //
        return view
    }

    inner class MyGridAdapter(private val context: Context) : BaseAdapter() {

        private var picID = arrayListOf<Int>(
            R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4, R.drawable.pic5,
            R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4, R.drawable.pic5,
            R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4, R.drawable.pic5
        )

        override fun getCount(): Int {
            return picID.size
        }

        override fun getItem(i: Int): Any? {
            return null
        }

        override fun getItemId(i: Int): Long {
            return 0
        }

        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            val imageView = ImageView(context)
            imageView.layoutParams = ViewGroup.LayoutParams(300, 300)
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            imageView.setPadding(5, 5, 5, 5)

            imageView.setImageResource(picID[i])

            // 이미지를 선택했을 때 큰 화면으로 보기
            imageView.setOnClickListener {
                makeBigImage(i)
            }

            return imageView
        }

        private fun makeBigImage(i: Int) {
            val dialogView = View.inflate(context, R.layout.activity_dialog, null)
            val dlg = AlertDialog.Builder(context)
            val ivPic = dialogView.findViewById<ImageView>(R.id.ivPic)
            ivPic.setImageResource(picID[i])
            dlg.setTitle("큰 이미지")
            dlg.setIcon(R.drawable.ic_launcher_foreground)
            dlg.setView(dialogView)
            dlg.setNegativeButton("닫기", null)
            dlg.show()
        }
    }
}