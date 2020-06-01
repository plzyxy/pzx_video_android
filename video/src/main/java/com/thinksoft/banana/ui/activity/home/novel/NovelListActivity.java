package com.thinksoft.banana.ui.activity.home.novel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.thinksoft.banana.R;
import com.thinksoft.banana.entity.bean.NovelTypeDataBean;
import com.thinksoft.banana.mvp.contract.CommonContract;
import com.thinksoft.banana.ui.fragment.home.novel.NovelTypeListFragemt;
import com.txf.ui_mvplibrary.ui.activity.BaseMvpActivity;
import com.txf.ui_mvplibrary.utils.BundleUtils;

/**
 * @author txf
 * @create 2019/4/9 0009
 * @小说列表
 */
public class NovelListActivity
        extends BaseMvpActivity<CommonContract.View, CommonContract.Presenter> {
    NovelTypeDataBean mNovelTypeDataBean;
    TextView titleTV;

    public static Intent getIntent(Context context, NovelTypeDataBean bean) {
        Intent i = new Intent(context, NovelListActivity.class);
        i.putExtra("data", bean);
        return i;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_novel_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNovelTypeDataBean = (NovelTypeDataBean) getIntent().getSerializableExtra("data");
        initView();
        if (mNovelTypeDataBean != null) {
            titleTV.setText(mNovelTypeDataBean.getName());
            NovelTypeListFragemt mNovelListFragment = new NovelTypeListFragemt();
            mNovelListFragment.setArguments(BundleUtils.putSerializable(mNovelTypeDataBean));
            showFragment(R.id.FrameLayout, mNovelListFragment, "NOVEL_LIST");
            mNovelListFragment.show();
        }
    }
    private void initView() {
        titleTV = findViewById(R.id.titleTV);
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
