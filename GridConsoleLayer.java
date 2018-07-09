package gridconsole;

import java.util.List;
import java.util.Map;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.data.ReflectiveColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.GlazedListsEventLayer;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.GlazedListsSortModel;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.filterrow.DefaultGlazedListsStaticFilterStrategy;
import org.eclipse.nebula.widgets.nattable.filterrow.FilterRowHeaderComposite;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultColumnHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultCornerDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultRowHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.CornerLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.DefaultColumnHeaderDataLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.DefaultRowHeaderDataLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.RowHeaderLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ColumnOverrideLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.layer.stack.DefaultBodyLayerStack;
import org.eclipse.nebula.widgets.nattable.sort.SortHeaderLayer;
import org.eclipse.nebula.widgets.nattable.sort.config.SingleClickSortConfiguration;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.TransformedList;

/**
 * @author Bitwise
 * Create underlying layer for GridCosole Table
 *
 */
public class GridConsoleLayer extends GridLayer{

	List<GridConsoleRow> listOfGridConsoleRow;
	 private final ListDataProvider<GridConsoleRow> bodyDataProvider;
	 private final DataLayer bodyDataLayer;
	private FilterRowHeaderComposite<GridConsoleRow>  filterRowHeaderLayer;
	private DefaultBodyLayerStack bodyLayer;
	protected GridConsoleLayer(IConfigRegistry configRegistry, List<GridConsoleRow> list) {
		super(true);
		// Underlying data layers
		this.listOfGridConsoleRow  = list;
		EventList<GridConsoleRow> eventList =GlazedLists.eventList(this.listOfGridConsoleRow);
		TransformedList<GridConsoleRow, GridConsoleRow> rowObjectsGlazedList = GlazedLists.threadSafeList(eventList);
		SortedList<GridConsoleRow> sortedList =new SortedList<>(rowObjectsGlazedList, null);
        FilterList<GridConsoleRow> filterList =new FilterList<>(sortedList);
        
        String[] propertyNames = GridConsoleRow.getPropertyNames();
        Map<String, String> propertyToLabelMap = GridConsoleRow.getPropertyToLabelMap();
        
        // Body layer
        IColumnPropertyAccessor<GridConsoleRow> columnPropertyAccessor =new ReflectiveColumnPropertyAccessor<>(propertyNames);
        this.bodyDataProvider = new ListDataProvider<>(filterList, columnPropertyAccessor);
        this.bodyDataLayer = new DataLayer(this.bodyDataProvider);
        GlazedListsEventLayer<GridConsoleRow> glazedListsEventLayer = new GlazedListsEventLayer<>(this.bodyDataLayer, eventList);
        bodyLayer = new DefaultBodyLayerStack(glazedListsEventLayer);
        ColumnOverrideLabelAccumulator bodyLabelAccumulator =new ColumnOverrideLabelAccumulator(this.bodyDataLayer);
        
        
        this.bodyDataLayer.setConfigLabelAccumulator(bodyLabelAccumulator);
        registerColumnLables(bodyLabelAccumulator);
        // Column header layer
        IDataProvider columnHeaderDataProvider =new DefaultColumnHeaderDataProvider(propertyNames, propertyToLabelMap);
        DataLayer columnHeaderDataLayer =new DefaultColumnHeaderDataLayer(columnHeaderDataProvider);
        ColumnHeaderLayer columnHeaderLayer =new ColumnHeaderLayer(columnHeaderDataLayer, bodyLayer, bodyLayer.getSelectionLayer());
        SortHeaderLayer<Person> sortHeaderLayer =
                new SortHeaderLayer<>(columnHeaderLayer,new GlazedListsSortModel<>(
                		sortedList, columnPropertyAccessor, configRegistry, columnHeaderDataLayer),false);
        sortHeaderLayer.addConfiguration(new SingleClickSortConfiguration());
        
        
        //Filter Strategy
        DefaultGlazedListsStaticFilterStrategy<GridConsoleRow> filterStrategy =new DefaultGlazedListsStaticFilterStrategy<>(
                        filterList, columnPropertyAccessor, configRegistry);
        filterRowHeaderLayer =
                new FilterRowHeaderComposite<>(filterStrategy, sortHeaderLayer, columnHeaderDataProvider, configRegistry);

        ColumnOverrideLabelAccumulator labelAccumulator =new ColumnOverrideLabelAccumulator(columnHeaderDataLayer);
        columnHeaderDataLayer.setConfigLabelAccumulator(labelAccumulator);
     // Row header layer
        DefaultRowHeaderDataProvider rowHeaderDataProvider =new DefaultRowHeaderDataProvider(this.bodyDataProvider);
        DefaultRowHeaderDataLayer rowHeaderDataLayer =new DefaultRowHeaderDataLayer(rowHeaderDataProvider);
        RowHeaderLayer rowHeaderLayer =new RowHeaderLayer(rowHeaderDataLayer, bodyLayer, bodyLayer.getSelectionLayer());
     // Corner layer
        DefaultCornerDataProvider cornerDataProvider =new DefaultCornerDataProvider(columnHeaderDataProvider, rowHeaderDataProvider);
        DataLayer cornerDataLayer =new DataLayer(cornerDataProvider);
        CornerLayer cornerLayer =new CornerLayer(cornerDataLayer, rowHeaderLayer, filterRowHeaderLayer);
        
        // Grid
        setBodyLayer(bodyLayer);
        // Note: Set the filter row as the column header
        setColumnHeaderLayer(filterRowHeaderLayer);
        setRowHeaderLayer(rowHeaderLayer);
        setCornerLayer(cornerLayer);
        
	}
	/**
	 * @param bodyLabelAccumulator
	 * This is necessary to register any editors on cells 
	 * 
	 */
	private void registerColumnLables(ColumnOverrideLabelAccumulator bodyLabelAccumulator) {
		bodyLabelAccumulator.registerColumnOverrides(0,GridConsoleConstants.TIMESTAMP);
        bodyLabelAccumulator.registerColumnOverrides(1,GridConsoleConstants.LOGLEVEL);
        bodyLabelAccumulator.registerColumnOverrides(2,GridConsoleConstants.JOBPHASE);
        bodyLabelAccumulator.registerColumnOverrides(3,GridConsoleConstants.MESSAGE);
	}
	public FilterRowHeaderComposite<GridConsoleRow> getFilterRowHeaderLayer() {
		return filterRowHeaderLayer;
	}

	public DefaultBodyLayerStack getBodyLayer() {
		return bodyLayer;
	}

	public ListDataProvider<GridConsoleRow> getBodyDataProvider() {
        return this.bodyDataProvider;
    }

    public DataLayer getBodyDataLayer() {
        return this.bodyDataLayer;
    }
    
}