    package com.mir.diyalizstok.Activity;
    import android.content.ContentValues;
    import android.database.sqlite.SQLiteDatabase;
    import android.os.Bundle;
    import android.text.Editable;
    import android.text.TextUtils;
    import android.text.TextWatcher;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.appcompat.app.AppCompatActivity;

    import com.mir.diyalizstok.DiyalizVeritabani;
    import com.mir.diyalizstok.R;

    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.Calendar;
    import java.util.Date;
    import java.util.Locale;

    public class seansKayit extends AppCompatActivity {
        private TextView txtAd, txtSoyad, txtTc, txtId, txtDeneme;
        Button btnKayit;
        EditText editTarih, editSaat;
        private String hastaAdi, hastaSoyadi, hastaTc, hastaId;

        private DiyalizVeritabani diyalizVeritabani;

        private void init() {
            txtAd = (TextView) findViewById(R.id.txtAdSeansKayit);
            txtSoyad = (TextView) findViewById(R.id.txtSoyadSeansKayit);
            txtTc = (TextView) findViewById(R.id.txtTcSeansKayit);
            txtId = (TextView) findViewById(R.id.txtIdSeansKayit);
            editTarih = (EditText) findViewById(R.id.editStarih);
            editSaat = (EditText) findViewById(R.id.editSsaat);
            btnKayit = (Button) findViewById(R.id.btnKayit);
            txtDeneme = (TextView) findViewById(R.id.txtDeneme);


            hastaAdi = listeArayuz.seansAktarim.getHastaAdi();
            hastaSoyadi = listeArayuz.seansAktarim.getHastaSoyadi();
            hastaTc = listeArayuz.seansAktarim.getHastaTc();
            hastaId = listeArayuz.seansAktarim.getHastaId().toString();
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.hasta_seans);
            diyalizVeritabani = new DiyalizVeritabani(this);
            init();
            if (!TextUtils.isEmpty(hastaAdi) && !TextUtils.isEmpty(hastaSoyadi) && !TextUtils.isEmpty(hastaTc) && !TextUtils.isEmpty(hastaId)) {
                txtAd.setText(hastaAdi);
                txtSoyad.setText(hastaSoyadi);
                txtTc.setText(hastaTc);
                txtId.setText(hastaId);
            }

            btnKayit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (!TextUtils.isEmpty(editTarih.getText().toString())) {
                            if (!TextUtils.isEmpty(editSaat.getText().toString())) {
                                checkEnteredDate();
                            } else showToast("Saat alanı boş geçilemez");
                        } else showToast("Tarih alanı boş geçilemez");
                    } finally {
                        diyalizVeritabani.close();
                    }
                }
            });

            editTarih.addTextChangedListener(new TextWatcher(){
                private String current = "";
                private String ddmmyyyy = "DDMMYYYY";
                private Calendar cal = Calendar.getInstance();

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                    {
                        if (!s.toString().equals(current)) {
                            String clean = s.toString().replaceAll("[^\\d.]", "");
                            String cleanC = current.replaceAll("[^\\d.]", "");

                            int cl = clean.length();
                            int sel = cl;
                            for (int z = 2; z <= cl && z < 6; z += 2) {
                                sel++;
                            }
                            //Fix for pressing delete next to a forward slash
                            if (clean.equals(cleanC)) sel--;

                            if (clean.length() < 8) {
                                clean = clean + ddmmyyyy.substring(clean.length());
                            } else {
                                //This part makes sure that when we finish entering numbers
                                //the date is correct, fixing it otherwise
                                int day = Integer.parseInt(clean.substring(0, 2));
                                int mon = Integer.parseInt(clean.substring(2, 4));
                                int year = Integer.parseInt(clean.substring(4, 8));

                                if (mon > 12) mon = 12;
                                cal.set(Calendar.MONTH, mon - 1);

                                year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                                cal.set(Calendar.YEAR, year);
                                // ^ first set year for the line below to work correctly
                                //with leap years - otherwise, date e.g. 29/02/2012
                                //would be automatically corrected to 28/02/2012

                                day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                                clean = String.format("%02d%02d%02d", day, mon, year);
                            }

                            clean = String.format("%s-%s-%s", clean.substring(0, 2),
                                    clean.substring(2, 4),
                                    clean.substring(4, 8));

                            sel = sel < 0 ? 0 : sel;
                            current = clean;
                            editTarih.setText(current);
                            editTarih.setSelection(sel < current.length() ? sel : current.length());
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


        }

        private void showToast(String mesaj) {
            Toast.makeText(getApplicationContext(), mesaj, Toast.LENGTH_SHORT).show();
        }
        private void seansEkle(Integer hastaId, String seansTarih, String seansSaat, String durum) {

            SQLiteDatabase db = diyalizVeritabani.getWritableDatabase();
            ContentValues datas = new ContentValues();
            datas.put("HASTAID", hastaId);
            datas.put("STARIH", seansTarih);
            datas.put("SSAAT", seansSaat);
            datas.put("DURUM", durum);
            db.insertOrThrow("SEANS", null, datas);

        }

        private void checkEnteredDate() {
            String userEnteredDate = editTarih.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyyy", Locale.getDefault());
            Date currentDate = Calendar.getInstance().getTime();
            try {
                Date enteredDate = sdf.parse(userEnteredDate);
                if (enteredDate != null && enteredDate.before(currentDate)) {
                    Toast.makeText(this, "Girdiğiniz tarih bugünden itibaren olmalıdır,geçmişe bir tarih veremezsiniz!", Toast.LENGTH_SHORT).show();
                } else {
                    int durum = 0;
                    seansEkle(Integer.valueOf(txtId.getText().toString()), editTarih.getText().toString(), editSaat.getText().toString(), String.valueOf(durum));
                    showToast("Seans kaydı gerçekleşmiştir.");
                }
            } catch (ParseException e) {
                showToast("Lütfen tarih biçimini dd-MM-yyyy şeklinde giriniz!");
            }
        }

    }
