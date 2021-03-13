package com.example.caculator;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int STATE_OPERAND_FIRST = 1;
    private static final int STATE_OPERAND_SECOND = 2;
    private int state;
    private int operand1, operand2;
    private int operator;
    private TextView textResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textResult = findViewById(R.id.textResult);

        findViewById(R.id.button0).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);

        findViewById(R.id.buttonadd).setOnClickListener(this);
        findViewById(R.id.buttonsub).setOnClickListener(this);
        findViewById(R.id.buttonmul).setOnClickListener(this);
        findViewById(R.id.buttondiv).setOnClickListener(this);

        findViewById(R.id.buttonequal).setOnClickListener(this);

        findViewById(R.id.buttonBS).setOnClickListener(this);
        findViewById(R.id.buttonC).setOnClickListener(this);
        findViewById(R.id.buttonCE).setOnClickListener(this);

        state = STATE_OPERAND_FIRST;
        operand1 = operand2 = 0;
        operator = Operator.INIT;
        textResult.setText(String.valueOf(0));
    }
    }



    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            /**
             * Không để hard code như này nhé, cần để vào hằng số
             * Có thể tạo 1 class khác lưu các loại operators, và 1 class lưu các loại state
             */
            case R.id.buttonadd:
                selectOperator(Operator.ADD);
                break;
            case R.id.buttonsub:
                selectOperator(Operator.SUB);
                break;
            case R.id.buttonmul:
                selectOperator(Operator.MUL);
                break;
            case R.id.buttondiv:
                selectOperator(Operator.DIV);
                break;
            case R.id.buttonequal:
                calculateResult();
                break;
            case R.id.buttonBS:
                removeDigit();
                break;
            case R.id.buttonCE:
                clearCurrentOperand();
                break;
            case R.id.buttonC:
                state = STATE_OPERAND_FIRST;
                operand1 = operand2 = 0;
                operator = Operator.INIT;
                textResult.setText(String.valueOf(0));
                break;
            default:
                //Sử dụng parse Int thay vì valueOf để tránh tạo thêm các biến rác trong quá trình sinh byte code
                addDigit(Integer.parseInt(((Button)view).getText().toString()));
        }
    }

    private void selectOperator(int _op) {
        operator = _op;
        state = STATE_OPERAND_SECOND;
    }

    private void addDigit(int digit) {
        if (state == STATE_OPERAND_FIRST) {
            if (operand1 < 0)
                operand1 = operand1 * 10 - digit;
            else
                operand1 = operand1 * 10 + digit;
            textResult.setText(String.valueOf(operand1));
        }
        else {
            if (operand2 < 0)
                operand2 = operand2 * 10 - digit;
            else
                operand2 = operand2 * 10 + digit;
            textResult.setText(String.valueOf(operand2));
        }
    }

    private void calculateResult() {
        /**
         * Nếu các khối lệnh if else có 3 nhánh trở lên, dùng switch-case để khiến code dễ đọc hơn
         *          switch (operator){
         *             case Operator.ADD: result = operand1 + operand2; break;
         *         }
         */
        int result = 0;
        switch (operator) {
            case Operator.ADD:
                result = operand1 + operand2;
                break;
            case Operator.SUB:
                result = operand1 - operand2;
                break;
            case Operator.MUL:
                result = operand1 * operand2;
                break;
            case Operator.DIV:
                if (operand2 != 0)
                    result = operand1 / operand2;
                break;
        }

        /**
         * Những đoạn text hiển thị cho người dùng nhìn thấy thì nên đặt trong strings.xml để tiện cho việc chuyển đổi ngôn ngữ
         * String finalResult = (operator == 4 && operand2 == 0) ? getString(R.string.msg_error) : result.toString();
         */
        if (operator == Operator.DIV && operand2 == 0)
            textResult.setText(R.string.msg_error);
        else
            textResult.setText(String.valueOf(result));

        // Quay lai trang thai 1
        /**
         * Đặt tên rõ ràng state để khi code không cần phải comment mà vẫn hiểu được code
         * state = States.SOME_THING;
         */
        state = STATE_OPERAND_FIRST;
        operand1 = 0;
        operand2 = 0;
        operator = Operator.INIT;
    }

    private void removeDigit() {
        if (state == STATE_OPERAND_FIRST) {
            operand1 = operand1 / 10;
            textResult.setText(String.valueOf(operand1));
        }
        else {
            operand2 = operand2 / 10;
            textResult.setText(String.valueOf(operand2));
        }
    }

    private void clearCurrentOperand() {
        if (state == STATE_OPERAND_FIRST) {
            operand1 = 0;
            textResult.setText(String.valueOf(operand1));
        }
        else {
            operand2 = 0;
            textResult.setText(String.valueOf(operand2));
        }
    }
}