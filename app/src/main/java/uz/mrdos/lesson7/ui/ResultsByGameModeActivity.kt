package uz.mrdos.lesson7.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import uz.mrdos.lesson7.R
import uz.mrdos.lesson7.core.GameMode
import uz.mrdos.lesson7.core.ResultCache
import uz.mrdos.lesson7.databinding.ActivityGameModeBinding
import uz.mrdos.lesson7.databinding.ActivityResultsByGameModeBinding
import uz.mrdos.lesson7.databinding.ItemResultBinding

class ResultsByGameModeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultsByGameModeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsByGameModeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var modeType = 3
        intent.let {
            modeType = intent.getIntExtra(GameModeActivity.KEY_NECESSARY, 3)
        }


        val lastResult = ResultCache.getAllResultsByGameMode(
            when(modeType){
                4 -> GameMode.MODE_FOUR
                5 -> GameMode.MODE_FIVE
                else -> GameMode.MODE_THREE
            }
        )
        binding.resultTitle.setText("results for game type ${modeType}x${modeType} ")
        lastResult.forEachIndexed { i, element ->

            val itemBinding = ItemResultBinding.inflate(LayoutInflater.from(this))
            val totalTime = element.totalTime
            val timeFormat = String.format(
                "%02d:%02d:%02d",
                totalTime / 60 / 60 % 24,
                totalTime / 60 % 60,
                totalTime % 60
            )
            itemBinding.orderView.text = "${i + 1}."
            itemBinding.stepView.text = "step: ${element.totalStep}"
            itemBinding.timeView.text = "time: $timeFormat"

            binding.resultGroup.addView(itemBinding.root)
//            Kotlin da dinamic dizayn qilish
            val imageView = ImageView(this)
            val layoutParam = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                5,
            )

            imageView.setBackgroundColor(Color.GRAY)

            imageView.layoutParams = layoutParam
            binding.resultGroup.addView(imageView)

        }
    }
}