package uz.mrdos.lesson7.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import uz.mrdos.lesson7.R
import uz.mrdos.lesson7.core.ResultCache
import uz.mrdos.lesson7.core.ThemeMode
import uz.mrdos.lesson7.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    companion object {
        val KEY_BEST_STEP_BY_TIME = "best_step_by_time"
        val KEY_BEST_TIME_BY_TIME = "best_time_by_time"

        val KEY_BEST_STEP_BY_STEP = "best_step_by_step"
        val KEY_BEST_TIME_BY_STEP = "best_time_by_step"

        val KEY_LAST_STEP = "last_steps"
        val KEY_LAST_TIME = "last_time"
    }

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setDAtaToView()
    }

    private fun setDAtaToView() {
        var time = 0
        var step = 0
        intent.let {
            time = intent.getIntExtra("TIME", 0)
            step = intent.getIntExtra("STEP", 0)
        }

        val timeFormat = String.format(
            "%02d:%02d:%02d",
            time / 60 / 60 % 24,
            time / 60 % 60,
            time % 60
        )

        binding.currentStep.text = step.toString()
        binding.currentTime.text = timeFormat

        loadThemeModeData()
    }

    private fun loadThemeModeData() {
        val lastMode = ResultCache.getLastThemeMode()

        when (lastMode) {
            ThemeMode.DAY -> {
                binding.root.setBackgroundColor(getColor(R.color.background_day))
                binding.tableLayout.setBackgroundColor(getColor(R.color.background_table_layout_day))
            }
            else -> {
                binding.root.setBackgroundColor(getColor(R.color.background_night))
                binding.tableLayout.setBackgroundColor(getColor(R.color.background_table_layout_night))

            }
        }
    }
}