package com.nigam.dbcrawler.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "Amibroker_Grafana_Algo2_10D")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AmibrokerGrafanaAlgoRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    // Composite primary key handled with @IdClass
    @Id
    @Column(name = "PK_Field", nullable = false)
    private String pkField;

    @Id
    @Column(name = "candle_Date", nullable = false)
    private Double candleDate;

    @Column(name = "Auto_Trade_Quantity")
    private Double autoTradeQuantity;

    @Column(name = "BS_BreakDown")
    private Double bsBreakDown;

    @Column(name = "BS_BreakDown_2")
    private Double bsBreakDown2;

    @Column(name = "BS_BreakDown_4")
    private Double bsBreakDown4;

    @Column(name = "BS_BreakDown_m2")
    private Double bsBreakDownM2;

    @Column(name = "BS_BreakDown_m4")
    private Double bsBreakDownM4;

    @Column(name = "BS_Breakout")
    private Double bsBreakout;

    @Column(name = "BS_Breakout_2")
    private Double bsBreakout2;

    @Column(name = "BS_Breakout_4")
    private Double bsBreakout4;

    @Column(name = "BS_Breakout_m2")
    private Double bsBreakoutM2;

    @Column(name = "BS_Breakout_m4")
    private Double bsBreakoutM4;

    @Column(name = "BS_HHV_Volume_Since_2X_Green")
    private Double bsHhvVolumeSince2xGreen;

    @Column(name = "BS_HHV_Volume_Since_2X_Red")
    private Double bsHhvVolumeSince2xRed;

    @Column(name = "BS_HHV_Volume_Since_3X_Green")
    private Double bsHhvVolumeSince3xGreen;

    @Column(name = "BS_HHV_Volume_Since_3X_Red")
    private Double bsHhvVolumeSince3xRed;

    @Column(name = "BS_HHV_Volume_Since_X_Green")
    private Double bsHhvVolumeSinceXGreen;

    @Column(name = "BS_HHV_Volume_Since_X_Red")
    private Double bsHhvVolumeSinceXRed;

    @Column(name = "Button_Trade_Quantity")
    private Double buttonTradeQuantity;

    @Column(name = "Close")
    private Double close;

    @Column(name = "CurrentIndex")
    private Double currentIndex;

    @Column(name = "EMA_Close_Fast")
    private Double emaCloseFast;

    @Column(name = "EMA_Close_Medium")
    private Double emaCloseMedium;

    @Column(name = "EMA_Close_Slow")
    private Double emaCloseSlow;

    @Column(name = "High")
    private Double high;

    @Column(name = "Low")
    private Double low;

    @Column(name = "Open")
    private Double open;

    @Column(name = "Volume")
    private Long volume;

    @Column(name = "Volume_200_EMA")
    private Double volume200Ema;

    @Column(name = "Previous_Close")
    private Double previousClose;

    @Column(name = "Previous_Open")
    private Double previousOpen;

    @Column(name = "Previous_High")
    private Double previousHigh;

    @Column(name = "Previous_Low")
    private Double previousLow;

    @Column(name = "Previous_Volume")
    private Double previousVolume;

    @Column(name = "Long_SL")
    private Double longSl;

    @Column(name = "Short_SL")
    private Double shortSl;

    @Column(name = "candle_DateTime")
    private Timestamp candleDateTime;

    @Column(name = "pushTimeStamp")
    private Timestamp pushTimeStamp;

    @Column(name = "tickerName")
    private String tickerName;

    @Column(name = "tickerType")
    private String tickerType;

    @Column(name = "uniqueID")
    private String uniqueId;

    @Column(name = "Previous_2D_Close")
    private Double previous2dClose;

    @Column(name = "Previous_2D_High")
    private Double previous2dHigh;

    @Column(name = "Previous_2D_Low")
    private Double previous2dLow;

    @Column(name = "Previous_2D_Open")
    private Double previous2dOpen;

    @Column(name = "Previous_2D_Volume")
    private Double previous2dVolume;

    @Column(name = "Previous_3D_Close")
    private Double previous3dClose;

    @Column(name = "SumSince_Volume_Since_X_Green")
    private Double sumSinceVolumeSinceXGreen;

    @Column(name = "SumSince_Volume_Since_X_Red")
    private Double sumSinceVolumeSinceXRed;

    @Column(name = "VSA_P1D_HV_Candle_Close")
    private Double vsaP1dHvCandleClose;

    @Column(name = "VSA_P1D_HV_Candle_High")
    private Double vsaP1dHvCandleHigh;

    @Column(name = "VSA_P1D_HV_Candle_Low")
    private Double vsaP1dHvCandleLow;

    @Column(name = "VSA_P1D_HV_Candle_Open")
    private Double vsaP1dHvCandleOpen;

    @Column(name = "VSA_P1D_HV_Candle_Volume")
    private Double vsaP1dHvCandleVolume;

    @Column(name = "VSA_P2D_HV_Candle_Close")
    private Double vsaP2dHvCandleClose;

    @Column(name = "VSA_P2D_HV_Candle_High")
    private Double vsaP2dHvCandleHigh;

    @Column(name = "VSA_P2D_HV_Candle_Low")
    private Double vsaP2dHvCandleLow;

    @Column(name = "VSA_P2D_HV_Candle_Open")
    private Double vsaP2dHvCandleOpen;

    @Column(name = "VSA_P2D_HV_Candle_Volume")
    private Double vsaP2dHvCandleVolume;

    @Column(name = "VSA_Spread_MA")
    private Double vsaSpreadMa;

    @Column(name = "VSA_Volume_MA")
    private Double vsaVolumeMa;

    @Column(name = "HHV_Volume_Since_2X_Green")
    private Double hhvVolumeSince2xGreen;

    @Column(name = "HHV_Volume_Since_2X_Red")
    private Double hhvVolumeSince2xRed;

    @Column(name = "HHV_Volume_Since_3X_Green")
    private Double hhvVolumeSince3xGreen;

    @Column(name = "HHV_Volume_Since_3X_Red")
    private Double hhvVolumeSince3xRed;

    @Column(name = "HHV_Volume_Since_X_Green")
    private Double hhvVolumeSinceXGreen;

    @Column(name = "HHV_Volume_Since_X_Red")
    private Double hhvVolumeSinceXRed;

    @Column(name = "candle_Previous_Date")
    private Double candlePreviousDate;

    @Column(name = "candle_Time")
    private Double candleTime;

    // ===== Daily trendline fields =====
    @Column(name = "data_daily_BS_TL_BearCross")
    private Double dataDailyBsTlBearCross;

    @Column(name = "data_daily_BS_TL_BearStart")
    private Double dataDailyBsTlBearStart;

    @Column(name = "data_daily_BS_TL_BullCross")
    private Double dataDailyBsTlBullCross;

    @Column(name = "data_daily_BS_TL_BullStart")
    private Double dataDailyBsTlBullStart;

    @Column(name = "data_daily_BS_TL_Previous_BearStart")
    private Double dataDailyBsTlPreviousBearStart;

    @Column(name = "data_daily_BS_TL_Previous_BullStart")
    private Double dataDailyBsTlPreviousBullStart;

    @Column(name = "data_daily_Is_trendlineBearish")
    private Long dataDailyIsTrendlineBearish;

    @Column(name = "data_daily_Is_trendlineBullish")
    private Long dataDailyIsTrendlineBullish;

    @Column(name = "data_daily_previous_trendline")
    private Double dataDailyPreviousTrendline;

    @Column(name = "data_daily_previous_trendlineOHLC")
    private Double dataDailyPreviousTrendlineOhlc;

    @Column(name = "data_daily_resistance1")
    private Double dataDailyResistance1;

    @Column(name = "data_daily_resistance2")
    private Double dataDailyResistance2;

    @Column(name = "data_daily_support1")
    private Double dataDailySupport1;

    @Column(name = "data_daily_support2")
    private Double dataDailySupport2;

    @Column(name = "data_daily_trendline")
    private Double dataDailyTrendline;

    @Column(name = "data_daily_trendlineDeviation")
    private Double dataDailyTrendlineDeviation;

    @Column(name = "data_daily_trendlineMiddle")
    private Double dataDailyTrendlineMiddle;

    @Column(name = "data_daily_trendlineOHLC")
    private Long dataDailyTrendlineOhlc;

    // ===== Weekly trendline fields =====
    @Column(name = "data_weekly_BS_TL_BearCross")
    private Double dataWeeklyBsTlBearCross;

    @Column(name = "data_weekly_BS_TL_BearStart")
    private Double dataWeeklyBsTlBearStart;

    @Column(name = "data_weekly_BS_TL_BullCross")
    private Double dataWeeklyBsTlBullCross;

    @Column(name = "data_weekly_BS_TL_BullStart")
    private Double dataWeeklyBsTlBullStart;

    @Column(name = "data_weekly_BS_TL_Previous_BearStart")
    private Double dataWeeklyBsTlPreviousBearStart;

    @Column(name = "data_weekly_BS_TL_Previous_BullStart")
    private Double dataWeeklyBsTlPreviousBullStart;

    @Column(name = "data_weekly_Is_trendlineBearish")
    private Long dataWeeklyIsTrendlineBearish;

    @Column(name = "data_weekly_Is_trendlineBullish")
    private Long dataWeeklyIsTrendlineBullish;

    @Column(name = "data_weekly_previous_trendline")
    private Double dataWeeklyPreviousTrendline;

    @Column(name = "data_weekly_previous_trendlineOHLC")
    private Double dataWeeklyPreviousTrendlineOhlc;

    @Column(name = "data_weekly_resistance1")
    private Double dataWeeklyResistance1;

    @Column(name = "data_weekly_resistance2")
    private Double dataWeeklyResistance2;

    @Column(name = "data_weekly_support1")
    private Double dataWeeklySupport1;

    @Column(name = "data_weekly_support2")
    private Double dataWeeklySupport2;

    @Column(name = "data_weekly_trendline")
    private Double dataWeeklyTrendline;

    @Column(name = "data_weekly_trendlineDeviation")
    private Double dataWeeklyTrendlineDeviation;

    @Column(name = "data_weekly_trendlineMiddle")
    private Double dataWeeklyTrendlineMiddle;

    @Column(name = "data_weekly_trendlineOHLC")
    private Long dataWeeklyTrendlineOhlc;

    // ===== Monthly trendline fields =====
    @Column(name = "data_monthly_BS_TL_BearCross")
    private Double dataMonthlyBsTlBearCross;

    @Column(name = "data_monthly_BS_TL_BearStart")
    private Double dataMonthlyBsTlBearStart;

    @Column(name = "data_monthly_BS_TL_BullCross")
    private Double dataMonthlyBsTlBullCross;

    @Column(name = "data_monthly_BS_TL_BullStart")
    private Double dataMonthlyBsTlBullStart;

    @Column(name = "data_monthly_BS_TL_Previous_BearStart")
    private Double dataMonthlyBsTlPreviousBearStart;

    @Column(name = "data_monthly_BS_TL_Previous_BullStart")
    private Double dataMonthlyBsTlPreviousBullStart;

    @Column(name = "data_monthly_Is_trendlineBearish")
    private Long dataMonthlyIsTrendlineBearish;

    @Column(name = "data_monthly_Is_trendlineBullish")
    private Long dataMonthlyIsTrendlineBullish;

    @Column(name = "data_monthly_previous_trendline")
    private Double dataMonthlyPreviousTrendline;

    @Column(name = "data_monthly_previous_trendlineOHLC")
    private Double dataMonthlyPreviousTrendlineOhlc;

    @Column(name = "data_monthly_resistance1")
    private Double dataMonthlyResistance1;

    @Column(name = "data_monthly_resistance2")
    private Double dataMonthlyResistance2;

    @Column(name = "data_monthly_support1")
    private Double dataMonthlySupport1;

    @Column(name = "data_monthly_support2")
    private Double dataMonthlySupport2;

    @Column(name = "data_monthly_trendline")
    private Double dataMonthlyTrendline;

    @Column(name = "data_monthly_trendlineDeviation")
    private Double dataMonthlyTrendlineDeviation;

    @Column(name = "data_monthly_trendlineMiddle")
    private Double dataMonthlyTrendlineMiddle;

    @Column(name = "data_monthly_trendlineOHLC")
    private Long dataMonthlyTrendlineOhlc;

    // ===== Quarterly trendline fields =====
    @Column(name = "data_quarterly_BS_TL_BearCross")
    private Double dataQuarterlyBsTlBearCross;

    @Column(name = "data_quarterly_BS_TL_BearStart")
    private Double dataQuarterlyBsTlBearStart;

    @Column(name = "data_quarterly_BS_TL_BullCross")
    private Double dataQuarterlyBsTlBullCross;

    @Column(name = "data_quarterly_BS_TL_BullStart")
    private Double dataQuarterlyBsTlBullStart;

    @Column(name = "data_quarterly_BS_TL_Previous_BearStart")
    private Double dataQuarterlyBsTlPreviousBearStart;

    @Column(name = "data_quarterly_BS_TL_Previous_BullStart")
    private Double dataQuarterlyBsTlPreviousBullStart;

    @Column(name = "data_quarterly_Is_trendlineBearish")
    private Long dataQuarterlyIsTrendlineBearish;

    @Column(name = "data_quarterly_Is_trendlineBullish")
    private Long dataQuarterlyIsTrendlineBullish;

    @Column(name = "data_quarterly_previous_trendline")
    private Double dataQuarterlyPreviousTrendline;

    @Column(name = "data_quarterly_previous_trendlineOHLC")
    private Double dataQuarterlyPreviousTrendlineOhlc;

    @Column(name = "data_quarterly_resistance1")
    private Double dataQuarterlyResistance1;

    @Column(name = "data_quarterly_resistance2")
    private Double dataQuarterlyResistance2;

    @Column(name = "data_quarterly_support1")
    private Double dataQuarterlySupport1;

    @Column(name = "data_quarterly_support2")
    private Double dataQuarterlySupport2;

    @Column(name = "data_quarterly_trendline")
    private Double dataQuarterlyTrendline;

    @Column(name = "data_quarterly_trendlineDeviation")
    private Double dataQuarterlyTrendlineDeviation;

    @Column(name = "data_quarterly_trendlineMiddle")
    private Double dataQuarterlyTrendlineMiddle;

    @Column(name = "data_quarterly_trendlineOHLC")
    private Long dataQuarterlyTrendlineOhlc;

    @Column(name = "dataType")
    private String dataType;
}
