package uz.mrdos.lesson7.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import uz.mrdos.lesson7.R;
import uz.mrdos.lesson7.core.GameMode;
import uz.mrdos.lesson7.core.ResultCache;
import uz.mrdos.lesson7.core.ResultDto;
import uz.mrdos.lesson7.core.ThemeMode;
import uz.mrdos.lesson7.databinding.ActivityGameBinding;


public class GameActivity extends AppCompatActivity {

    private TextView timerView;
    private TextView stepView;
    private Button pauseButton;

    private Button refreshButton;

    private int countStep = 0;
    private RelativeLayout buttonGroup;
    private TextView[][] buttons = new TextView[4][4];
    private CardView[][] cards = new CardView[4][4];
    private ArrayList<Integer> numbers = new ArrayList<>();
    private int timeCount = 0;
    private int emptyI = 3;
    private int emptyJ = 3;

    private final String STEP_KEY = "step";
    private final String TIME_KEY = "time";
    private final String EMPTY_I_KEY = "empty_i";
    private final String EMPTY_J_KEY = "empty_j";

    private final String BUTTON_1 = "1";
    private final String BUTTON_2 = "2";
    private final String BUTTON_3 = "3";
    private final String BUTTON_4 = "4";
    private final String BUTTON_5 = "5";
    private final String BUTTON_6 = "6";
    private final String BUTTON_7 = "7";
    private final String BUTTON_8 = "8";
    private final String BUTTON_9 = "9";
    private final String BUTTON_10 = "10";
    private final String BUTTON_11 = "11";
    private final String BUTTON_12 = "12";
    private final String BUTTON_13 = "13";
    private final String BUTTON_14 = "14";
    private final String BUTTON_15 = "15";
    private final String BUTTON_16 = "16";
    private final String BUTTONS_IS_HIDDEN = "buttons_is_hidden";

    private ActivityGameBinding binding;


    Timer timer;
    boolean buttonsIsHidden = false;
    int clickPauseCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadViews();

        if (savedInstanceState != null){
            countStep = savedInstanceState.getInt(STEP_KEY, 0);
            timeCount= savedInstanceState.getInt(TIME_KEY, 0);

            if (savedInstanceState.getBoolean(BUTTONS_IS_HIDDEN, false)) onButtonClickPauseButton(this.pauseButton);
            countTime();
            emptyI= savedInstanceState.getInt(EMPTY_I_KEY, 3);
            emptyJ= savedInstanceState.getInt(EMPTY_J_KEY, 3);

            numbers = new ArrayList<>();
            numbers.add(Integer.parseInt(savedInstanceState.getString(BUTTON_1, "1")));
            numbers.add(Integer.parseInt(savedInstanceState.getString(BUTTON_2, "2")));
            numbers.add(Integer.parseInt(savedInstanceState.getString(BUTTON_3, "3")));
            numbers.add(Integer.parseInt(savedInstanceState.getString(BUTTON_4, "4")));
            numbers.add(Integer.parseInt(savedInstanceState.getString(BUTTON_5, "5")));
            numbers.add(Integer.parseInt(savedInstanceState.getString(BUTTON_6, "6")));
            numbers.add(Integer.parseInt(savedInstanceState.getString(BUTTON_7, "7")));
            numbers.add(Integer.parseInt(savedInstanceState.getString(BUTTON_8, "8")));
            numbers.add(Integer.parseInt(savedInstanceState.getString(BUTTON_9, "9")));
            numbers.add(Integer.parseInt(savedInstanceState.getString(BUTTON_10, "10")));
            numbers.add(Integer.parseInt(savedInstanceState.getString(BUTTON_11, "11")));
            numbers.add(Integer.parseInt(savedInstanceState.getString(BUTTON_12, "12")));
            numbers.add(Integer.parseInt(savedInstanceState.getString(BUTTON_13, "13")));
            numbers.add(Integer.parseInt(savedInstanceState.getString(BUTTON_14, "14")));
            numbers.add(Integer.parseInt(savedInstanceState.getString(BUTTON_15, "15")));
            numbers.add(Integer.parseInt(savedInstanceState.getString(BUTTON_16, "16")));

        }else {
            loadData();
        }

        loadDataToView();
        loadActions();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putInt(STEP_KEY, countStep);
        outState.putInt(TIME_KEY, timeCount);
        outState.putInt(EMPTY_I_KEY, emptyI);
        outState.putInt(EMPTY_J_KEY, emptyJ);

        outState.putString(BUTTON_1, ((TextView)cards[0][0].getChildAt(0)).getText().toString());
        outState.putString(BUTTON_2, ((TextView)cards[0][1].getChildAt(0)).getText().toString());
        outState.putString(BUTTON_3, ((TextView)cards[0][2].getChildAt(0)).getText().toString());
        outState.putString(BUTTON_4, ((TextView)cards[0][3].getChildAt(0)).getText().toString());
        outState.putString(BUTTON_5, ((TextView)cards[1][0].getChildAt(0)).getText().toString());
        outState.putString(BUTTON_6, ((TextView)cards[1][1].getChildAt(0)).getText().toString());
        outState.putString(BUTTON_7, ((TextView)cards[1][2].getChildAt(0)).getText().toString());
        outState.putString(BUTTON_8, ((TextView)cards[1][3].getChildAt(0)).getText().toString());
        outState.putString(BUTTON_9, ((TextView)cards[2][0].getChildAt(0)).getText().toString());
        outState.putString(BUTTON_10, ((TextView)cards[2][1].getChildAt(0)).getText().toString());
        outState.putString(BUTTON_11, ((TextView)cards[2][2].getChildAt(0)).getText().toString());
        outState.putString(BUTTON_12, ((TextView)cards[2][3].getChildAt(0)).getText().toString());
        outState.putString(BUTTON_13, ((TextView)cards[3][0].getChildAt(0)).getText().toString());
        outState.putString(BUTTON_14, ((TextView)cards[3][1].getChildAt(0)).getText().toString());
        outState.putString(BUTTON_15, ((TextView)cards[3][2].getChildAt(0)).getText().toString());
        outState.putString(BUTTON_16, ((TextView)cards[3][3].getChildAt(0)).getText().toString());
        outState.putBoolean(BUTTONS_IS_HIDDEN, buttonsIsHidden);


        super.onSaveInstanceState(outState);
    }


    private void loadViews() {
        timerView = findViewById(R.id.time_count_view);
        stepView = findViewById(R.id.step_count_view);
        buttonGroup = findViewById(R.id.button_group);
        pauseButton = findViewById(R.id.pause_view);
        refreshButton = findViewById(R.id.refresh_view);

        for (int i = 0; i < 16; i++) {
            CardView cardView = (CardView) buttonGroup.getChildAt(i);
            cards[i / 4][i % 4] = cardView;

            TextView textView = (TextView) cardView.getChildAt(0);
            buttons[i / 4][i % 4] = textView;
        }
    }

    private void loadData() {
        numbers = new ArrayList<>();
        for (int i = 1; i < 16; i++) {
            numbers.add(i);
        }
//        Collections.shuffle(numbers);
        numbers.add(0);
    }

    private void loadDataToView() {


        int second = timeCount % 60;
        int minute = timeCount / 60 % 60;
        int hour = timeCount / 60 / 60 % 24;

        String timeFormat = String.format("%02d:%02d:%02d", hour, minute, second);
        timerView.setText("Time: " + timeFormat);

        stepView.setText(String.valueOf(countStep));
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                countTime();
            }
        };
        timer.scheduleAtFixedRate(timerTask, 1_000, 1_000);

        for (int i = 0; i < 16; i++) {
            buttons[i / 4][i % 4].setText(numbers.get(i).toString());
        }

        cards[emptyI][emptyJ].setVisibility(View.INVISIBLE);

        loadThemeModeData();
    }

    private void loadActions() {

        for (int i = 0; i < 16; i++) {
            buttons[i / 4][i % 4].setOnClickListener(this::onButtonClickButton);
        }

        pauseButton.setOnClickListener(this::onButtonClickPauseButton);

        refreshButton.setOnClickListener(this::onButtonClickRefreshButton);

    }

    private void onButtonClickPauseButton(View view) {
        clickPauseCount++;
        if (clickPauseCount % 2 == 0) {
            pauseButton.setText("PAUSE");
            pauseButton.setBackgroundColor(Color.parseColor("#4CAF50"));
        } else {
            pauseButton.setText("PLAY");
            pauseButton.setBackgroundColor(Color.RED);
        }

        if (!buttonsIsHidden) {
            for (int i = 0; i < 16; i++) {
                buttons[i / 4][i % 4].setVisibility(View.INVISIBLE);
            }
        } else {
            for (int i = 0; i < 16; i++) {
                buttons[i / 4][i % 4].setVisibility(View.VISIBLE);
            }
        }
        buttonsIsHidden = !buttonsIsHidden;
    }

    private void onButtonClickRefreshButton(View view) {
        countStep = 0;
        timeCount = 0;
        cards[emptyI][emptyJ].setVisibility(View.VISIBLE);
        emptyI = 3;
        emptyJ = 3;
        cards[emptyI][emptyJ].setVisibility(View.INVISIBLE);
        numbers = new ArrayList<>();
        loadData();
        loadDataToView();
    }

    private void onButtonClickButton(View view) {
        TextView textView = (TextView) view;
        String hint = textView.getHint().toString();
        int i = Integer.parseInt(hint.split(":")[0]);
        int j = Integer.parseInt(hint.split(":")[1]);
        int deltaI = Math.abs(i - emptyI);
        int deltaJ = Math.abs(j - emptyJ);

        if (deltaI + deltaJ == 1) {
            buttons[emptyI][emptyJ].setText(textView.getText());
            cards[emptyI][emptyJ].setVisibility(View.VISIBLE);

            buttons[i][j].setText("0");
            cards[i][j].setVisibility(View.INVISIBLE);

            emptyI = i;
            emptyJ = j;

            stepView.setText("Step: " + (++countStep));
        }

        if (isWin()) {
            Toast.makeText(this, "You win!!!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, ResultActivity.class);
            ResultCache.INSTANCE.saveNextResult(new ResultDto(timeCount, countStep), GameMode.MODE_FOUR);
            Bundle bundle = new Bundle();
            bundle.putInt("STEP", countStep);
            bundle.putInt("TIME", timeCount);
            bundle.putInt("GAME_MODE", 3);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }

    }


    private void countTime() {

        if (!buttonsIsHidden)
            timeCount++;

        int second = timeCount % 60;
        int minute = timeCount / 60 % 60;
        int hour = timeCount / 60 / 60 % 24;

        String timeFormat = String.format("%02d:%02d:%02d", hour, minute, second);
        timerView.setText("Time: " + timeFormat);
    }

    private boolean isWin() {
        for (int i = 0; i < 15; i++) {
            CardView cardView = (CardView) buttonGroup.getChildAt(i);
            TextView textView = (TextView) cardView.getChildAt(0);
            if (textView.getText() != String.valueOf(i + 1))
                return false;
        }
        return true;
    }

    private void loadThemeModeData() {
        ThemeMode lastThemeMode = ResultCache.INSTANCE.getLastThemeMode();

        if (lastThemeMode == ThemeMode.DAY) {
                binding.getRoot().setBackgroundColor(getColor(R.color.background_day));

        }else{
            binding.getRoot().setBackgroundColor(getColor(R.color.background_night));
        }
    }

}
