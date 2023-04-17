package uz.mrdos.lesson7.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import uz.mrdos.lesson7.R
import uz.mrdos.lesson7.core.ResultCache
import uz.mrdos.lesson7.core.ThemeMode
import uz.mrdos.lesson7.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadThemeModeData()

        binding.themeModeGroup.setOnCheckedChangeListener { _, checedId ->
            ResultCache.saveLastThemeMode(if (checedId == binding.dayMode.id) ThemeMode.DAY else ThemeMode.NIGHT)
            loadThemeModeData()
        }

        binding.clearAllSettings.setOnClickListener {
            ResultCache.clearAllData()
            loadThemeModeData()
            Toast.makeText(this, "All settings reset.", Toast.LENGTH_SHORT).show()
        }

        binding.clearBestResults.setOnClickListener {
            ResultCache.clearBestResult()
            Toast.makeText(this, "All results removed.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun loadThemeModeData() {
        val lastMode = ResultCache.getLastThemeMode()

        when (lastMode) {
            ThemeMode.DAY -> {
                binding.themeModeGroup.check(binding.dayMode.id)
                binding.root.setBackgroundColor(getColor(R.color.background_day))
                binding.tableLayout.setBackgroundColor(getColor(R.color.background_table_layout_day))
            }
            else -> {
                binding.themeModeGroup.check(binding.nightMode.id)
                binding.root.setBackgroundColor(getColor(R.color.background_night))
                binding.tableLayout.setBackgroundColor(getColor(R.color.background_table_layout_night))

            }
        }
    }
}