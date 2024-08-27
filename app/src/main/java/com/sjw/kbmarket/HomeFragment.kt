package com.sjw.kbmarket

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sjw.kbmarket.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val productList = (activity as MainActivity).productList
        val productAdapter = ProductAdapter(productList)

        with(binding) {
            mainRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            mainRV.adapter = productAdapter

            // Handle item clicks
            productAdapter.itemClick = object : ProductAdapter.ItemClick {
                override fun onClick(view: View, position: Int) {
                    val bundle = Bundle().apply {
                        putParcelable(KEY_PRODUCT, productAdapter.items[position])
                        putInt(KEY_POSITION, position)
                    }
                    val fragment = DetailFragment.newInstance(bundle)

                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()
                }

                override fun onLongClick(view: View, position: Int) {
                    if (position > 0) {
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setTitle("상품 삭제")
                        builder.setMessage("정말로 삭제하시겠습니까?")
                        builder.setIcon(R.drawable.sad_img)

                        val listener = DialogInterface.OnClickListener { dialog, which ->
                            when (which) {
                                DialogInterface.BUTTON_POSITIVE -> {
                                    // Remove item from the list in MainActivity
                                    (activity as MainActivity).productList.removeAt(position)
                                    productAdapter.notifyItemRemoved(position)
                                    productAdapter.notifyItemRangeChanged(position, productList.size)
                                }
                                DialogInterface.BUTTON_NEGATIVE -> dialog.dismiss()
                            }
                        }

                        builder.setPositiveButton("확인", listener)
                        builder.setNegativeButton("취소", listener)
                        builder.show()
                    }
                }
            }

            return binding.root
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle) =
            HomeFragment().apply {
                arguments = bundle
            }
    }
}
