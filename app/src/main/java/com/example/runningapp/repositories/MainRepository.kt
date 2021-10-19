package com.example.runningapp.repositories

import androidx.lifecycle.LiveData
import com.example.runningapp.db.Run
import com.example.runningapp.db.RunDAO
import javax.inject.Inject

class MainRepository @Inject constructor(
    val runDAO: RunDAO
){
    suspend fun insertRun(run: Run) { runDAO.insertRun(run) }

    suspend fun deleteRun(run: Run) { runDAO.deleteRun(run) }

    fun getAllRunsSortedByDate() : LiveData<List<Run>>  { return runDAO.getAllRunsSortedByDate() }

    fun getAllRunsSortedByDistance() : LiveData<List<Run>> { return runDAO.getAllRunsSortedByDistanceInMeters() }

    fun getAllRunsSortedByAvgSpeed() : LiveData<List<Run>> { return runDAO.getAllRunsSortedByAvgSpeed() }

    fun getAllRunsSortedByCaloriesBurned() : LiveData<List<Run>>{ return runDAO.getAllRunsSortedByCaloriesBurned() }

    fun getAllRunsSortedByTimeInMillis() : LiveData<List<Run>>{ return runDAO.getAllRunsSortedByTimeInMillis() }

    fun getTotalAvgSpeed() : LiveData<Float> {return runDAO.getTotalAvgSpeed()}

    fun getTotalDistanceInMeters() : LiveData<Int> {return runDAO.getTotalDistanceInMeters()}

    fun getTotalCaloriesBurned() : LiveData<Int> {return runDAO.getTotalCaloriesBurned()}

    fun getTotalTimeInMillis() : LiveData<Long> {return runDAO.getTotalTimeInMillis()}



}