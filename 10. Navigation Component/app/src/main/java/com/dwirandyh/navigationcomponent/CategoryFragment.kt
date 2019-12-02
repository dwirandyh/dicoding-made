package com.dwirandyh.navigationcomponent


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_category.*

/**
 * A simple [Fragment] subclass.
 */
class CategoryFragment : Fragment() {

    companion object {
        val EXTRA_NAME = "extra_name"
        val EXTRA_STOCK = "extra_stock"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_category_lifestyle.setOnClickListener {
            navigateToDetailWithSafeArgs()
        }
    }

    private fun navigateToDetailWithBundle() {
        val bundle = Bundle()
        bundle.putString(EXTRA_NAME, "Lifestyle")
        bundle.putLong(EXTRA_STOCK, 7)
        view?.findNavController()
            ?.navigate(R.id.action_categoryFragment_to_detailCategoryFragment, bundle)
    }

    private fun navigateToDetailWithSafeArgs() {
        val toDetailCategoryFragment =
            CategoryFragmentDirections.actionCategoryFragmentToDetailCategoryFragment()
        toDetailCategoryFragment.name = "LifeStyle"
        toDetailCategoryFragment.stock = 7
        view?.findNavController()?.navigate(toDetailCategoryFragment)
    }


}
