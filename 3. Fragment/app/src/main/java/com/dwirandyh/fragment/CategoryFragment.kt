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
class CategoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnDetailCategory: Button = view.findViewById(R.id.btn_detail_category)
        btnDetailCategory.setOnClickListener {
            openDetailCategory()
        }
    }

    private fun openDetailCategory() {
        val detailCategoryFragment = DetailCategoryFragment()

        val bundle = Bundle()
        bundle.putString(DetailCategoryFragment.EXTRA_NAME, "Lifestyle")
        val description = "Kategori ini akan berisisi produk-produk lifestyle"

        // mengirim data ke fragment category detail melalui bundle
        detailCategoryFragment.arguments = bundle
        // mengirim data ke fragment category detail melalui property getter-setter
        detailCategoryFragment.description = description

        /*
       Method addToBackStack akan menambahkan fragment ke backstack
       Behaviour dari back button akan cek fragment dari backstack,
       jika ada fragment di dalam backstack maka fragment yang akan di close / remove
       jika sudah tidak ada fragment di dalam backstack maka activity yang akan di close / finish
        */
        val mFragmentManager = fragmentManager as FragmentManager
        mFragmentManager
            .beginTransaction()
            .replace(
                R.id.frame_container,
                detailCategoryFragment,
                DetailCategoryFragment::class.java.simpleName
            )
            .addToBackStack(null)
            .commit()
    }

}
