package com.example.cacuoc;

import android.content.Intent;
import android.os.Bundle;
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
    private int money;
    private int betAmount1, betAmount2, betAmount3, betAmount4;
    private boolean isHorse1Checked, isHorse2Checked, isHorse3Checked, isHorse4Checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvResult = findViewById(R.id.tvResult);

        Intent intent = getIntent();
        ArrayList<Integer> winningHorses = intent.getIntegerArrayListExtra("winningHorses");
        money = intent.getIntExtra("money", 1000);
        betAmount1 = intent.getIntExtra("betAmountHorse1", 0);
        betAmount2 = intent.getIntExtra("betAmountHorse2", 0);
        betAmount3 = intent.getIntExtra("betAmountHorse3", 0);
        betAmount4 = intent.getIntExtra("betAmountHorse4", 0);
        isHorse1Checked = intent.getBooleanExtra("isCheckedHorse1", false);
        isHorse2Checked = intent.getBooleanExtra("isCheckedHorse2", false);
        isHorse3Checked = intent.getBooleanExtra("isCheckedHorse3", false);
        isHorse4Checked = intent.getBooleanExtra("isCheckedHorse4", false);

        calculateResult(winningHorses);
    }

    private void calculateResult(ArrayList<Integer> winningHorses) {
        boolean won = false;

        if (isHorse1Checked && winningHorses.contains(0)) {
            money += betAmount1 * 2;
            won = true;
        }
        if (isHorse2Checked && winningHorses.contains(1)) {
            money += betAmount2 * 2;
            won = true;
        }
        if (isHorse3Checked && winningHorses.contains(2)) {
            money += betAmount3 * 2;
            won = true;
        }
        if (isHorse4Checked && winningHorses.contains(3)) {
            money += betAmount4 * 2;
            won = true;
        }

        if (!won) {
            int totalBetAmount = 0;
            if (isHorse1Checked) {
                totalBetAmount += betAmount1;
            }
            if (isHorse2Checked) {
                totalBetAmount += betAmount2;
            }
            if (isHorse3Checked) {
                totalBetAmount += betAmount3;
            }
            if (isHorse4Checked) {
                totalBetAmount += betAmount4;
            }
            money -= totalBetAmount;
        }

        if (won) {
            tvResult.setText("You Won! Your remaining money: " + money);
        } else {
            tvResult.setText("You Lost! Your remaining money: " + money);
        }
    }
}