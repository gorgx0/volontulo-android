package com.stxnext.volontulo.ui.offers;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseActivity;

import butterknife.OnClick;

public class OfferDetailsActivity extends VolontuloBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_details);
        init(R.string.title_activity_offers_details);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.button_step_out)
    public void doStepOut(View view) {
        Snackbar.make(view, "Zgłosiłeś się!!!", Snackbar.LENGTH_SHORT).show();
    }

}
