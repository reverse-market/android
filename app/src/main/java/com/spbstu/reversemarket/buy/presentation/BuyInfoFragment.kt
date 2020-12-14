package com.spbstu.reversemarket.buy.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.spbstu.reversemarket.R
import com.spbstu.reversemarket.base.InjectionFragment
import com.spbstu.reversemarket.buy.data.model.Request
import com.spbstu.reversemarket.buy.domain.Address
import com.spbstu.reversemarket.category.data.model.Category
import com.spbstu.reversemarket.filter.data.model.Tag
import com.spbstu.reversemarket.profile.data.model.AddressBodyWithId
import com.spbstu.reversemarket.sell.presentation.adapter.PhotoAdapter
import com.spbstu.reversemarket.sell.presentation.adapter.TagsAdapter
import com.spbstu.reversemarket.utils.AddSearchViewUtils.Companion.NO_MARGIN_FLAG
import com.spbstu.reversemarket.utils.AddSearchViewUtils.Companion.addTag
import com.spbstu.reversemarket.utils.AddSearchViewUtils.Companion.getFocusListener
import com.spbstu.reversemarket.utils.PermissionUtils
import com.spbstu.reversemarket.utils.Utils
import kotlinx.android.synthetic.main.fragment_buy_info.*
import kotlinx.android.synthetic.main.layout_new_product.*
import kotlinx.android.synthetic.main.layout_photos.*
import java.io.File
import java.lang.Exception
import java.util.*


class BuyInfoFragment : InjectionFragment<BuyInfoViewModel>(R.layout.fragment_buy_info) {

    companion object {
        const val PICK_IMAGE = 50123
    }

    private lateinit var photosList: RecyclerView
    private lateinit var addressList: RecyclerView
    private lateinit var search: EditText
    private lateinit var searchButtonBackground: RelativeLayout
    private lateinit var searchOpenLayout: View
    private lateinit var closeBtn: ImageView
    private lateinit var selectedTagsList: RecyclerView
    private lateinit var addTagsList: RecyclerView
    private var attachedImageFile: File? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ImageView>(R.id.frg_buy_info__back_btn).setOnClickListener {
            findNavController().navigate(R.id.navigation_buy)
        }

        frg_buy_info__address.visibility = View.GONE
        photosList = view.findViewById(R.id.layout_new_product__photo_list)
        photosList.adapter = PhotoAdapter(
            provideUrlList(),
            requireContext(),
            Glide.with(this)
        )

        addressList = view.findViewById(R.id.layout_address_list)
        addressList.adapter = AddressAdapter(
            emptyList(),
            ::provideAddressClickListener,
            R.id.buyInfoFragment
        )

        selectedTagsList = view.findViewById(R.id.frg_filter__selected_categories)
        selectedTagsList.adapter =
            TagsAdapter(
                emptyList(),
                R.layout.layout_filter_selected_item,
                addFunc = { tag: Tag -> addTag(addTagsList, tag) },
            )

        searchOpenLayout = view.findViewById(R.id.frg_but_info__selected_tags)
        searchButtonBackground = view.findViewById(R.id.frg_but_info__search_text_background)
        search = view.findViewById(R.id.layout_search__toolbar_search__text)
        closeBtn = view.findViewById(R.id.layout_search__toolbar_search_close_btn)
        closeBtn.setOnClickListener { Utils.closeKeyboard(activity, search) }

        addTagsList = view.findViewById(R.id.layout_selected_tags__new_tags)

        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        layoutManager.flexWrap = FlexWrap.WRAP
        addTagsList.layoutManager = layoutManager

        searchOpenLayout.viewTreeObserver.addOnGlobalLayoutListener(
            getFocusListener(
                search,
                searchButtonBackground,
                searchOpenLayout,
                closeBtn,
                NO_MARGIN_FLAG
            )
        )


        layout_new_product__photo_list.adapter = PhotoAdapter(
            provideUrlList(),
            requireContext(),
            Glide.with(this)
        ) {
            PermissionUtils.checkReadWriteStorage(requireContext()) {
                if (it) {
                    val intent = Intent(
                        Intent.ACTION_GET_CONTENT,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    this.attachedImageFile =
                        File.createTempFile(
                            System.currentTimeMillis().toString(),
                            ".jpg",
                            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                        )
                    intent.type = "image/*"
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
                    startActivityForResult(
                        Intent.createChooser(intent, getString(R.string.choose_photo)),
                        PICK_IMAGE
                    )
                }
            }
        }

        frg_buy_info__save_btn.setOnClickListener {
            try {
                val category: Int = allCategories[frg_but_info__category.selectedItemPosition].id
                val description = layout_new_product__description_text.text.toString()
                val price = layout_new_product__price.text.toString().toInt()
                val amount = layout_new_product__amount.text.toString().toInt()
                val name = layout_new_product__name.text.toString()
                val itemName = layout_new_product__itemName.text.toString()
                val tags = (addTagsList.adapter as TagsAdapter).tags
                if (description.isBlank() || name.isBlank() || itemName.isBlank()) {
                    throw IllegalArgumentException("Fields must not be blank!")
                }
                val request =
                    Request(name, itemName, description, emptyList(), price, amount, tags, category, Date())
                viewModel.createRequest(request).observe(viewLifecycleOwner, {
                    if (it) {
                        try {
                            findNavController().popBackStack()
                        } catch (ignored: Exception) {
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Не удалось создать объявление",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Неверно заполнены поля!", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private lateinit var allCategories: List<Category>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO Change provideAddTagsList() to it, when backend part is done
        viewModel.getTags(true).observe(viewLifecycleOwner, {
            addTagsList.adapter =
                TagsAdapter(
                    it,
                    R.layout.layout_add_tag,
                    addFunc = { tag: Tag -> addTag(selectedTagsList, tag) },
                )
        })

        viewModel.getCategories().observe(viewLifecycleOwner, {
            allCategories = it
            frg_but_info__category.adapter = ArrayAdapter<String>(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                it.map { it.name }
            )
            frg_but_info__category.setSelection(0)
            frg_but_info__category.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        //Toast.makeText(requireContext(), "${allCategories[p2]}", Toast.LENGTH_SHORT).show()
                        viewModel.getTags(true, allCategories[p2].id)
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }

                }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE) {
            val uri = data?.data
            if (uri != null) {
                val file = attachedImageFile ?: return
                viewModel.copyUriDataToFile(requireActivity().contentResolver, uri, file)
                viewModel.addImageFile(uri, file)
                val adapter = layout_new_product__photo_list.adapter as PhotoAdapter
                val newList = mutableListOf<String>()
                newList.addAll(adapter.urls)
                newList.add(uri.toString())
                adapter.urls = newList
            }
        }
    }

    private fun provideUrlList(): List<String> = listOf("null")

    private fun provideAddTagsList(): List<Tag> = listOf(
        Tag(0, "Кроссовки"),
        Tag(0, "Костюмы"),
        Tag(0, "Брюки"),
        Tag(0, "Джинсы"),
        Tag(0, "Шорты"),
        Tag(0, "Носки"),
        Tag(0, "Куртки"),
        Tag(0, "Пальто"),
        Tag(0, "Футболки"),
        Tag(0, "Плавки")
    )

    private fun provideSelectedList(): List<Tag> =
        listOf(Tag(0, "Обувь"), Tag(0, "Кроссовки"), Tag(0, "Санкт-Петербург"))

    private fun provideAddresses(): List<Address> = listOf(
        Address(
            "Мой адрес",
            "Ленинградская область, г. Санкт-Петербург, Невский просп. д.28, кв. 1, 190000",
            "Иванов Иван Иванович"
        ),
        Address(
            "Работа",
            "Ленинградская область, г. Санкт-Петербург, Невский просп. д.35, кв. 30, 190000",
            "Иванов Иван Иванович"
        )
    )

    private fun provideAddressClickListener(address: AddressBodyWithId) {

    }

}