package com.example.mariajeu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.mariajeu.databinding.FragmentMypageBinding

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
//        var myRestaurantList = RestaurantAdapter.myPageRestaurantList
//
//        fun setValues() {
//            val adapter = MyPageAdapter(requireContext(), myRestaurantList)
//            binding.lvMypage.adapter = adapter
//        }
//
//        setValues()
//
//        return binding.root

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    MyPageFragmentScreen()
                }
            }
        }

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

        var myRestaurantList = RestaurantAdapter.myPageRestaurantList

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MyPageFragmentLogo()
            MyPageFragmentLogin()
        }

        MypageFragmentTitleText()

        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {

            Spacer(modifier = Modifier.height(213.dp))
            MypageFragmentReservationText()

            MyPageFragmentListView(myRestaurantList)

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

    @Composable
    fun MypageFragmentTitleText(){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(93.dp))

            Text(
                text = "My Page",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
            )

            Text(
                text = "'당신을 위한 와인 안주 추천 서비스'",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
    }

    @Composable
    fun MypageFragmentReservationText() {
        Text(
            text = "나의 예약",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 10.dp, start = 40.dp)
        )
    }

    @Composable
    fun MyPageFragmentListView(myPageLists: ArrayList<ReservedRestaurant>) {
        LazyColumn(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .size(width = 330.dp, height = 460.dp)
        ) {
            items(myPageLists) { myPageList ->
                ReservationItem(myPageList)
            }
        }
    }

    @Composable
    fun ReservationItem(reservation: ReservedRestaurant) {
        Column {
            // 콜키지 비용 무료 버튼

            Row {
                Box(
                    Modifier
                        .width(80.dp)
                        .height(90.dp)
                        .background(Color.Gray)
                )
                Column {
                    Text(reservation.restaurantName)

                    when (reservation.reservedTime) {
                        1 -> Text(text = " - 오후 6:00 - ")
                        2 -> Text(text = " - 오후 6:30 - ")
                        3 -> Text(text = " - 오후 7:00 - ")
                        4 -> Text(text = " - 오후 7:30 - ")
                        5 -> Text(text = " - 오후 8:00 - ")
                    }

                    // 취소 버튼
//                    Button()
                    // 당일 취소 안 된다는 문구
                }

            }

        }


    }

}

@Preview(showBackground = true)
@Composable
fun MyPageFragmentScreenPreView() {
    MypageFragment().MyPageFragmentScreen()
}