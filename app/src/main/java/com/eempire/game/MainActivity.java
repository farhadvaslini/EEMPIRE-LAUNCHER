package com.eempire.game;

import android.app.Activity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {

    SharedPreferences prefs;
    LinearLayout root;

    int WHITE = Color.WHITE;
    int CYAN = Color.rgb(80, 220, 255);
    int BLUE = Color.rgb(40, 120, 255);
    int DARK = Color.rgb(10, 10, 24);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        prefs = getSharedPreferences("empire_accounts", MODE_PRIVATE);

        showLogin();
    }

    GradientDrawable panelBackground() {
        GradientDrawable g = new GradientDrawable();
        g.setColor(Color.argb(205, 15, 20, 45));
        g.setCornerRadius(35);
        g.setStroke(2, Color.argb(180, 80, 220, 255));
        return g;
    }

    TextView text(String value, float size) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextColor(WHITE);
        t.setTextSize(size);
        t.setGravity(Gravity.CENTER);
        t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        t.setPadding(15, 15, 15, 15);
        return t;
    }

    EditText input(String hint) {
        EditText e = new EditText(this);
        e.setHint(hint);
        e.setHintTextColor(Color.LTGRAY);
        e.setTextColor(WHITE);
        e.setTextSize(16);
        e.setSingleLine(true);
        e.setPadding(25, 5, 25, 5);

        GradientDrawable bg = new GradientDrawable();
        bg.setColor(Color.argb(100, 255, 255, 255));
        bg.setCornerRadius(25);
        bg.setStroke(1, Color.argb(150, 80, 220, 255));
        e.setBackground(bg);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(-1, 58);
        p.setMargins(0, 7, 0, 7);
        e.setLayoutParams(p);

        return e;
    }

    Button button(String value) {
        Button b = new Button(this);
        b.setText(value);
        b.setTextColor(WHITE);
        b.setTextSize(15);
        b.setAllCaps(false);

        GradientDrawable bg = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{Color.rgb(25, 90, 180), Color.rgb(100, 30, 180)}
        );
        bg.setCornerRadius(28);
        b.setBackground(bg);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(-1, 58);
        p.setMargins(0, 8, 0, 8);
        b.setLayoutParams(p);

        return b;
    }

    LinearLayout base() {
        FrameLayout frame = new FrameLayout(this);

        ImageView background = new ImageView(this);
        background.setImageResource(R.drawable.menu_bg);
        background.setScaleType(ImageView.ScaleType.CENTER_CROP);
        frame.addView(background,
                new FrameLayout.LayoutParams(-1, -1));

        ScrollView scroll = new ScrollView(this);

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding(35, 35, 35, 35);

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(-1, -2);
        cardParams.gravity = Gravity.CENTER;

        root.setBackground(panelBackground());

        scroll.addView(root, cardParams);

        FrameLayout.LayoutParams scrollParams =
                new FrameLayout.LayoutParams(-1, -2);
        scrollParams.gravity = Gravity.CENTER;
        scrollParams.setMargins(25, 40, 25, 40);

        frame.addView(scroll, scrollParams);

        setContentView(frame);

        return root;
    }

    void showLogin() {

        root = base();

        root.addView(text("💎 EMPIRE GAME", 30));
        root.addView(text("ورود به سرور", 19));

        EditText nickname = input("Nickname");
        EditText password = input("Password");
        password.setInputType(
                InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD
        );

        root.addView(nickname);
        root.addView(password);

        Button login = button("🔐 ورود");
        Button register = button("📝 ثبت‌نام");

        root.addView(login);
        root.addView(register);

        login.setOnClickListener(v -> {

            String name = nickname.getText().toString().trim();
            String pass = password.getText().toString();

            if (name.isEmpty() || pass.isEmpty()) {
                toast("Nickname و Password را وارد کن.");
                return;
            }

            String savedName = prefs.getString("nickname", "");
            String savedPass = prefs.getString("password", "");

            if (name.equals(savedName) && pass.equals(savedPass)) {
                showWelcome(name);
            } else {
                toast("اطلاعات ورود صحیح نیست.");
            }
        });

        register.setOnClickListener(v -> showRegister());
    }

    void showRegister() {

        root = base();

        root.addView(text("💎 EMPIRE GAME", 30));
        root.addView(text("ساخت حساب جدید", 19));

        EditText nickname = input("Nickname");
        EditText password = input("Password");
        EditText confirm = input("Confirm Password");
        EditText email = input("Email *");
        EditText phone = input("Phone Number *");
        EditText referral = input("Referral Code (اختیاری)");

        password.setInputType(0x81);
        confirm.setInputType(0x81);

        root.addView(nickname);
        root.addView(password);
        root.addView(confirm);
        root.addView(email);
        root.addView(phone);
        root.addView(referral);

        Button register = button("✨ تکمیل ثبت‌نام");
        Button back = button("↩ بازگشت");

        root.addView(register);
        root.addView(back);

        register.setOnClickListener(v -> {

            String name = nickname.getText().toString().trim();
            String pass = password.getText().toString();
            String conf = confirm.getText().toString();
            String mail = email.getText().toString().trim();
            String mobile = phone.getText().toString().trim();
            String ref = referral.getText().toString().trim();

            if (name.isEmpty() ||
                    pass.isEmpty() ||
                    conf.isEmpty() ||
                    mail.isEmpty() ||
                    mobile.isEmpty()) {

                toast("تمام موارد ستاره‌دار اجباری هستند.");
                return;
            }

            if (!pass.equals(conf)) {
                toast("تکرار رمز عبور صحیح نیست.");
                return;
            }

            prefs.edit()
                    .putString("nickname", name)
                    .putString("password", pass)
                    .putString("email", mail)
                    .putString("phone", mobile)
                    .putString("referral", ref)
                    .apply();

            showWelcome(name);
        });

        back.setOnClickListener(v -> showLogin());
    }

    void showWelcome(String name) {

        root = base();

        root.addView(text("💎 خوش آمدی", 30));
        root.addView(text(name, 25));

        TextView info = text(
                "حساب شما با موفقیت وارد شد.\n\n" +
                "اکنون می‌توانید وارد EMPIRE GAME شوید.",
                17
        );

        root.addView(info);

        Button enter = button("🎮 ورود به سرور");
        Button logout = button("🚪 خروج از حساب");

        root.addView(enter);
        root.addView(logout);

        enter.setOnClickListener(v ->
                toast("اتصال به سرور در مرحله بعد اضافه می‌شود.")
        );

        logout.setOnClickListener(v -> showLogin());
    }

    void toast(String message) {
        Toast.makeText(
                this,
                message,
                Toast.LENGTH_LONG
        ).show();
    }
}
