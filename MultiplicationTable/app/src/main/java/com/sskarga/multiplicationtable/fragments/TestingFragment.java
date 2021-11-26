package com.sskarga.multiplicationtable.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;

import com.sskarga.multiplicationtable.BaseFragment;
import com.sskarga.multiplicationtable.R;
import com.sskarga.multiplicationtable.databinding.FragmentTestingBinding;
import com.sskarga.multiplicationtable.dto.OptionResult;
import com.sskarga.multiplicationtable.interfaces.HasCustomTitle;
import com.sskarga.multiplicationtable.questions.DivisionQuestion;
import com.sskarga.multiplicationtable.questions.MultiplicationQuestion;
import com.sskarga.multiplicationtable.questions.Question;

import java.util.ArrayList;

public class TestingFragment extends BaseFragment implements HasCustomTitle {

    FragmentTestingBinding binding;
    ArrayList<Button> arrBtnAns;
    TimerHandler timerHandler;
    ValueAnimator colorAnim;

    final long TIMER_DURATION = 60;

    Question question;
    int questionType = 0;
    int numberBtnAns = 0;

    final String KEY_COUNT_QUESTION = "COUNT_QUESTION";
    int countQuestion = 0;

    final String KEY_COUNT_RIGHT_ANS = "COUNT_RIGHT_ANS";
    int countRightAns = 0;


    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    private void setRandomAns(ArrayList<Button> btns, int ans) {
        ArrayList<Integer> arrFakeAns = new ArrayList<>();

        for (int i = 0; i < btns.size(); i++) {
            int min = Math.max((ans - 5), 0);
            int max = Math.min((ans + 5), 100);

            int fakeAns = getRandomNumber(min, max);
            while ((ans == fakeAns) || (arrFakeAns.contains(fakeAns))) {
                fakeAns = getRandomNumber(min, max);
            }

            arrFakeAns.add(fakeAns);
            btns.get(i).setText(String.valueOf(fakeAns));
        }
    }


    private void getNextQuestion() {
        if (questionType == 0) {
            question = new MultiplicationQuestion().next();
            questionType = 1;
        } else {
            question = new DivisionQuestion().next();
            questionType = 0;
        }

        binding.textViewTask.setText(question.questionStr);

        setRandomAns(arrBtnAns, question.ans);

        numberBtnAns = getRandomNumber(0, arrBtnAns.size() - 1);
        arrBtnAns.get(numberBtnAns).setText(String.valueOf(question.ans));
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timerHandler = new TimerHandler(TIMER_DURATION, savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTestingBinding.inflate(getLayoutInflater(), container, false);

        if (savedInstanceState != null) {
            countRightAns = savedInstanceState.getInt(KEY_COUNT_RIGHT_ANS, 0);
            countQuestion = savedInstanceState.getInt(KEY_COUNT_QUESTION, 0);
        }
        updateUiScore();

        arrBtnAns = new ArrayList<>();
        arrBtnAns.add(binding.btnAns1);
        arrBtnAns.add(binding.btnAns2);
        arrBtnAns.add(binding.btnAns3);
        arrBtnAns.add(binding.btnAns4);

        for (int i = 0; i < arrBtnAns.size(); i++) {
            arrBtnAns.get(i).setTag(i);
            arrBtnAns.get(i).setOnClickListener(getOnClickListener());
        }

        questionType = 0;
        getNextQuestion();

        return binding.getRoot();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_COUNT_QUESTION, countQuestion);
        outState.putInt(KEY_COUNT_RIGHT_ANS, countRightAns);
        timerHandler.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        timerHandler.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        timerHandler.onStop();
    }

    // UI
    private void updateUiTimer() {
        binding.textViewTimeLeft.setText(
                String.format(
                        getString(R.string.textTimeLeft), timerHandler.getRemainingSeconds()
                ));
    }


    private void updateUiScore() {
        binding.textViewScores.setText(
                String.format(
                        getString(R.string.textScores), countRightAns, countQuestion)
        );
    }


    private void animColor(View view) {
        colorAnim = ObjectAnimator.ofInt(
                view,
                "backgroundColor",
                ContextCompat.getColor(getContext(), R.color.purple_500),
                ContextCompat.getColor(getContext(), R.color.green));
        colorAnim.setDuration(300);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(3);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);

        colorAnim.addListener(animListener());

        colorAnim.start();
    }


    // Listener
    @NonNull
    private View.OnClickListener getOnClickListener() {
        return view -> {
            Button button = (Button) view;
            Integer answer = Integer.parseInt(button.getTag().toString());

            animColor(arrBtnAns.get(numberBtnAns));

            if (answer.equals(numberBtnAns)) {
                countRightAns++;
            }
            countQuestion++;
            updateUiScore();
//            getNextQuestion();
        };
    }

    @NonNull
    private Animator.AnimatorListener animListener() {
        return new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                colorAnim.removeAllListeners();
                getNextQuestion();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        };
    }


    @Override
    public int getTitleRes() {
        return R.string.titleTesting;
    }


    class TimerHandler {
        private final String KEY_START_TIMESTAMP = "START_TIMESTAMP";
        private CountDownTimer timer;
        private final long timerDuration;
        private final long timerStartTimestamp;

        public TimerHandler(long timerDuration, @Nullable Bundle savedInstanceState) {
            this.timerDuration = timerDuration * 1000L;

            if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_START_TIMESTAMP)) {
                timerStartTimestamp = savedInstanceState.getLong(KEY_START_TIMESTAMP);
            } else {
                this.timerStartTimestamp = System.currentTimeMillis();
            }
        }

        public long getRemainingSeconds() {
            long finishedAt = timerStartTimestamp + timerDuration;
            return Math.max(0, (finishedAt - System.currentTimeMillis()) / 1000);
        }

        public void onSaveInstanceState(Bundle outState) {
            outState.putLong(KEY_START_TIMESTAMP, timerStartTimestamp);
        }

        public void onStart() {
            timer = new CountDownTimer(getRemainingSeconds() * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    updateUiTimer();
                }

                @Override
                public void onFinish() {
                    updateUiTimer();
                    getNavigator().showResultScreen(new OptionResult(countQuestion, countRightAns));
                }
            };
            updateUiTimer();
            timer.start();
        }

        public void onStop() {
            timer.cancel();
        }
    }

}