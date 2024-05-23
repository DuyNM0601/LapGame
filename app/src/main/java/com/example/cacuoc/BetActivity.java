package com.example.cacuoc;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BetActivity extends AppCompatActivity {

    private EditText etBetHorse1, etBetHorse2, etBetHorse3, etBetHorse4;
    private CheckBox cbHorse1, cbHorse2, cbHorse3, cbHorse4;
    private Button btnStartGame;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bet);

        mediaPlayer = MediaPlayer.create(this, R.raw.cacuocsound);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        etBetHorse1 = findViewById(R.id.editTextText);
        etBetHorse2 = findViewById(R.id.editTextText2);
        etBetHorse3 = findViewById(R.id.editTextText3);
        etBetHorse4 = findViewById(R.id.editTextText4);
        cbHorse1 = findViewById(R.id.checkBox);
        cbHorse2 = findViewById(R.id.checkBox2);
        cbHorse3 = findViewById(R.id.checkBox3);
        cbHorse4 = findViewById(R.id.checkBox4);
        btnStartGame = findViewById(R.id.buttonStartGame);

        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int betAmountHorse1 = getBetAmount(etBetHorse1);
                int betAmountHorse2 = getBetAmount(etBetHorse2);
                int betAmountHorse3 = getBetAmount(etBetHorse3);
                int betAmountHorse4 = getBetAmount(etBetHorse4);
                boolean isCheckedHorse1 = cbHorse1.isChecked();
                boolean isCheckedHorse2 = cbHorse2.isChecked();
                boolean isCheckedHorse3 = cbHorse3.isChecked();
                boolean isCheckedHorse4 = cbHorse4.isChecked();

                Intent intent = new Intent(BetActivity.this, MainActivity.class);
                intent.putExtra("betAmountHorse1", betAmountHorse1);
                intent.putExtra("betAmountHorse2", betAmountHorse2);
                intent.putExtra("betAmountHorse3", betAmountHorse3);
                intent.putExtra("betAmountHorse4", betAmountHorse4);
                intent.putExtra("isCheckedHorse1", isCheckedHorse1);
                intent.putExtra("isCheckedHorse2", isCheckedHorse2);
                intent.putExtra("isCheckedHorse3", isCheckedHorse3);
                intent.putExtra("isCheckedHorse4", isCheckedHorse4);
                startActivity(intent);
            }
        });

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }

    }

    private int getBetAmount(EditText editText) {
        String text = editText.getText().toString();
        if (text.isEmpty()) {
            return 0;
        } else {
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }
}

