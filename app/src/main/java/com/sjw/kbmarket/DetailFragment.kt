package com.sjw.kbmarket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sjw.kbmarket.databinding.FragmentDetailBinding

const val KEY_PRODUCT = "product_data"
const val KEY_POSITION = "position"

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private var param: Product? = null
    private var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getParcelable(KEY_PRODUCT)
            position = it.getInt(KEY_POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var isChecked = param?.isChecked ?: true  // 초기 좋아요 상태를 가져옴

        with(binding) {
            detailProfileImg.clipToOutline = true
            param?.let {
                detailProductImg.setImageResource(it.imageId)
                detailNickname.text = it.seller
                detailLocation.text = it.location
                detailProductName.text = it.productName
                detailProductIntroduce.text = it.productDescription
                detailPrice.text = it.price.toString()

                // 좋아요 버튼 상태 설정
                detailFavoriteBtn.setImageResource(
                    if (isChecked) R.drawable.ic_favorite_full else R.drawable.ic_favorite
                )

                // 좋아요 버튼 클릭 이벤트
                detailFavoriteBtn.setOnClickListener {
                    if (isChecked) {
                        param!!.favorites -= 1
                        isChecked = false
                    } else {
                        param!!.favorites += 1
                        isChecked = true
                    }
                    param!!.isChecked = isChecked
                    detailFavoriteBtn.setImageResource(
                        if (isChecked) R.drawable.ic_favorite_full else R.drawable.ic_favorite
                    )

                    // Update the product in MainActivity's productList
                    val productList = (activity as MainActivity).productList
                    position?.let { pos ->
                        productList[pos] = param!!
                    }
                }
            }

            detailProfileImg.setImageResource(R.drawable.profile_img)
            detailTemperature.text = "39.3℃"
            detailFace.setImageResource(R.drawable.smile_img)

            // 뒤로가기 버튼 클릭 이벤트
            detailBackBtn.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle) = DetailFragment().apply {
            arguments = bundle
        }
    }
}
