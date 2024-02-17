package com.example.mariajeu

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView

class ReservationDialog(context: Context) : Dialog(context) {

    companion object {
        lateinit var btnGoback: ImageButton
        lateinit var btnMyPage: ImageButton
        lateinit var rName: TextView
        lateinit var rDate: TextView
        lateinit var rTime: TextView
        lateinit var rCnt: TextView

        var myPageFragment: MypageFragment = MypageFragment()

    }

    init {
        // 클래스 초기화 시에 초기화 블록(init)을 사용하여 변수 초기화
        setContentView(R.layout.dialog_reservation)

        // 커스텀 다이얼로그 레이아웃의 UI 요소에 대한 이벤트 처리
        val btnGoback: ImageButton = findViewById(R.id.dialog_button_goback)
        val btnMyPage: ImageButton = findViewById(R.id.dialog_button_mypage)

        rName = findViewById(R.id.tv_dialog_name)
        rDate = findViewById(R.id.tv_dialog_date)
        rTime = findViewById(R.id.tv_dialog_time)
        rCnt = findViewById(R.id.tv_dialog_cnt_personnel)

        btnGoback.setOnClickListener {
            Log.d("ReservationDialog", "btnGoback clicked")
            dismiss()
        }

        btnMyPage.setOnClickListener {
            Log.d("ReservationDialog", "btnMyPage clicked")
            dismiss()
        }


        setDateClickListeners()

    }

    private fun setDateClickListeners() {
        // 날짜를 클릭하면 FilterdateFragment로 이동
        rDate.setOnClickListener {
            navigateToFilterDateFragment()
        }

        // 시간을 클릭하면 FiltertimeFragment로 이동
        rTime.setOnClickListener {
            navigateToFilterDateFragment()
        }
    }

    // FilterdateFragment로 이동하는 함수
    private fun navigateToFilterDateFragment() {
        val nextFragment = FilterdateFragment()
        // 이동할 Fragment로 전환
        myPageFragment.navigateToFilterDateFragment(nextFragment)
        // 다이얼로그 닫기
        dismiss()
    }




}



