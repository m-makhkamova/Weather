package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.weatherapp.databinding.FragmentHomeBinding
import org.json.JSONArray

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private val url = "https://api.weatherapi.com/v1/forecast.json?key=11b9394e7e024a2588a44954230610&q=Tashkent&days=8&aqi=no&alerts=no"
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val requestQue = Volley.newRequestQueue(requireContext())
        var forecastAdapter = ForecastAdapter(JSONArray())
        binding.rvTodayWeather.setHasFixedSize(true)
        binding.rvNextWeekWeather.setHasFixedSize(true)
        binding.rvNextWeekWeather.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvTodayWeather.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        val request = JsonObjectRequest(url,
            { response ->
                val current = response.getJSONObject("current")
                val tempC = current.getDouble("temp_c")


                binding.weatherDegree.text = "${tempC}°"

                forecastAdapter = ForecastAdapter(response.getJSONObject("forecast").getJSONArray("forecastday"))
                binding.rvNextWeekWeather.adapter = forecastAdapter
                binding.rvTodayWeather.adapter = TodayAdapter(response.getJSONObject("forecast").getJSONArray("forecastday")
                    .getJSONObject(0).getJSONArray("hour"))


                forecastAdapter.notifyDataSetChanged()
                Log.d("TAG", "$response")
            }
        ) { error -> Log.d("TAG", "onErrorResponse: $error") }
        requestQue.add(request)




        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}