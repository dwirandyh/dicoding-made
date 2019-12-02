package com.dwirandyh.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_detail_category.*

/**
 * A simple [Fragment] subclass.
 */
class DetailCategoryFragment : Fragment() {

    var description: String? = null

    companion object {
        val EXTRA_NAME = "extra_name"
        val EXTRA_DESCRIPTION = "extra_description"
    }

    lateinit var tvCategoryName: TextView
    lateinit var tvCategoryDescription: TextView
    lateinit var btnProfile: Button
    lateinit var btnShowDialog: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_category, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvCategoryName = view.findViewById(R.id.tv_category_name)
        tvCategoryDescription = view.findViewById(R.id.tv_category_description)
        btnProfile = view.findViewById(R.id.btn_profile)
        btnProfile.setOnClickListener {
            showProfileActivity()
        }
        btnShowDialog = view.findViewById(R.id.btn_show_dialog)
        btnShowDialog.setOnClickListener {
            showOptionDialog()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null) {
            val descFromBundle = savedInstanceState.getString(EXTRA_DESCRIPTION)
            description = descFromBundle
        }

        if (arguments != null) {
            val categoryName = arguments?.getString(EXTRA_NAME)
            tvCategoryName.text = categoryName
            tvCategoryDescription.text = description
        }
    }

    private fun showOptionDialog() {
        val mOptionDialogFragment = OptionDialogFragment()
        val mFragmentManager = childFragmentManager
        mOptionDialogFragment.show(mFragmentManager, OptionDialogFragment::class.java.simpleName)
    }

    private fun showProfileActivity() {
        val intent = Intent(activity, ProfileActivity::class.java)
        startActivity(intent)
    }


    internal var optionDialogListener: OptionDialogFragment.OnOptionDialogListener =
        object : OptionDialogFragment.OnOptionDialogListener {
            override fun onOptionChosen(text: String?) {
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
            }
        }
}
