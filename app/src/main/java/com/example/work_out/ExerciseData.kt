package com.example.work_out

object ExerciseData {

    fun dataList(): ArrayList<Exercise>{
        val myArrayListData = ArrayList<Exercise>()

        val data1 = Exercise(
            1,
            "pike_push_up",
            R.drawable.pike_push_up,
            false,
            false
        )
        val data3 = Exercise(
            3,
            "bridge_exercise",
            R.drawable.bridge_exercise,
            false,
            false
        )
        val data4 = Exercise(
            4,
            "burpee",
            R.drawable.burpee,
            false,
            false
        )
        val data5 = Exercise(
            5,
            "full_plank_and_low_plank",
            R.drawable.full_plank_and_low_plank,
            false,
            false
        )
        val data6 = Exercise(
            6,
            "hip_thrust",
            R.drawable.hip_thrust,
            false,
            false
        )
        val data7 = Exercise(
            7,
            "leg_raise",
            R.drawable.leg_raise,
            false,
            false
        )
        val data8 = Exercise(
            8,
            "plank_shoulder_taps",
            R.drawable.plank_shoulder_taps,
            false,
            false
        )
        val data9 = Exercise(
            9,
            "side_plank_leg_raise",
            R.drawable.side_plank_leg_raise,
            false,
            false
        )
        val data10 = Exercise(
            10,
            "squat_jumps",
            R.drawable.squat_jumps,
            false,
            false
        )
        val data11 = Exercise(
            11,
            "v_sit",
            R.drawable.v_sit,
            false,
            false
        )

        myArrayListData.addAll(arrayOf(data1, data3, data4, data5, data6, data7, data8, data9, data10, data11))

        return myArrayListData
    }
}