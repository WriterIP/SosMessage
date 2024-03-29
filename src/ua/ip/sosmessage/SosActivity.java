package ua.ip.sosmessage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import ua.ip.sosmessage.data.PhonesDatasourse;
import ua.ip.sosmessage.data.Prefs;
import ua.ip.sosmessage.setphones.SetPhoneFragment;

/**
 * Created by ihorpysmennyi on 12/14/13.
 */
public class SosActivity extends ActionBarActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sosmain);
        setSupportActionBar();

        Fragment fragment;
        if (Prefs.isFirstRun(this))
            fragment = new FirstRunFragment();
        else if (new PhonesDatasourse(this).getAllPones().size() == 0)
            fragment = new SetPhoneFragment();
        else
            fragment = new SendMessageFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    private void setSupportActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        ImageView icon;
        if (android.os.Build.VERSION.SDK_INT < 11)
            icon = (ImageView) findViewById(R.id.home);
        else
            icon = (ImageView) findViewById(android.R.id.home);
        FrameLayout.LayoutParams iconLp = (FrameLayout.LayoutParams) icon.getLayoutParams();
        iconLp.topMargin = iconLp.bottomMargin = 0;
        icon.setLayoutParams(iconLp);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                if (new PhonesDatasourse(SosActivity.this).getAllPones().size() == 0) {
                    Toast.makeText(SosActivity.this, R.string.enter_phones, Toast.LENGTH_LONG).show();
                    break;
                }
                Fragment fragment = new SendMessageFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                    fragmentManager.popBackStack();
                }
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out, R.anim.slide_left_in, R.anim.slide_left_out);
                fragmentTransaction.replace(R.id.content_frame, fragment).commit();
                break;

        }
        return (super.onOptionsItemSelected(menuItem));
    }
}