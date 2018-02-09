/*
 * JEquity
 * Copyright(c) 2008-2018, Beowurks
 * Original Author: Eddie Fann
 * License: Eclipse Public License - v 2.0 (https://www.eclipse.org/org/documents/epl-2.0/EPL-2.0.html)
 *
 */

package com.beowurks.jequity.controller.tab;

import com.beowurks.jequity.dao.XMLTextReader;
import com.beowurks.jequity.dao.XMLTextWriter;
import com.beowurks.jequity.dao.combobox.StringKeyItem;
import com.beowurks.jequity.dao.hibernate.HibernateUtil;
import com.beowurks.jequity.dao.hibernate.SymbolEntity;
import com.beowurks.jequity.dao.tableview.GroupProperty;
import com.beowurks.jequity.utility.AppProperties;
import com.beowurks.jequity.utility.Constants;
import com.beowurks.jequity.utility.Misc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.w3c.dom.Node;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

// ---------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
public class TabHistoricalGraphController
{
  @FXML
  private ComboBox<StringKeyItem> cboStocks;

  @FXML
  private DatePicker txtStart;

  @FXML
  private DatePicker txtEnd;

  @FXML
  private CheckBox chkUseToday;

  @FXML
  private Button btnAnalyze;

  @FXML
  private LineChart<Date, Number> chtLineChart;

  private final ObservableList<GroupProperty> foDataList = FXCollections.observableArrayList();

  private XYChart.Series[] faXYDataSeries;


  private String fcCurrentSymbol = "";
  private String fcCurrentXML = "";

  // ---------------------------------------------------------------------------------------------------------------------
  // From https://stackoverflow.com/questions/34785417/javafx-fxml-controller-constructor-vs-initialize-method
  @FXML
  public void initialize()
  {
    this.setupXYDataSeries();
    this.setupChart();

    this.setupListeners();

    this.txtStart.setValue(AppProperties.INSTANCE.getHistoricalStartDefault().toLocalDate());
  }

  // ---------------------------------------------------------------------------------------------------------------------
  public ComboBox<StringKeyItem> getComboBox()
  {
    return (this.cboStocks);
  }

  // ---------------------------------------------------------------------------------------------------------------------
  private void setupListeners()
  {
    this.btnAnalyze.setOnAction(toActionEvent -> TabHistoricalGraphController.this.analyzeData());
    this.chkUseToday.setOnAction(toActionEvent -> TabHistoricalGraphController.this.useToday());
    this.cboStocks.setOnAction(toActionEvent -> TabHistoricalGraphController.this.updateOnComboBoxSelect());
  }

  // ---------------------------------------------------------------------------------------------------------------------
  private void setupChart()
  {
    for (final XYChart.Series loSeries : this.faXYDataSeries)
    {
      this.chtLineChart.getData().add(loSeries);
    }
  }

  // ---------------------------------------------------------------------------------------------------------------------
  private void setupXYDataSeries()
  {
    this.faXYDataSeries = new XYChart.Series[5];

    this.faXYDataSeries[0] = new XYChart.Series();
    this.faXYDataSeries[0].setName("Open");
    this.faXYDataSeries[1] = new XYChart.Series();
    this.faXYDataSeries[1].setName("High");
    this.faXYDataSeries[2] = new XYChart.Series();
    this.faXYDataSeries[2].setName("Low");
    this.faXYDataSeries[3] = new XYChart.Series();
    this.faXYDataSeries[3].setName("Close");
    this.faXYDataSeries[4] = new XYChart.Series();
    this.faXYDataSeries[4].setName("Adj Close");
  }

  // ---------------------------------------------------------------------------------------------------------------------
  synchronized public StringKeyItem refreshData()
  {
    final StringKeyItem loSelectItem = this.cboStocks.getSelectionModel().getSelectedItem();

    final HibernateUtil loHibernate = HibernateUtil.INSTANCE;
    final Session loSession = loHibernate.getSession();

    // Needs to be * (all fields) as we're populating the SymbolEntity.class.
    final NativeQuery loQuery = loSession.createNativeQuery(String.format("SELECT * FROM %s WHERE (description <> '') ORDER BY description", loHibernate.getTableSymbol()), SymbolEntity.class);

    final ObservableList<StringKeyItem> loStringKeys = FXCollections.observableArrayList();

    StringKeyItem loInitKeyItem = null;
    final List<SymbolEntity> loList = loQuery.list();

    for (final SymbolEntity loRow : loList)
    {
      final String lcID = loRow.getSymbol();
      final StringKeyItem loKeyItem = new StringKeyItem(lcID, loRow.getDescription());
      loStringKeys.add(loKeyItem);
      if (loInitKeyItem == null)
      {
        loInitKeyItem = loKeyItem;
      }
    }
    loSession.close();

    this.cboStocks.getItems().clear();
    this.cboStocks.setItems(loStringKeys);

    if (loSelectItem != null)
    {
      this.cboStocks.setValue(loSelectItem);
      this.cboStocks.getSelectionModel().select(loSelectItem);
    }
    else
    {
      this.cboStocks.setValue(loInitKeyItem);
      this.cboStocks.getSelectionModel().select(loInitKeyItem);
    }

    return (loInitKeyItem);

  }

  // ---------------------------------------------------------------------------------------------------------------------
  private void useToday()
  {
    final boolean llChecked = this.chkUseToday.isSelected();

    Misc.setEditableForDatePicker(this.txtEnd, !llChecked);

    if (llChecked)
    {
      this.txtEnd.setValue(LocalDate.now());
    }
  }

  // ---------------------------------------------------------------------------------------------------------------------
  private void analyzeData()
  {
  }

  // -----------------------------------------------------------------------------
  private void updateTableData()
  {
    final XMLTextWriter loTextWriter = XMLTextWriter.INSTANCE;
    loTextWriter.initializeXMLDocument();
    loTextWriter.createRootNode(Constants.XML_SYMBOL_ROOT_LABEL, null);

    final Node loRecord = loTextWriter.appendNodeToRoot(Constants.XML_SYMBOL_RECORD_LABEL, (String) null, null);

    loTextWriter.appendToNode(loRecord, Constants.XML_SYMBOL_USE_TODAY, this.chkUseToday.isSelected() ? Constants.XML_TRUE : Constants.XML_FALSE, null);
    loTextWriter.appendToNode(loRecord, Constants.XML_SYMBOL_START_DATE, this.txtStart.getValue().toString(), null);
    loTextWriter.appendToNode(loRecord, Constants.XML_SYMBOL_END_DATE, this.txtEnd.getValue().toString(), null);

    String lcXML = loTextWriter.generateXMLString(2);
  }

  // -----------------------------------------------------------------------------
  private void updateOnComboBoxSelect()
  {
    if (!this.updateSelectedVariables())
    {
      this.btnAnalyze.setDisable(true);
      final StringKeyItem loItem = this.cboStocks.getSelectionModel().getSelectedItem();
      Misc.errorMessage(String.format("Unable to obtain the setup data for %s (%)", loItem.getDescription(), loItem.getKey()));
      return;
    }

    this.btnAnalyze.setDisable(false);
    this.updateComponents();
  }

  // -----------------------------------------------------------------------------
  private void updateComponents()
  {
    final XMLTextReader loReader = XMLTextReader.INSTANCE;
    if (!this.fcCurrentXML.isEmpty() && loReader.initializeXMLDocument(this.fcCurrentXML, false))
    {
      boolean llUseToday = loReader.getBoolean(Constants.XML_SYMBOL_USE_TODAY, true);
      this.chkUseToday.setSelected(llUseToday);
      this.txtStart.setValue(loReader.getDate(Constants.XML_SYMBOL_START_DATE, System.currentTimeMillis()).toLocalDate());
      this.txtEnd.setValue(llUseToday ? LocalDate.now() : loReader.getDate(Constants.XML_SYMBOL_END_DATE, System.currentTimeMillis()).toLocalDate());
    }

    this.chkUseToday.setSelected(true);
    this.txtStart.setValue(AppProperties.INSTANCE.getHistoricalStartDefault().toLocalDate());
    this.txtEnd.setValue(LocalDate.now());
  }

  // -----------------------------------------------------------------------------
  private boolean updateSelectedVariables()
  {
    final HibernateUtil loHibernate = HibernateUtil.INSTANCE;
    final Session loSession = HibernateUtil.INSTANCE.getSession();

    // There should only be one symbol. I'm not using LIMIT as there could be differences in SQL syntax between
    // the database servers.
    final String lcSQL = String.format("SELECT * FROM %s WHERE symbol = :symbol", loHibernate.getTableSymbol());
    final NativeQuery loQuery = loSession.createNativeQuery(lcSQL)
        .addEntity(SymbolEntity.class)
        .setParameter("symbol", this.cboStocks.getSelectionModel().getSelectedItem().getKey());

    final List<SymbolEntity> loList = loQuery.list();

    for (final SymbolEntity loRow : loList)
    {
      this.fcCurrentSymbol = loRow.getSymbol();
      this.fcCurrentXML = loRow.getHistoricalInfo();

      return (true);
    }

    return (false);
  }

  // ---------------------------------------------------------------------------------------------------------------------

}
// ---------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
// ---------------------------------------------------------------------------------------------------------------------
