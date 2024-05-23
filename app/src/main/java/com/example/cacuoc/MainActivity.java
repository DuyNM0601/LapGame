package com.example.cacuoc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView imgHorse1, imgHorse2, imgHorse3, imgHorse4, imgBackground;
    private View finishLine;
    private int[] positions = {30, 0, 0, 0};
    private Handler handler = new Handler();
    private Random random = new Random();
    private float finishX;
    private int maxStep = 10;
    private int animationDuration = 2000;
    private boolean raceFinished;
    private int money;
    private int betAmount1, betAmount2, betAmount3, betAmount4;
    private boolean isHorse1Checked, isHorse2Checked, isHorse3Checked, isHorse4Checked;
    private int fixedDistance = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgHorse1 = findViewById(R.id.imgHorse1);
        imgHorse2 = findViewById(R.id.imgHorse2);
        imgHorse3 = findViewById(R.id.imgHorse3);
        imgHorse4 = findViewById(R.id.imgHorse4);
        imgBackground = findViewById(R.id.imgBackground);
        finishLine = findViewById(R.id.finishLine);

        Intent intent = getIntent();
        betAmount1 = intent.getIntExtra("betAmountHorse1", 0);
        betAmount2 = intent.getIntExtra("betAmountHorse2", 0);
        betAmount3 = intent.getIntExtra("betAmountHorse3", 0);
        betAmount4 = intent.getIntExtra("betAmountHorse4", 0);
        money = intent.getIntExtra("money", 1000);
        isHorse1Checked = intent.getBooleanExtra("isCheckedHorse1", false);
        isHorse2Checked = intent.getBooleanExtra("isCheckedHorse2", false);
        isHorse3Checked = intent.getBooleanExtra("isCheckedHorse3", false);
        isHorse4Checked = intent.getBooleanExtra("isCheckedHorse4", false);

        finishLine.post(new Runnable() {
            @Override
            public void run() {
                finishX = finishLine.getLeft() + finishLine.getWidth();
            }
        });

        Glide.with(this).asGif().load(R.drawable.horsebgr).into(imgBackground);

        startRace();
    }

    private void startRace() {
        raceFinished = false;
        saveBetInfo(); // Lưu thông tin cược và con ngựa khi bắt đầu cuộc đua
        handler.post(raceRunnable);
    }

    private void saveBetInfo() {
        Intent intent = getIntent();
        intent.putExtra("betAmountHorse1", betAmount1);
        intent.putExtra("betAmountHorse2", betAmount2);
        intent.putExtra("betAmountHorse3", betAmount3);
        intent.putExtra("betAmountHorse4", betAmount4);
        intent.putExtra("money", money);
        intent.putExtra("isCheckedHorse1", isHorse1Checked);
        intent.putExtra("isCheckedHorse2", isHorse2Checked);
        intent.putExtra("isCheckedHorse3", isHorse3Checked);
        intent.putExtra("isCheckedHorse4", isHorse4Checked);
    }

    private Runnable raceRunnable = new Runnable() {
        @Override
        public void run() {
            if (!raceFinished) {
                List<Integer> winningHorses = new ArrayList<>();

                for (int i = 0; i < positions.length; i++) {
                    if (positions[i] < fixedDistance) {
                        int step = Math.abs(random.nextInt(maxStep));
                        positions[i] += step;

                        if (positions[i] >= fixedDistance && !winningHorses.contains(i)) {
                            //fixedDistance -= maxStep;
                            raceFinished = true;
                            winningHorses.add(i); // Thêm chỉ số của ngựa chiến thắng vào danh sách
                            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                            intent.putIntegerArrayListExtra("winningHorses", (ArrayList<Integer>) winningHorses);
                            intent.putExtra("money", money);
                            intent.putExtra("betAmountHorse1", betAmount1);
                            intent.putExtra("betAmountHorse2", betAmount2);
                            intent.putExtra("betAmountHorse3", betAmount3);
                            intent.putExtra("betAmountHorse4", betAmount4);
                            intent.putExtra("isCheckedHorse1", isHorse1Checked);
                            intent.putExtra("isCheckedHorse2", isHorse2Checked);
                            intent.putExtra("isCheckedHorse3", isHorse3Checked);
                            intent.putExtra("isCheckedHorse4", isHorse4Checked);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                }

                runOnUiThread(() -> {
                    for (int i = 0; i < positions.length; i++) {
                        updateUI(i);

                        float horseX = 0;
                        if (i == 0) {
                            horseX = imgHorse1.getX() + (positions[i] * (finishX / 100.0f));
                        } else if (i == 1) {
                            horseX = imgHorse2.getX() + (positions[i] * (finishX / 100.0f));
                        } else if (i == 2) {
                            horseX = imgHorse3.getX() + (positions[i] * (finishX / 100.0f));
                        } else if (i == 3) {
                            horseX = imgHorse4.getX() + (positions[i] * (finishX / 100.0f));
                        }

                        if (horseX >= finishX && !winningHorses.contains(i)) {
                            winningHorses.add(i);
                        }
                    }

                    boolean allHorsesFinished = true;
                    for (int position : positions) {
                        if (position < fixedDistance) {
                            allHorsesFinished = false;
                            break;
                        }
                    }

                    if (!allHorsesFinished) {
                        handler.postDelayed(this, animationDuration);
                    }
                });
            }
        }
    };


    private void showResult(int winningHorse) {
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("winningHorse", winningHorse);
        intent.putExtra("money", money);
        intent.putExtra("betAmountHorse1", betAmount1);
        intent.putExtra("betAmountHorse2", betAmount2);
        intent.putExtra("betAmountHorse3", betAmount3);
        intent.putExtra("betAmountHorse4", betAmount4);
        intent.putExtra("isCheckedHorse1", isHorse1Checked);
        intent.putExtra("isCheckedHorse2", isHorse2Checked);
        intent.putExtra("isCheckedHorse3", isHorse3Checked);
        intent.putExtra("isCheckedHorse4", isHorse4Checked);
        startActivity(intent);
        finish();
    }

    private void updateUI(int horseIndex) {
        if (horseIndex == 0) {
            animateHorse(imgHorse1, positions[0], R.drawable.horsevip);
        } else if (horseIndex == 1) {
            animateHorse(imgHorse2, positions[1], R.drawable.horsevip);
        } else if (horseIndex == 2) {
            animateHorse(imgHorse3, positions[2], R.drawable.horsevip);
        } else if (horseIndex == 3) {
            animateHorse(imgHorse4, positions[3], R.drawable.horsevip);
        }
    }

    private void animateHorse(final ImageView horse, int position, int horseDrawable) {
        final float newX = (position / 100f) * finishX;
        horse.animate().x(newX).setDuration(animationDuration).start();
        Glide.with(this).asGif().load(horseDrawable).into(horse);
    }
}
