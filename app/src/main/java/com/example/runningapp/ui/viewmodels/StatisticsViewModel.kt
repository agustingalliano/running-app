package com.example.runningapp.ui.viewmodels

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.runningapp.R
import com.example.runningapp.db.RunDAO
import com.example.runningapp.repositories.MainRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor (
    val mainRepository: MainRepository
): ViewModel() {
}