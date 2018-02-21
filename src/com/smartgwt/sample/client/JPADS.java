package com.smartgwt.sample.client;

import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.core.KeyIdentifier;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.util.PageKeyHandler;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.EditorEnterEvent;
import com.smartgwt.client.widgets.grid.events.EditorEnterHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.VStack;

public class JPADS implements EntryPoint {
    /** 
     * Creates a new instance of JPADS
     */
    public JPADS() {
    }

    /** 
     * The entry point method, called automatically by loading a module
     * that declares an implementing class as an entry-point
     */
    public void onModuleLoad() {
		KeyIdentifier debugKey = new KeyIdentifier();
		debugKey.setCtrlKey(true);
		debugKey.setKeyName("D");

		Page.registerKey(debugKey, new PageKeyHandler() {
			public void execute(String keyName) {
				SC.showConsole();
			}
		});

        VStack vStack = new VStack();
        vStack.setLeft(175);
        vStack.setTop(75);
        vStack.setWidth("70%");
        vStack.setMembersMargin(20);

        final DataSource countryDS = DataSource.get("country_DataSource");

        final ListGrid countryGrid = new ListGrid();
        countryGrid.setWidth(700);
        countryGrid.setHeight(224);
        countryGrid.setAlternateRecordStyles(true);
        countryGrid.setDataSource(countryDS);
        countryGrid.setAutoFetchData(true);
        countryGrid.setShowFilterEditor(true);
        countryGrid.setCanEdit(true);
        countryGrid.setEditEvent(ListGridEditEvent.CLICK);
        countryGrid.setCanRemoveRecords(true);

        ListGridField countryCode = new ListGridField("countryCode", "Code", 50);
        ListGridField countryName = new ListGridField("countryName", "Country");

        countryGrid.setFields(countryCode, countryName);

        IButton newCountryButton = new IButton("New country");
        newCountryButton.addClickHandler(new ClickHandler()
        {
            public void onClick (ClickEvent event)
            {
                countryGrid.startEditingNew();
            }
        });

        DataSource cityDS = DataSource.get("city_DataSource");
        final ListGrid cityGrid = new ListGrid();
        cityGrid.setWidth(700);
        cityGrid.setHeight(224);
        cityGrid.setAlternateRecordStyles(true);
        cityGrid.setDataSource(cityDS);
        cityGrid.setAutoFetchData(false);
        cityGrid.setShowFilterEditor(true);
        cityGrid.setCanEdit(true);
        cityGrid.setEditEvent(ListGridEditEvent.CLICK);
        cityGrid.setCanRemoveRecords(true);

        ListGridField cityName = new ListGridField("cityName", "City");

        cityGrid.setFields(cityName);

        IButton newCityButton = new IButton("New city");
        newCityButton.addClickHandler(new ClickHandler()
        {
            public void onClick (ClickEvent event)
            {
                ListGridRecord record = countryGrid.getSelectedRecord();
                if (record != null) {
                    cityGrid.startEditingNew();
                }
                else {
                    SC.warn("Select country first.");
                }
            }
        });

        countryGrid.addSelectionChangedHandler(new SelectionChangedHandler()
        {
            public void onSelectionChanged (SelectionEvent event)
            {
                if (event.getState()) {
                    ListGridRecord record = countryGrid.getSelectedRecord();
                    if (record != null) {
                        cityGrid.fetchRelatedData(record, countryDS);
                    }
                }
            }
        });

        cityGrid.addEditorEnterHandler(new EditorEnterHandler()
        {
            public void onEditorEnter (EditorEnterEvent event)
            {
                cityGrid.setEditValue(event.getRowNum(), "countryId",
                                      countryGrid.getSelectedRecord().getAttribute("countryId"));
            }
        });

        vStack.addMember(newCountryButton);
        vStack.addMember(countryGrid);
        vStack.addMember(newCityButton);
        vStack.addMember(cityGrid);

        vStack.draw();

        countryGrid.getResultSet().setUseClientFiltering(Boolean.FALSE);
        countryGrid.getResultSet().setUseClientSorting(Boolean.FALSE);
    }
}
