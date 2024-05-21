package com.example.mariajeu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.mariajeu.databinding.FragmentMypageBinding
import kotlin.math.log

class MypageFragment : Fragment() {

    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    lateinit var mainActivity: MainActivity
    lateinit var userId: String

    private val client = RetrofitInstance.getInstance().create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = FragmentMypageBinding.inflate(layoutInflater)

        userId = ""

        arguments.let {
            var id = it?.getString("loginId") ?: "로그인이 필요한 서비스입니다"
            if (id != "로그인이 필요한 서비스입니다") {
                userId = id
                Log.d("useriduserid", userId)
            }

            Log.d("전달후", id)
        }

        binding.mypageLoginTv.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        binding = FragmentMypageBinding.inflate(inflater, container, false)
        var myRestaurantList = RestaurantAdapter.myPageRestaurantList

        fun setValues() {
            val adapter = MyPageAdapter(requireContext(), myRestaurantList)
            binding.lvMypage.adapter = adapter
        }

        setValues()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.tvMypageNickname.text = userId
    }


    fun mypageLogin() {
        binding.mypageLoginTv.visibility = View.GONE
        binding.mypageLogoutTv.visibility = View.VISIBLE
    }

    fun mypageLogout() {
        binding.mypageLoginTv.visibility = View.VISIBLE
        binding.mypageLogoutTv.visibility = View.GONE
    }

    fun navigateToFilterDateFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.filter_date_constraintlayout, fragment )
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    @Composable
    fun MyPageFragmentScreen() {
        Row(Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
            )
        {
            MyPageFragmentLogo()
            MyPageFragmentLogin()
        }
    }

    @Composable
    fun MyPageFragmentLogo() {
        Image(
            painter = painterResource(id = R.drawable.ic_start_logo),
            contentDescription = "logo",
            modifier = Modifier
                .padding(start = 20.68.dp, top = 17.dp)
        )
    }

    @Composable
    fun MyPageFragmentLogin() {
        var isLoggedIn by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp, end = 20.dp),
            horizontalAlignment = Alignment.End
        ) {
            if (isLoggedIn) {
                Text(
                    text = "LOGOUT",
                    fontSize = 11.sp
                )
            } else {
                Text(
                    text = "LOGIN",
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MyPageFragmentScreenPreView() {
    MypageFragment().MyPageFragmentScreen()
}