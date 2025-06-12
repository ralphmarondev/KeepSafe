package com.ralphmarondev.keepsafe.family_list.presentation

import androidx.lifecycle.ViewModel
import com.ralphmarondev.keepsafe.family_list.domain.model.FamilyMember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FamilyListViewModel : ViewModel() {

    private val _familyMembers = MutableStateFlow<List<FamilyMember>>(emptyList())
    val familyMember = _familyMembers.asStateFlow()


    init {
        _familyMembers.value += FamilyMember(
            id = 0,
            firstName = "First Name 1",
            lastName = "LN 1",
            middleName = "MN 1",
            role = "Father",
            birthDay = "June 5, 2025",
            birthPlace = "Earth",
            contactNumber = "09123456789",
            email = "firstname1@gmail.com"
        )
        _familyMembers.value += FamilyMember(
            id = 1,
            firstName = "First Name 2",
            lastName = "LN 2",
            middleName = "MN 2",
            role = "Wife",
            birthDay = "June 4, 2025",
            birthPlace = "Mars",
            contactNumber = "09612345678",
            email = "firstname2@gmail.com"
        )
    }
}