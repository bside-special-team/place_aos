package com.special.place.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor() : ViewModel(), OnBoardingListener {
    private val _onBoardingList: MutableLiveData<List<OnBoarding>> = MutableLiveData()
    override val onBoardingList: LiveData<List<OnBoarding>> = _onBoardingList

    fun init() {
        val list = mutableListOf<OnBoarding>()
        list.add(
            OnBoarding(
                0, "우리동네 히든플레이스를\n" +
                        "공유해보세요!", "일상의 인상 깊은 모든 순간들을 기록해보세요"
            )
        )
        list.add(
            OnBoarding(
                1, "동네를 탐험하며\n" +
                        "히든플레이스를 찾아보세요!", "히든 플레이스 50m이내에서 방문 인증을 할 수 있어요\n" +
                        "방문 인증 전에는 히든 플레이스 정보를 볼 수 없어요"
            )
        )
        list.add(
            OnBoarding(
                2, "우리동네 랜드마크를\n" +
                        "발견해보세요!", "추천이 20개 이상 모이면 랜드마크가 세워져요\n" +
                        "랜드마크는 방문 인증 전에도 누구나 볼 수 있답니다"
            )
        )
        list.add(
            OnBoarding(
                3, "경험치를 모아\n" +
                        "동네 마스터에 도전해보세요!", "출석, 댓글, 글 작성, 방문인증에 따른 경험치를 드려요"
            )
        )
        _onBoardingList.postValue(list)
    }


}