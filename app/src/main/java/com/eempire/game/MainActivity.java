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
    int GREEN = Color.rgb(60, 220, 120);
    int CYAN = Color.rgb(80, 220, 255);
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

        showLauncher();
    }

    GradientDrawable panelBackground() {
        GradientDrawable g = new GradientDrawable();
        g.setColor(Color.argb(215, 15, 20, 45));
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
        b.setTextColor(Color.WHITE);
        b.setTextSize(16);
        b.setAllCaps(false);
        b.setGravity(Gravity.CENTER);
        b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        b.setPadding(20, 0, 20, 0);

        GradientDrawable bg = new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{
                        Color.rgb(170, 20, 35),
                        Color.rgb(255, 75, 45)
                }
        );
        bg.setCornerRadius(32);
        bg.setStroke(2, Color.argb(210, 255, 150, 110));
        b.setBackground(bg);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(-1, 62);
        p.setMargins(0, 10, 0, 10);
        b.setLayoutParams(p);

        return b;
    }

    LinearLayout base() {
        FrameLayout frame = new FrameLayout(this);

        ImageView background = new ImageView(this);
        background.setImageResource(R.drawable.launcher_bg);
        background.setScaleType(ImageView.ScaleType.CENTER_CROP);
        frame.addView(background, new FrameLayout.LayoutParams(-1, -1));

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setClipToPadding(false);
        scroll.setOverScrollMode(View.OVER_SCROLL_NEVER);

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setPadding(24, 28, 24, 28);
        root.setBackground(panelBackground());

        scroll.addView(root, new ScrollView.LayoutParams(
                -1,
                -2
        ));

        FrameLayout.LayoutParams scrollParams =
                new FrameLayout.LayoutParams(
                        -1,
                        -1
                );

        scrollParams.setMargins(18, 18, 18, 18);
        frame.addView(scroll, scrollParams);

        setContentView(frame);
        return root;
    }

    void showLauncher() {
        FrameLayout frame = new FrameLayout(this);

        ImageView background = new ImageView(this);
        background.setImageResource(R.drawable.launcher_bg);
        background.setScaleType(ImageView.ScaleType.FIT_XY);
        frame.addView(background, new FrameLayout.LayoutParams(-1, -1));

        // ناحیه کلیک Nickname
        TextView nicknameArea = new TextView(this);
        nicknameArea.setBackgroundColor(Color.TRANSPARENT);
        nicknameArea.setOnClickListener(v -> showRegisterName());
        addClickArea(frame, nicknameArea, 0.68f, 0.12f, 0.30f, 0.12f);

        // ناحیه کلیک ورود به بازی
        TextView playArea = new TextView(this);
        playArea.setBackgroundColor(Color.TRANSPARENT);
        playArea.setOnClickListener(v -> showLogin());
        addClickArea(frame, playArea, 0.68f, 0.28f, 0.30f, 0.13f);

        // ناحیه کلیک خروج از حساب
        TextView logoutArea = new TextView(this);
        logoutArea.setBackgroundColor(Color.TRANSPARENT);
        logoutArea.setOnClickListener(v -> {
            getSharedPreferences("eempire", MODE_PRIVATE)
                    .edit()
                    .remove("nickname")
                    .remove("password")
                    .apply();

            toast("از حساب خارج شدید.");
        });
        addClickArea(frame, logoutArea, 0.68f, 0.43f, 0.30f, 0.12f);

        // ناحیه کلیک راهنما و پشتیبانی
        TextView supportArea = new TextView(this);
        supportArea.setBackgroundColor(Color.TRANSPARENT);
        supportArea.setOnClickListener(v ->
                toast("در روبیکا به آیدی @Farhad_vaseli پیام دهید")
        );
        addClickArea(frame, supportArea, 0.68f, 0.58f, 0.30f, 0.12f);

        setContentView(frame);
    }

    void addClickArea(FrameLayout frame, View view,
                      float x, float y, float width, float height) {

        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(0, 0);

        params.leftMargin = 0;
        params.topMargin = 0;

        frame.addView(view, params);

        view.post(() -> {
            int screenWidth = frame.getWidth();
            int screenHeight = frame.getHeight();

            int w = (int) (screenWidth * width);
            int h = (int) (screenHeight * height);
            int left = (int) (screenWidth * x);
            int top = (int) (screenHeight * y);

            FrameLayout.LayoutParams p =
                    new FrameLayout.LayoutParams(w, h);

            p.leftMargin = left;
            p.topMargin = top;

            view.setLayoutParams(p);
        });
    }

    void showLogin() {

        root = base();

        root.addView(text("💎 EEMPIRE GAME", 30));
        root.addView(text("ورود به حساب", 20));

        String savedName =
                prefs.getString("nickname", "");

        if (!savedName.isEmpty()) {

            root.addView(
                    text("Nickname: " + savedName, 18)
            );

            Button play = button("▶ Play");

            root.addView(play);

            play.setOnClickListener(v ->
                    showPassword(savedName)
            );

        } else {

            EditText nickname =
                    input("Nickname");

            root.addView(nickname);

            Button play =
                    button("▶ Play");

            Button register =
                    button("📝 ثبت‌نام");

            root.addView(play);
            root.addView(register);

            play.setOnClickListener(v -> {

                String name =
                        nickname.getText()
                                .toString()
                                .trim();

                if (name.isEmpty()) {
                    toast("Nickname را وارد کنید.");
                    return;
                }

                showPassword(name);
            });

            register.setOnClickListener(v ->
                    showRegisterName()
            );
        }
    }

    void showPassword(String name) {

        root = base();

        root.addView(text("💎 EEMPIRE GAME", 30));
        root.addView(text("ورود", 20));
        root.addView(text(name, 18));

        EditText password =
                input("Password");

        password.setInputType(
                InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD
        );

        root.addView(password);

        Button login =
                button("🔐 ورود");

        Button back =
                button("↩ بازگشت");

        root.addView(login);
        root.addView(back);

        login.setOnClickListener(v -> {

            String pass =
                    password.getText().toString();

            String savedPass =
                    prefs.getString("password", "");

            if (pass.isEmpty()) {
                toast("Password را وارد کنید.");
                return;
            }

            if (name.equals(
                    prefs.getString("nickname", "")
            ) && pass.equals(savedPass)) {

                showGameWelcome(name);

            } else {

                toast("Nickname یا Password صحیح نیست.");
            }
        });

        back.setOnClickListener(v ->
                showLogin()
        );
    }

    void showRegisterName() {

        root = base();

        root.addView(
                text("💎 EEMPIRE GAME", 30)
        );

        root.addView(
                text("ساخت حساب جدید", 20)
        );

        root.addView(
                text(
                        "نام کاربری باید بین 3 تا 20 کاراکتر باشد.\n" +
                        "از حروف انگلیسی، اعداد و کاراکترهای مجاز استفاده کنید.",
                        15
                )
        );

        EditText nickname =
                input("Nickname");

        root.addView(nickname);

        Button next =
                button("ادامه");

        Button back =
                button("↩ بازگشت");

        root.addView(next);
        root.addView(back);

        next.setOnClickListener(v -> {

            String name =
                    nickname.getText()
                            .toString()
                            .trim();

            if (name.length() < 3 ||
                    name.length() > 20) {

                toast(
                        "Nickname باید بین 3 تا 20 کاراکتر باشد."
                );

                return;
            }

            showRegisterPassword(name);
        });

        back.setOnClickListener(v ->
                showLogin()
        );
    }

    void showRegisterPassword(String name) {

        root = base();

        root.addView(text("ثبت‌نام", 22));

        EditText password =
                input("Password");

        EditText confirm =
                input("تکرار Password");

        password.setInputType(0x81);
        confirm.setInputType(0x81);

        root.addView(password);
        root.addView(confirm);

        Button next =
                button("ادامه");

        root.addView(next);

        next.setOnClickListener(v -> {

            String p =
                    password.getText().toString();

            String c =
                    confirm.getText().toString();

            if (p.isEmpty()) {
                toast("Password را وارد کنید.");
                return;
            }

            if (!p.equals(c)) {
                toast("تکرار Password صحیح نیست.");
                return;
            }

            showRules(name, p);
        });
    }

    void showRules(
            String name,
            String password
    ) {

        root = base();

        root.addView(
                text("شرایط و قوانین EEMPIRE GAME", 21)
        );

        root.addView(
                text(
                        "برای ادامه ثبت‌نام باید شرایط و قوانین " +
                        "EEMPIRE GAME را مطالعه و قبول کنید.",
                        16
                )
        );

        Button rules =
                button("📖 خواندن شرایط و قوانین");

        Button accept =
                button("✅ قبول قوانین");

        root.addView(rules);
        root.addView(accept);

        rules.setOnClickListener(v ->
                toast(
                        "شرایط و قوانین در فروم EEMPIRE GAME منتشر خواهد شد."
                )
        );

        accept.setOnClickListener(v ->
                showPhone(name, password)
        );
    }

    void showPhone(
            String name,
            String password
    ) {

        root = base();

        root.addView(
                text("تأیید شماره موبایل", 21)
        );

        EditText phone =
                input("شماره موبایل");

        root.addView(phone);

        Button next =
                button("ارسال کد تأیید");

        root.addView(next);

        next.setOnClickListener(v -> {

            String mobile =
                    phone.getText()
                            .toString()
                            .trim();

            if (mobile.isEmpty()) {
                toast("شماره موبایل را وارد کنید.");
                return;
            }

            showVerify(
                    name,
                    password,
                    mobile
            );
        });
    }

    void showVerify(
            String name,
            String password,
            String phone
    ) {

        root = base();

        root.addView(
                text("کد تأیید", 22)
        );

        root.addView(
                text(
                        "کد تأیید به شماره واردشده ارسال شد.",
                        15
                )
        );

        EditText code =
                input("کد تأیید");

        root.addView(code);

        Button next =
                button("تأیید");

        root.addView(next);

        next.setOnClickListener(v -> {

            if (code.getText()
                    .toString()
                    .trim()
                    .isEmpty()) {

                toast("کد تأیید را وارد کنید.");
                return;
            }

            showLanguage(
                    name,
                    password,
                    phone
            );
        });
    }

    void showLanguage(
            String name,
            String password,
            String phone
    ) {

        root = base();

        root.addView(
                text("انتخاب زبان", 22)
        );

        Spinner spinner =
                new Spinner(this);

        String[] languages = {
                "فارسی",
                "English"
        };

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        languages
                );

        spinner.setAdapter(adapter);

        root.addView(spinner);

        Button next =
                button("ادامه");

        root.addView(next);

        next.setOnClickListener(v ->
                showReferral(
                        name,
                        password,
                        phone,
                        spinner.getSelectedItem()
                                .toString()
                )
        );
    }

    void showReferral(
            String name,
            String password,
            String phone,
            String language
    ) {

        root = base();

        root.addView(
                text("کد معرف", 22)
        );

        root.addView(
                text(
                        "اگر توسط بازیکن دیگری معرفی شده‌اید، " +
                        "کد معرف او را وارد کنید.",
                        15
                )
        );

        EditText referral =
                input("Referral ID - اختیاری");

        root.addView(referral);

        Button next =
                button("ادامه");

        root.addView(next);

        next.setOnClickListener(v ->
                showGender(
                        name,
                        password,
                        phone,
                        language,
                        referral.getText()
                                .toString()
                                .trim()
                )
        );
    }

    void showGender(
            String name,
            String password,
            String phone,
            String language,
            String referral
    ) {

        root = base();

        root.addView(
                text("انتخاب جنسیت", 22)
        );

        RadioGroup group =
                new RadioGroup(this);

        RadioButton male =
                new RadioButton(this);

        male.setText("مرد");
        male.setTextColor(WHITE);

        RadioButton female =
                new RadioButton(this);

        female.setText("زن");
        female.setTextColor(WHITE);

        group.addView(male);
        group.addView(female);

        root.addView(group);

        Button next =
                button("ادامه");

        root.addView(next);

        next.setOnClickListener(v -> {

            if (group.getCheckedRadioButtonId()
                    == -1) {

                toast("جنسیت را انتخاب کنید.");
                return;
            }

            showEmail(
                    name,
                    password,
                    phone,
                    language,
                    referral
            );
        });
    }

    void showEmail(
            String name,
            String password,
            String phone,
            String language,
            String referral
    ) {

        root = base();

        root.addView(
                text("ایمیل", 22)
        );

        root.addView(
                text(
                        "لطفاً از یک ایمیل واقعی و در دسترس استفاده کنید.",
                        15
                )
        );

        EditText email =
                input("Email");

        email.setInputType(
                InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        );

        root.addView(email);

        Button finish =
                button("✨ تکمیل ثبت‌نام");

        root.addView(finish);

        finish.setOnClickListener(v -> {

            String mail =
                    email.getText()
                            .toString()
                            .trim();

            if (mail.isEmpty() ||
                    !mail.contains("@")) {

                toast("یک ایمیل معتبر وارد کنید.");
                return;
            }

            prefs.edit()
                    .putString("nickname", name)
                    .putString("password", password)
                    .putString("phone", phone)
                    .putString("language", language)
                    .putString("referral", referral)
                    .putString("email", mail)
                    .apply();

            showRegisterSuccess();
        });
    }

    void showRegisterSuccess() {

        root = base();

        root.addView(
                text("🎉 تبریک!", 30)
        );

        root.addView(
                text(
                        "ثبت‌نام شما با موفقیت انجام شد.",
                        19
                )
        );

        Button ok =
                button("OK");

        root.addView(ok);

        ok.setOnClickListener(v ->
                showGameWelcome(
                        prefs.getString(
                                "nickname",
                                ""
                        )
                )
        );
    }

    void showGameWelcome(String name) {

        root = base();

        root.addView(
                text("💎 خوش آمدی", 30)
        );

        root.addView(
                text(name, 24)
        );

        TextView rank =
                text("RANK: PLAYER", 18);

        rank.setTextColor(GREEN);

        root.addView(rank);

        root.addView(
                text(
                        "حساب شما با موفقیت وارد شد.",
                        17
                )
        );

        Button enter =
                button("🎮 ورود به بازی");

        Button logout =
                button("🚪 خروج");

        root.addView(enter);
        root.addView(logout);

        enter.setOnClickListener(v ->
                toast(
                        "محل دریافت گواهینامه رانندگی " +
                        "بعداً مشخص می‌شود."
                )
        );

        logout.setOnClickListener(v ->
                showLogin()
        );
    }

    void toast(String message) {

        Toast.makeText(
                this,
                message,
                Toast.LENGTH_LONG
        ).show();
    }
}
