package com.ralphmarondev.keepsafe.feature.family.presentation.member_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ralphmarondev.keepsafe.domain.repository.MemberRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class MemberDetailViewModel(
    private val uid: String,
    private val memberRepository: MemberRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MemberDetailState())
    val state = _state.asStateFlow()

    init {
        loadInformation()
    }

    fun onAction(action: MemberDetailAction) {
        when (action) {
            MemberDetailAction.LoadInformation -> loadInformation()
            MemberDetailAction.Refresh -> loadInformation(isRefreshing = true)
        }
    }

    private fun loadInformation(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        isRefreshing = isRefreshing,
                        isLoading = true,
                        isError = false,
                        errorMessage = null,
                        showErrorMessage = false
                    )
                }
                val member = memberRepository.getMembers()
                    .filter { it.uid == uid }

                if (isRefreshing) {
                    delay(1000.milliseconds)
                }

                if (member.isEmpty()) {
                    _state.update {
                        it.copy(
                            isError = true,
                            showErrorMessage = true,
                            errorMessage = "Member not found."
                        )
                    }
                    return@launch
                }

                _state.update { it.copy(member = member[0]) }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _state.update {
                    it.copy(isLoading = false, isRefreshing = false)
                }
            }
        }
    }
}