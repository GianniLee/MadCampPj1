package com.example.madcamppj1

import android.os.Bundle
//import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFrag : BottomSheetDialogFragment()  {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.add_contact, container, false)
    }


}