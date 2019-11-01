package com.zwonb.coolweatherjetpack.ui.area

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zwonb.coolweatherjetpack.R
import com.zwonb.coolweatherjetpack.databinding.ChooseAreaBindingImpl
import com.zwonb.coolweatherjetpack.util.InjectorUtil
import kotlinx.android.synthetic.main.choose_area.*

/**
 * 说明
 *
 * @author zwonb
 * @date 2019-11-01
 */
class ChooseAreaFragment : Fragment(R.layout.choose_area) {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            InjectorUtil.getChooseAreaModelFactory()
        )[ChooseAreaViewModel::class.java]
    }
    private var progressDialog: ProgressDialog? = null
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        val bind = DataBindingUtil.bind<ChooseAreaBindingImpl>(view!!)
        bind?.viewModel = viewModel
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = ChooseAreaAdapter(context!!, R.layout.simple_item, viewModel.dataList)
        listView.adapter = adapter
        observe()
    }

    private fun observe() {
        viewModel.currentLevel.observe(viewLifecycleOwner, Observer {
            when (it) {
                LEVEL_PROVINCE -> {
                    titleText.text = "中国"
                    backButton.visibility = View.GONE
                }
                LEVEL_CITY -> {
                    titleText.text = viewModel.selectedProvince?.provinceName
                    backButton.visibility = View.VISIBLE
                }
                LEVEL_COUNTY -> {
                    titleText.text = viewModel.selectedCity?.cityName
                    backButton.visibility = View.VISIBLE
                }
            }
        })
        viewModel.dataChanged.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
            listView.setSelection(0)
            closeProgressDialog()
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                showProgressDialog()
            } else {
                closeProgressDialog()
            }
        })
        viewModel.areaSelected.observe(viewLifecycleOwner, Observer {
            if (it && viewModel.selectedCounty != null) {
//                if (activity is MainActivity) {
//                    val intent = Intent(activity, WeatherActivity::class.java)
//                    intent.putExtra("weather_id", viewModel.selectedCounty!!.weatherId)
//                    startActivity(intent)
//                    activity?.finish()
//                } else if (activity is WeatherActivity) {
//                    val weatherActivity = activity as WeatherActivity
//                    weatherActivity.drawerLayout.closeDrawers()
//                    weatherActivity.viewModel.weatherId = viewModel.selectedCounty!!.weatherId
//                    weatherActivity.viewModel.refreshWeather()
//                }
                viewModel.areaSelected.value = false
            }
        })
        if (viewModel.dataList.isEmpty()) {
            viewModel.getProvinces()
        }
    }

    private fun showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(activity)
            progressDialog?.setMessage("正在加载...")
            progressDialog?.setCanceledOnTouchOutside(false)
        }
        progressDialog?.show()
    }

    private fun closeProgressDialog() {
        progressDialog?.dismiss()
    }

    companion object {
        const val LEVEL_PROVINCE = 0
        const val LEVEL_CITY = 1
        const val LEVEL_COUNTY = 2
    }
}