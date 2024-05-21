package com.devapps.questionsoccer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.devapps.questionsoccer.adapters.CountryAdapter
import com.devapps.questionsoccer.databinding.FragmentCountriesBinding
import com.devapps.questionsoccer.interfaces.CountryService
import com.devapps.questionsoccer.items.ItemCountry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Countries : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentCountriesBinding
    private lateinit var adapter: CountryAdapter
    private var CountriesFragmentResponse = mutableListOf<ItemCountry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CountryAdapter(CountriesFragmentResponse)
        binding.rvCountriesFragment.layoutManager = LinearLayoutManager(context)
        binding.rvCountriesFragment.adapter = adapter
        getCountries()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://v3.football.api-sports.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getCountries(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(CountryService::class.java).getCountries()
            val countriesResponse = call.body()
            if(call.isSuccessful){
                val countries = countriesResponse?.response ?: emptyList()
                withContext(Dispatchers.Main){
                    CountriesFragmentResponse.clear()
                    CountriesFragmentResponse.addAll(countries)
                    adapter.notifyDataSetChanged()
                }
                Log.d("MyFavorites", "JSON data: $countries")
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Countries().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}