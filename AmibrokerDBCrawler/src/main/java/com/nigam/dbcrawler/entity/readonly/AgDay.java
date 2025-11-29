package com.nigam.dbcrawler.entity.readonly;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "ag_day")
@IdClass(AgDayId.class)
@Immutable
public class AgDay {

    @Id
    @Column(name = "candle_date", nullable = false)
    private Double candleDate;

    @Id
    @Column(name = "pk_field", nullable = false)
    private String pk_field;

    @Column(name = "previous_candle_date", nullable = false)
    private Double previousCandleDate;

    @Column(name = "inner_index", nullable = false)
    private Double innerIndex;

    @Column(name = "candle_time", nullable = false)
    private Double candleTime;

    @Column(name = "equity_name", nullable = false)
    private String equityName;

    @Column(name = "formula_name", nullable = false)
    private String formulaName;

    @Column(name = "ema_medium")
    private Double emaMedium;

    @Column(name = "ema_slow")
    private Double emaSlow;

    @Column(name = "hhv_x_green")
    private Double hhvXGreen;

    @Column(name = "hhv_2x_green")
    private Double hhv2xGreen;

    @Column(name = "hhv_3x_green")
    private Double hhv3xGreen;

    @Column(name = "hhv_x_red")
    private Double hhvXRed;

    @Column(name = "hhv_2x_red")
    private Double hhv2xRed;

    @Column(name = "hhv_3x_red")
    private Double hhv3xRed;

    @Column(name = "bs_hhv_x_green")
    private Double bsHhvXGreen;

    @Column(name = "bs_hhv_2x_green")
    private Double bsHhv2xGreen;

    @Column(name = "bs_hhv_3x_green")
    private Double bsHhv3xGreen;

    @Column(name = "bs_hhv_x_red")
    private Double bsHhvXRed;

    @Column(name = "bs_hhv_2x_red")
    private Double bsHhv2xRed;

    @Column(name = "bs_hhv_3x_red")
    private Double bsHhv3xRed;

    @Column(name = "sum_x_green")
    private Double sumXGreen;

    @Column(name = "sum_x_red")
    private Double sumXRed;

    @Column(name = "ema_fast")
    private Double emaFast;

    @Column(name = "auto_tr_q")
    private Double autoTrQ;

    @Column(name = "bs_c_bout_4")
    private Double bsCBout4;

    @Column(name = "bs_c_bout_2")
    private Double bsCBout2;

    @Column(name = "bs_c_bout")
    private Double bsCBout;

    @Column(name = "bs_c_bout_m2")
    private Double bsCBoutM2;

    @Column(name = "bs_c_bout_m4")
    private Double bsCBoutM4;

    @Column(name = "button_tr_q")
    private Double buttonTrQ;

    @Column(name = "close")
    private Double close;

    @Column(name = "close1")
    private Double close1;

    @Column(name = "close2")
    private Double close2;

    @Column(name = "close3")
    private Double close3;

    @Column(name = "vsa_close1")
    private Double vsaClose1;

    @Column(name = "vsa_close2")
    private Double vsaClose2;

    @Column(name = "candle_datetime")
    private Timestamp candleDatetime;

    @Column(name = "daily_bs_bull_start")
    private Double dailyBsBullStart;

    @Column(name = "daily_bs_bear_start")
    private Double dailyBsBearStart;

    @Column(name = "daily_bs_p_bull_start")
    private Double dailyBsPBullStart;

    @Column(name = "daily_is_tl_bull")
    private Double dailyIsTlBull;

    @Column(name = "daily_is_tl_bear")
    private Double dailyIsTlBear;

    @Column(name = "daily_bs_p_bear_start")
    private Double dailyBsPBearStart;

    @Column(name = "daily_tl_middle")
    private Double dailyTlMiddle;

    @Column(name = "daily_tl_ohlc")
    private Double dailyTlOhlc;

    @Column(name = "daily_bs_bull_cross")
    private Double dailyBsBullCross;

    @Column(name = "daily_resistance1")
    private Double dailyResistance1;

    @Column(name = "daily_resistance2")
    private Double dailyResistance2;

    @Column(name = "daily_support1")
    private Double dailySupport1;

    @Column(name = "daily_support2")
    private Double dailySupport2;

    @Column(name = "daily_tl_deviation")
    private Double dailyTlDeviation;

    @Column(name = "daily_tl")
    private Double dailyTl;

    @Column(name = "daily_p_tl")
    private Double dailyPTl;

    @Column(name = "daily_p_tl_ohlc")
    private Double dailyPTlOhlc;

    @Column(name = "daily_bs_bear_cross")
    private Double dailyBsBearCross;

    @Column(name = "monthly_bs_bull_start")
    private Double monthlyBsBullStart;

    @Column(name = "monthly_bs_bear_start")
    private Double monthlyBsBearStart;

    @Column(name = "monthly_bs_p_bull_start")
    private Double monthlyBsPBullStart;

    @Column(name = "monthly_is_tl_bull")
    private Double monthlyIsTlBull;

    @Column(name = "monthly_is_tl_bear")
    private Double monthlyIsTlBear;

    @Column(name = "monthly_bs_p_bear_start")
    private Double monthlyBsPBearStart;

    @Column(name = "monthly_tl_middle")
    private Double monthlyTlMiddle;

    @Column(name = "monthly_tl_ohlc")
    private Double monthlyTlOhlc;

    @Column(name = "monthly_bs_bull_cross")
    private Double monthlyBsBullCross;

    @Column(name = "monthly_resistance1")
    private Double monthlyResistance1;

    @Column(name = "monthly_resistance2")
    private Double monthlyResistance2;

    @Column(name = "monthly_support1")
    private Double monthlySupport1;

    @Column(name = "monthly_support2")
    private Double monthlySupport2;

    @Column(name = "monthly_tl_deviation")
    private Double monthlyTlDeviation;

    @Column(name = "monthly_tl")
    private Double monthlyTl;

    @Column(name = "monthly_p_tl")
    private Double monthlyPTl;

    @Column(name = "monthly_p_tl_ohlc")
    private Double monthlyPTlOhlc;

    @Column(name = "monthly_bs_bear_cross")
    private Double monthlyBsBearCross;

    @Column(name = "quarterly_bs_bull_start")
    private Double quarterlyBsBullStart;

    @Column(name = "quarterly_bs_bear_start")
    private Double quarterlyBsBearStart;

    @Column(name = "quarterly_bs_p_bull_start")
    private Double quarterlyBsPBullStart;

    @Column(name = "quarterly_is_tl_bull")
    private Double quarterlyIsTlBull;

    @Column(name = "quarterly_is_tl_bear")
    private Double quarterlyIsTlBear;

    @Column(name = "quarterly_bs_p_bear_start")
    private Double quarterlyBsPBearStart;

    @Column(name = "quarterly_tl_middle")
    private Double quarterlyTlMiddle;

    @Column(name = "quarterly_tl_ohlc")
    private Double quarterlyTlOhlc;

    @Column(name = "quarterly_bs_bull_cross")
    private Double quarterlyBsBullCross;

    @Column(name = "quarterly_resistance1")
    private Double quarterlyResistance1;

    @Column(name = "quarterly_resistance2")
    private Double quarterlyResistance2;

    @Column(name = "quarterly_support1")
    private Double quarterlySupport1;

    @Column(name = "quarterly_support2")
    private Double quarterlySupport2;

    @Column(name = "quarterly_tl_deviation")
    private Double quarterlyTlDeviation;

    @Column(name = "quarterly_tl")
    private Double quarterlyTl;

    @Column(name = "quarterly_p_tl")
    private Double quarterlyPTl;

    @Column(name = "quarterly_p_tl_ohlc")
    private Double quarterlyPTlOhlc;

    @Column(name = "quarterly_bs_bear_cross")
    private Double quarterlyBsBearCross;

    @Column(name = "weekly_bs_bull_start")
    private Double weeklyBsBullStart;

    @Column(name = "weekly_bs_bear_start")
    private Double weeklyBsBearStart;

    @Column(name = "weekly_bs_p_bull_start")
    private Double weeklyBsPBullStart;

    @Column(name = "weekly_is_tl_bull")
    private Double weeklyIsTlBull;

    @Column(name = "weekly_is_tl_bear")
    private Double weeklyIsTlBear;

    @Column(name = "weekly_bs_p_bear_start")
    private Double weeklyBsPBearStart;

    @Column(name = "weekly_tl_middle")
    private Double weeklyTlMiddle;

    @Column(name = "weekly_tl_ohlc")
    private Double weeklyTlOhlc;

    @Column(name = "weekly_bs_bull_cross")
    private Double weeklyBsBullCross;

    @Column(name = "weekly_resistance1")
    private Double weeklyResistance1;

    @Column(name = "weekly_resistance2")
    private Double weeklyResistance2;

    @Column(name = "weekly_support1")
    private Double weeklySupport1;

    @Column(name = "weekly_support2")
    private Double weeklySupport2;

    @Column(name = "weekly_tl_deviation")
    private Double weeklyTlDeviation;

    @Column(name = "weekly_tl")
    private Double weeklyTl;

    @Column(name = "weekly_p_tl")
    private Double weeklyPTl;

    @Column(name = "weekly_p_tl_ohlc")
    private Double weeklyPTlOhlc;

    @Column(name = "weekly_bs_bear_cross")
    private Double weeklyBsBearCross;

    @Column(name = "high")
    private Double high;

    @Column(name = "high1")
    private Double high1;

    @Column(name = "high2")
    private Double high2;

    @Column(name = "vsa_high1")
    private Double vsaHigh1;

    @Column(name = "vsa_high2")
    private Double vsaHigh2;

    @Column(name = "low")
    private Double low;

    @Column(name = "low1")
    private Double low1;

    @Column(name = "low2")
    private Double low2;

    @Column(name = "vsa_low1")
    private Double vsaLow1;

    @Column(name = "vsa_low2")
    private Double vsaLow2;

    @Column(name = "long_sl")
    private Double longSl;

    @Column(name = "open")
    private Double open;

    @Column(name = "open1")
    private Double open1;

    @Column(name = "open2")
    private Double open2;

    @Column(name = "vsa_open1")
    private Double vsaOpen1;

    @Column(name = "vsa_open2")
    private Double vsaOpen2;

    @Column(name = "current_datetime")
    private LocalDateTime currentDatetime;

    @Column(name = "ma_vsa_spreed")
    private Double maVsaSpreed;

    @Column(name = "short_sl")
    private Double shortSl;

    @Column(name = "ticker_type")
    private String tickerType;

    @Column(name = "unique_name")
    private String uniqueName;

    @Column(name = "volume")
    private Double volume;

    @Column(name = "volume1")
    private Double volume1;

    @Column(name = "volume2")
    private Double volume2;

    @Column(name = "vsa_volume1")
    private Double vsaVolume1;

    @Column(name = "vsa_volume2")
    private Double vsaVolume2;

    @Column(name = "ma_vsa_volume")
    private Double maVsaVolume;

    @Column(name = "ema_200_volume")
    private Double ema200Volume;

    @Column(name = "bs_c_bdown_4")
    private Double bsCBdown4;

    @Column(name = "bs_c_bdown_2")
    private Double bsCBdown2;

    @Column(name = "bs_c_bdown")
    private Double bsCBdown;

    @Column(name = "bs_c_bdown_m2")
    private Double bsCBdownM2;

    @Column(name = "bs_c_bdown_m4")
    private Double bsCBdownM4;
}
