package com.example.easyeat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.example.easyeat.model.Order;
import com.example.easyeat.model.User;
import com.example.easyeat.payment.DBPayment;
import com.example.easyeat.payment.PaymentModel;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    public static final String GPAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    private CardForm cardForm;
    private AlertDialog.Builder alertBuilder;
    private ArrayList<Order> orderList;
    private User user;
    private String total;
    private DBPayment myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        Bundle bundleObject = getIntent().getExtras();
        orderList = (ArrayList<Order>) bundleObject.getSerializable("orderList");
        user = (User) bundleObject.getSerializable("user");
        total = (String) bundleObject.get("total");
        myDb = new DBPayment(this);

        cardForm = findViewById(R.id.card_form);
        Button buy = findViewById(R.id.btnBuy);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(PaymentActivity.this);
        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardForm.isValid()) {
                    alertBuilder = new AlertDialog.Builder(PaymentActivity.this);
                    alertBuilder.setTitle("Confirm before purchase");
                    alertBuilder.setMessage("Card number: " + cardForm.getCardNumber()
                            + "\n" +
                            "Card expiry date: " + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                            "Card CVV: " + cardForm.getCvv() + "\n" +
                            "Postal code: " + cardForm.getPostalCode() + "\n" +
                            "Phone number: " + cardForm.getMobileNumber()
                    );
                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            PaymentModel paymentModel = new PaymentModel(orderList.get(0).getItem().getCategoryName(), cardForm.getPostalCode(), user.getUsernname(), total, "");
                            boolean var = myDb.addPlacedOrder(paymentModel);
                            if (var) {
                                Toast.makeText(PaymentActivity.this, "Order Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), PostOrderPayment.class);
                                intent.putExtra("paymentModel", paymentModel);
                                startActivity(intent);
                            } else
                                Toast.makeText(PaymentActivity.this, "Failed to Add Order", Toast.LENGTH_SHORT).show();

                        }
                    });
                    alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();
                } else {
                    Toast.makeText(PaymentActivity.this, "Please complete the form", Toast.LENGTH_LONG).show();
                }
            }
        });





    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            Toast.makeText(PaymentActivity.this, "Transaction Successfull", Toast.LENGTH_LONG).show();
        }
    }


}