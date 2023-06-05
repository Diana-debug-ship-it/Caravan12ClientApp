package com.caravan12.DianaRadchuk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caravan12.DianaRadchuk.R
import com.caravan12.DianaRadchuk.adapters.DirectionsRVAdapter
import com.caravan12.DianaRadchuk.data_classes.TopDirection
import com.caravan12.DianaRadchuk.databinding.FragmentMainBinding

class HomeFragment : Fragment() {

    private lateinit var adapter: DirectionsRVAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var binding: FragmentMainBinding

    private lateinit var directionsArray: Array<String>
    private lateinit var descriptionsArray: Array<String>
    private lateinit var imagesArray: Array<Int>

    private lateinit var topDirectionArray: ArrayList<TopDirection>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topDirectionArray = arrayListOf<TopDirection>()
        getData()
        setData()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment
    }

    private fun getData() {
        directionsArray = arrayOf(
            getString(R.string.strTurkey),
            getString(R.string.strDominikana),
            getString(R.string.strThailand),
            getString(R.string.strVietnam))
        descriptionsArray = arrayOf(
            "Турция — очаровательная страна. Здесь переплелись сотни культур, многочисленные цивилизации оставили свой след на земле Малой Азии, территорию которой занимает современная Турция. Богатая история, памятники со времен Византийской империи, прекрасные мечети и дворцы Османской империи, уникальные природные достопримечательности, мягкий климат, замечательные пляжи и великолепная кухня привлекают в Турцию путешественников со всего мира.",
            "Доминиканская Республика — благодатный край, раскинувшийся на востоке острова Гаити в окружении волн Карибского моря. Столица — знойный Санто-Доминго, родина эталонных сигар, колыбель зажигательной меренге.\nДоминикана живет в ритмах бачаты и сальсы, приглашая всех желающих окунуться в жизнерадостную атмосферу мира, где каждый день — как маленький праздник.",
            "Таиланд — страна жаркого солнца, ласкового моря и невероятного драйва. Просторные чистые пляжи, экзотическая растительность и улыбки местных жителей оставляют у российских туристов отличные воспоминания об отдыхе в Таиланде. На побережье найдётся место и семьям с маленькими детьми, и тусовщикам, и любителям достопримечательностей — курорты в Таиланде есть на любой вкус.",
            "Вьетнам – чарующая страна, наполненная улыбками местных жителей и жарким солнцем. Вьетнам интересен для любого типа туристов: для любителей понежиться на пляже, для любителей путешествий и для тех, кто просто хочет отдохнуть и увидеть что-то новое, необычное.Кроме природных и архитектурных достопримечательностей, Вьетнам сохранил французский флёр. В бывшей колонии можно попробовать превосходные круассаны, фуа-гру и другие шедевры изысканной кухни."
        )
        imagesArray = arrayOf(
            R.drawable.turkey,
            R.drawable.dominikana,
            R.drawable.thailand,
            R.drawable.vietnam
        )

        for (i in 0..3) {
            topDirectionArray.add(TopDirection(imagesArray[i], directionsArray[i], descriptionsArray[i]))
        }
    }

    private fun setData() {
        val layoutManager = LinearLayoutManager(context)
        recyclerView = binding.recyclerViewTopDirections
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        adapter = DirectionsRVAdapter(topDirectionArray)
        recyclerView.adapter = adapter
    }
}