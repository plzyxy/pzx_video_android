package com.thinksoft.banana.ui.activity.start;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.entity.bean.AgreementBean;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.mvp.model.CommonStartModel;
import com.thinksoft.banana.mvp.presenter.CommonPresenter;
import com.thinksoft.banana.net.api.ApiRequestTask;
import com.txf.other_toolslibrary.tools.JsonTools;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpActivity;

/**
 * @author txf
 * @create 2019/2/28 0028
 * @用户协议
 */
public class AgreementActivity
        extends BaseMvpActivity<CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View {
    TextView contentTV, titleTV;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_agreement;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContract(this, new CommonPresenter(this, new CommonStartModel()));
        initView();
        getPresenter().getData(ApiRequestTask.start.TAG_USER_AGREEMENT);
    }

    private void initView() {
        contentTV = findViewById(R.id.contentTV);
        titleTV = findViewById(R.id.titleTV);

        titleTV.setText(getString(R.string.用户协议));
        setOnClick(R.id.backButton);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.backButton:
                finish();
                break;
        }
    }

    @Override
    public void httpOnSuccess(int tag, JsonElement data, String message) {
        AgreementBean bean = JsonTools.fromJson(data, AgreementBean.class);
        if (bean == null || bean.getUserAgreement() == null)
            return;
        setText(bean.getUserAgreement().getContent());
    }

    @Override
    public void httpOnError(int tag, int error, String message) {

    }

    public void setText(String text) {
        contentTV.setText(Html.fromHtml(text));
    }

}
