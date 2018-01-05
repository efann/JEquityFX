/*
 * JEquity
 * Copyright(c) 2008-2018, Beowurks
 * Original Author: Eddie Fann
 * License: Eclipse Public License - v 2.0 (https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.html)
 *
 */
package com.beowurks.jequity.dao;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

// -----------------------------------------------------------------------------
// -----------------------------------------------------------------------------
// -----------------------------------------------------------------------------
public interface IDefaultXYChartSeries
{

  // -----------------------------------------------------------------------------
  LineChart<java.util.Date, Number> getLineChart();

  // -----------------------------------------------------------------------------
  XYChart.Series[] getXYDataSeries();

  // -----------------------------------------------------------------------------
  String getSymbol();

  // -----------------------------------------------------------------------------
  String getDescription();

  // -----------------------------------------------------------------------------
  java.util.Date getStartDate();

  // -----------------------------------------------------------------------------
  java.util.Date getEndDate();

  // -----------------------------------------------------------------------------
}
// -----------------------------------------------------------------------------
// -----------------------------------------------------------------------------
// -----------------------------------------------------------------------------
