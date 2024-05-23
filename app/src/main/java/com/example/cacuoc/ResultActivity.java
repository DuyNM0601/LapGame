package com.example.cacuoc;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private TextView tvResult;
    private TextView tvResultStatus;
    private TextView tvResultHorse, tvResultMoneyLeft;
    private Button btnReset;
    private int money;
    private int betAmount1, betAmount2, betAmount3, betAmount4;
    private boolean isHorse1Checked, isHorse2Checked, isHorse3Checked, isHorse4Checked;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvResult = findViewById(R.id.tvResult);
        tvResultStatus = findViewById(R.id.textViewStatus);
        tvResultHorse = findViewById(R.id.tvResultHorse);
        tvResultMoneyLeft = findViewById(R.id.tvResultMoneyLeft);
        btnReset = findViewById(R.id.buttonReset);
        Intent intent = getIntent();
        ArrayList<Integer> winningHorses = intent.getIntegerArrayListExtra("winningHorses");
        ArrayList<Integer> losingHorses = intent.getIntegerArrayListExtra("losingHorses");
        money = intent.getIntExtra("money", 1000);
        betAmount1 = intent.getIntExtra("betAmountHorse1", 0);
        betAmount2 = intent.getIntExtra("betAmountHorse2", 0);
        betAmount3 = intent.getIntExtra("betAmountHorse3", 0);
        betAmount4 = intent.getIntExtra("betAmountHorse4", 0);
        isHorse1Checked = intent.getBooleanExtra("isCheckedHorse1", false);
        isHorse2Checked = intent.getBooleanExtra("isCheckedHorse2", false);
        isHorse3Checked = intent.getBooleanExtra("isCheckedHorse3", false);
        isHorse4Checked = intent.getBooleanExtra("isCheckedHorse4", false);

        calculateResult(winningHorses, losingHorses);
    }

    private void calculateResult(ArrayList<Integer> winningHorses, ArrayList<Integer> losingHorses) {
        int totalWinAmount = 0;
        int totalLossAmount = 0;

        // Calculate winnings
        if (isHorse1Checked && winningHorses.contains(0)) {
            totalWinAmount += betAmount1;
        }
        if (isHorse2Checked && winningHorses.contains(1)) {
            totalWinAmount += betAmount2;
        }
        if (isHorse3Checked && winningHorses.contains(2)) {
            totalWinAmount += betAmount3;
        }
        if (isHorse4Checked && winningHorses.contains(3)) {
            totalWinAmount += betAmount4;
        }

        // Calculate losses
        if (isHorse1Checked && losingHorses.contains(0)) {
            totalLossAmount += betAmount1;
        }
        if (isHorse2Checked && losingHorses.contains(1)) {
            totalLossAmount += betAmount2;
        }
        if (isHorse3Checked && losingHorses.contains(2)) {
            totalLossAmount += betAmount3;
        }
        if (isHorse4Checked && losingHorses.contains(3)) {
            totalLossAmount += betAmount4;
        }

        // Update money
        money = money + totalWinAmount - totalLossAmount;
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, BetActivity.class);
                intent.putExtra("moneyAfter", money);
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                startActivity(intent);
            }
        });
        // Display result
        if (totalWinAmount > 0) {
            tvResultStatus.setText("You Win!");
            tvResultStatus.setTextColor(Color.GREEN);
            tvResultHorse.setText("Ngựa số " + (winningHorses.get(0) + 1) + " bạn chọn đã thắng");
            tvResultMoneyLeft.setText("Số tiền thắng cược là: " + totalWinAmount);
            tvResult.setText("Số tiền còn lại của bạn: " + money);
            mediaPlayer = MediaPlayer.create(ResultActivity.this, R.raw.success);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        } else {
            tvResultStatus.setText("You Lose!");
            tvResultStatus.setTextColor(Color.RED);
            tvResultHorse.setText("Ngựa bạn chọn đã thua");
            tvResultMoneyLeft.setText("Số tiền thua cược là: " + totalLossAmount);
            tvResult.setText("Số tiền còn lại của bạn: " + money);
            mediaPlayer = MediaPlayer.create(ResultActivity.this, R.raw.lose);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }
}