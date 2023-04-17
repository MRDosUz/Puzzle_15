package uz.mrdos.lesson7.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.drawable.GradientDrawable.Orientation
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import uz.mrdos.lesson7.R
import uz.mrdos.lesson7.core.ResultCache
import uz.mrdos.lesson7.databinding.ActivityMainBinding
import uz.mrdos.lesson7.databinding.ActivityResultBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setActionToView()

    }

    private fun setActionToView() {
        binding.startButton.setOnClickListener{
            val intent = Intent(this@MainActivity, GameModeActivity::class.java)
            ResultCache.saveForGamePage()
            val bundle = Bundle()
            intent.putExtras(bundle)
            startActivity(intent)
        }

        binding.resultButton.setOnClickListener{
            val intent = Intent(this@MainActivity, GameModeActivity::class.java)
            ResultCache.saveForResultPage()
            val bundle = bundleOf("KEY_NECESSARY" to 50.0)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        binding.settingButton.setOnClickListener{
            val intent = Intent(this@MainActivity, SettingActivity::class.java)
            val bundle = Bundle()
            intent.putExtras(bundle)
            startActivity(intent)
        }

    }
}