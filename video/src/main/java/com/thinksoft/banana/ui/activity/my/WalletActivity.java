package com.thinksoft.banana.ui.activity.my;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.JsonElement;
import com.thinksoft.banana.R;
import com.thinksoft.banana.app.manage.UserInfoManage;
import com.thinksoft.banana.entity.bean.UserInfoBean;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.ui.activity.start.LoginActivity;
import com.thinksoft.banana.ui.fragment.my.WalletListFragment;
import com.txf.other_toolslibrary.manager.AppManager;
import com.txf.other_toolslibrary.tools.StringTools;
import com.txf.other_toolslibrary.utils.ToastUtils;
import com.txf.ui_mvplibrary.interfaces.IFragment;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpActivity;
import com.txf.ui_mvplibrary.ui.adapter.LjzFragmentAdapter;
import com.txf.ui_mvplibrary.utils.BundleUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author txf
 * @create 2019/2/19 0019
 * @我的钱包
 */
public class WalletActivity
        extends BaseMvpActivity<CommonContract.View, CommonContract.Presenter>
        implements CommonContract.View {
    TextView titleText, yeTextView;
    ViewPager mViewPager;
    SlidingTabLayout mSlidingTabLayout;

    View extRoot, extContent, cancelButton, confirmButton;
    EditText editText1, editText2, editText3;

    LjzFragmentAdapter mAdapter;
    String[] titls;


    UserInfoBean mUserInfoBean;

    public static Intent getIntent(Context context, UserInfoBean bean) {
        Intent i = new Intent(context, WalletActivity.class);
        i.putExtra("data", bean);
        return i;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBarTransparent();
        mUserInfoBean = (UserInfoBean) getIntent().getSerializableExtra("data");
        initView();
        initListener();
        initData();
        initSlidingTabLayout();
    }

    private void initSlidingTabLayout() {
        titls = new String[]{getString(R.string.总收入), getString(R.string.总支出)};
        mViewPager.setAdapter(mAdapter = new LjzFragmentAdapter(getSupportFragmentManager()));
        List<IFragment> datas = new ArrayList<>();
        for (String s : titls) {
            WalletListFragment walletListFragment = new WalletListFragment();
            walletListFragment.setArguments(BundleUtils.putString(s));
            datas.add(walletListFragment);
        }
        mAdapter.setData(datas, mViewPager);
        mSlidingTabLayout.setViewPager(mViewPager, titls);
        mViewPager.setCurrentItem(0);
    }

    private void initData() {
        titleText.setText(getString(R.string.我的钱包));
        yeTextView.setText(mUserInfoBean.getDiamond());
        extRoot.setVisibility(View.GONE);
    }

    private void initListener() {
        setOnClick(R.id.backButton, R.id.czButton, R.id.txButton,
                R.id.extRoot, R.id.extContent, R.id.cancelButton, R.id.confirmButton);
    }

    private void initView() {
        titleText = findViewById(R.id.titleText);
        yeTextView = findViewById(R.id.yeTextView);

        mViewPager = findViewById(R.id.ViewPager);
        mSlidingTabLayout = findViewById(R.id.SlidingTabLayout);

        extRoot = findViewById(R.id.extRoot);
        extContent = findViewById(R.id.extContent);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        cancelButton = findViewById(R.id.cancelButton);
        confirmButton = findViewById(R.id.confirmButton);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.backButton:
                finish();
                break;
            case R.id.czButton://充值
                startActivity(new Intent(getContext(), PaymentActivity.class));
                break;
            case R.id.txButton://提现
                showExtLayout();
                break;
            case R.id.extRoot:
                hideExtLayout();
                break;
            case R.id.cancelButton:
                hideExtLayout();
                break;
            case R.id.confirmButton:
                if (StringTools.isNull(editText1.getText().toString())) {
                    ToastUtils.show(getString(R.string.请输入提现金额));
                    return;
                }
                if (StringTools.isNull(editText2.getText().toString())) {
                    ToastUtils.show(getString(R.string.请输入收款人姓名));
                    return;
                }
                if (StringTools.isNull(editText3.getText().toString())) {
                    ToastUtils.show(getString(R.string.请输入收款账号));
                    return;
                }
                hideExtLayout();
                break;
        }
    }

    private void showExtLayout() {
        extRoot.setVisibility(View.VISIBLE);
    }

    private void hideExtLayout() {
        extRoot.setVisibility(View.GONE);
    }

    @Override
    public void httpOnSuccess(int sign, JsonElement data, String message) {

    }
    @Override
    public void httpOnError(int sign, int error, String message) {

    }
    @Override
    public void logInvalid() {
        super.logInvalid();
        UserInfoManage.getInstance().cleanUserInfo();
        new Builder()
                .setWith(1024)
                .setButton3(getString(R.string.确认))
                .setContent(getString(R.string.登录过期请重新登录))
                .setCancelable(false)
                .setDialogListener(new DialogListener(null, 0) {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            //登录过期
                            startActivity(new Intent(getContext(), LoginActivity.class));
                            AppManager.getInstance().retainAcitivity(LoginActivity.class);
                        }
                    }
                })
                .show();
    }
}
