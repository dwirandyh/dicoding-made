package com.dwirandyh.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnCategory: Button = view.findViewById(R.id.btn_category)
        btnCategory.setOnClickListener {
            openCategoryFragment()
        }
    }

    private fun openCategoryFragment() {
        val categoryFragment = CategoryFragment()
        val fragmentManager = fragmentManager as FragmentManager
        fragmentManager
            .beginTransaction()
            .replace(
                R.id.frame_container,
                categoryFragment,
                CategoryFragment::class.java.simpleName
            )
            .addToBackStack(null)
            .commit()
    }


}
