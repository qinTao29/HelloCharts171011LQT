package cn.edu.gdpt.hellocharts171011lqt;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

public class MainActivity extends AppCompatActivity {
private TabLayout mTabLayoutMainTabs;
private ViewPager mViewPagerMainCharts;
private View lineView,pieView,columView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initTabLayout();
        initLineChart();
        initPicChart();
        initColumn();
    }
    private void initColumn() {
        ColumnChartView columnChartView= columView.findViewById(R.id.ccv_main_market);
        String [] year=new String[]{"2013","2014","2015","2016","2017"};
        List<AxisValue>axisXValues=new ArrayList<>();
        for (int i=0;i<year.length;i++){
            axisXValues.add(new AxisValue(i).setLabel(year[i]));
        }
        Axis axisX=new Axis(axisXValues);
        int [] columnY={500,1000,1500,2000,2800,3000};
        List<AxisValue> axisYValue=new ArrayList<>();
        for (int i=0;i<columnY.length;i++){
            axisYValue.add(new AxisValue(i).setValue(columnY[i]));
        }
        Axis axisY=new Axis(axisYValue);
        int[] columnVaues={924,1367,1200,1500,1000};
        int[] columnColor={ChartUtils.COLOR_BLUE,ChartUtils.COLOR_GREEN,ChartUtils.COLOR_ORANGE,ChartUtils.COLOR_RED,ChartUtils.COLOR_VIOLET};
        List<Column> columns=new ArrayList<>();
        for (int i=0;i<columnVaues.length;i++){
            List<SubcolumnValue> subcolumnValues=new ArrayList<>();
            subcolumnValues.add(new SubcolumnValue(columnVaues[i],columnColor[i]));
            columns.add(new Column(subcolumnValues).setHasLabelsOnlyForSelected(true));
        }
        ColumnChartData columnChartData=new ColumnChartData(columns);
        columnChartData.setAxisXBottom(axisX);
        columnChartData.setAxisYLeft(axisY);
        columnChartView.setColumnChartData(columnChartData);
    }
    private void initPicChart() {
        PieChartView pieChartView=pieView.findViewById(R.id.pcv_main_edu);
        int [] pieData={8,24,35,23,10};
        int [] color={Color.parseColor("#356fb3"),Color.parseColor("#b53663"),Color.parseColor("#86aa3d"),Color.parseColor("#6a4b90"),Color.parseColor("#2e9cba")};
        List<SliceValue> sliceValues=new ArrayList<>();
        for (int i=0;i<pieData.length;i++){
            SliceValue sliceValue= new SliceValue((float)pieData[i],color[i]);
            sliceValues.add(sliceValue);
        }
      final PieChartData pieChartData=new PieChartData();
        pieChartData.setValues(sliceValues);
        pieChartData.setHasLabels(true);
        pieChartData.setHasCenterCircle(true);
        pieChartData.setCenterText1("数据");
        pieChartView.setPieChartData(pieChartData);
        pieChartView.setOnValueTouchListener(new PieChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int i, SliceValue sliceValue) {
                String[] stateChar={"高等教育","职业教育","语言教育","K12教育","其他"};
                pieChartData.setCenterText1(stateChar[i]);
            }
            @Override
            public void onValueDeselected() {

            }
        });
    }
    private void initLineChart() {
        LineChartView lineChartView=lineView.findViewById(R.id.lcv_main_temperature);
        String[] lineData={"周一","周二","周三","周四","周五","周六","周七"};
        List<AxisValue> axisXValues=new ArrayList<>();
        for (int i=0;i<lineData.length;i++){
            axisXValues.add(new AxisValue(i).setLabel(lineData[i]));
        }
        Axis axisX=new Axis();
        axisX.setValues(axisXValues);
        Axis axisY=new Axis();
        int [] temperature={24,27,26,25,26,27,24};
        List<PointValue> pointValuesnew =new ArrayList<>();
        for (int i=0;i<temperature.length;i++){
            pointValuesnew.add(new PointValue(i,temperature[i]));
        }
        Line line=new Line();
        line.setValues(pointValuesnew);
        List<Line> lines=new ArrayList<>();
        lines.add(line);
        LineChartData data=new LineChartData();
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);
        data.setLines(lines);
        lineChartView.setLineChartData(data);
        lineChartView.setVisibility(View.VISIBLE);
    }
    private void initTabLayout() {
        LayoutInflater mInflater=LayoutInflater.from(this);
        lineView=mInflater.inflate(R.layout.layout_line_chart,null);
        pieView=mInflater.inflate(R.layout.layout_pic_chart,null);
        columView=mInflater.inflate(R.layout.layout_colum_chart,null);
        List<View> mViewList=new ArrayList<>();
        mViewList.add(lineView);
        mViewList.add(pieView);
        mViewList.add(columView);
        List<String> mTitleList=new ArrayList<>();
        mTitleList.add("线形图");
        mTitleList.add("饼状图");
        mTitleList.add("柱状图");
        ViewPagerAdapter mAdapter=new ViewPagerAdapter(mViewList,mTitleList);
        mViewPagerMainCharts.setAdapter(mAdapter);
        mTabLayoutMainTabs.setTabMode(TabLayout.MODE_FIXED);
        mTabLayoutMainTabs.addTab(mTabLayoutMainTabs.newTab().setText(mTitleList.get(0)));
        mTabLayoutMainTabs.addTab(mTabLayoutMainTabs.newTab().setText(mTitleList.get(1)));
        mTabLayoutMainTabs.addTab(mTabLayoutMainTabs.newTab().setText(mTitleList.get(2)));
        mTabLayoutMainTabs.setupWithViewPager(mViewPagerMainCharts);
    }
    private void initView() {
        mTabLayoutMainTabs=(TabLayout)findViewById(R.id.tabLayout_main_tabs);
        mViewPagerMainCharts=(ViewPager)findViewById(R.id.viewPager_main_charts);
    }
}
