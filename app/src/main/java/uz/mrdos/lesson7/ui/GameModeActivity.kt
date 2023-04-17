package uz.mrdos.lesson7.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import uz.mrdos.lesson7.core.ResultCache
import uz.mrdos.lesson7.databinding.ActivityGameModeBinding

class GameModeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameModeBinding

    companion object {
        val KEY_NECESSARY = "key_necessary"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityGameModeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setActionToView()
        loadView()
    }

    private fun loadView() {
        if (!ResultCache.getForGameOrResultPage())
            binding.gameModeTitle.setText("result game mode")
    }

    private fun setActionToView() {

        binding.gameMode3.setOnClickListener{
            val isGame = ResultCache.getForGameOrResultPage()
            val intent = Intent(this@GameModeActivity, if (isGame) Game3Activity::class.java else ResultsByGameModeActivity::class.java)
            val bundle = bundleOf(KEY_NECESSARY to 3)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
        }

        binding.gameMode4.setOnClickListener{
            val isGame = ResultCache.getForGameOrResultPage()
            val intent = Intent(this@GameModeActivity, if (isGame) GameActivity::class.java else ResultsByGameModeActivity::class.java)
            val bundle = bundleOf(KEY_NECESSARY to 4)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
        }

        binding.gameMode5.setOnClickListener{
            val isGame = ResultCache.getForGameOrResultPage()
            val intent = Intent(this@GameModeActivity, if (isGame) Game5Activity::class.java else ResultsByGameModeActivity::class.java)
            val bundle = bundleOf(KEY_NECESSARY to 5)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
        }
    }
}