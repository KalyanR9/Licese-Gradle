package gridconsole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.AbstractUiBindingConfiguration;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.ConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.DefaultNatTableStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.IEditableRule;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.data.convert.DefaultBooleanDisplayConverter;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.editor.CheckBoxCellEditor;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.nebula.widgets.nattable.edit.editor.MultiLineTextCellEditor;
import org.eclipse.nebula.widgets.nattable.edit.gui.CellEditDialog;
import org.eclipse.nebula.widgets.nattable.filterrow.FilterIconPainter;
import org.eclipse.nebula.widgets.nattable.filterrow.FilterRowHeaderComposite;
import org.eclipse.nebula.widgets.nattable.filterrow.FilterRowPainter;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ColumnOverrideLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.layer.stack.DefaultBodyLayerStack;
import org.eclipse.nebula.widgets.nattable.painter.cell.ButtonCellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.CheckBoxPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.ImagePainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.CellPainterDecorator;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.HorizontalAlignmentEnum;
import org.eclipse.nebula.widgets.nattable.style.Style;
import org.eclipse.nebula.widgets.nattable.style.TextDecorationEnum;
import org.eclipse.nebula.widgets.nattable.style.theme.ModernNatTableThemeConfiguration;
import org.eclipse.nebula.widgets.nattable.style.theme.ThemeConfiguration;
import org.eclipse.nebula.widgets.nattable.ui.NatEventData;
import org.eclipse.nebula.widgets.nattable.ui.action.IMouseAction;
import org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry;
import org.eclipse.nebula.widgets.nattable.ui.matcher.CellLabelMouseEventMatcher;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.nebula.widgets.nattable.ui.util.CellEdgeEnum;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public class GridNattable {
	public static final String CHECK_BOX_CONFIG_LABEL = "checkBox";
    public static final String CHECK_BOX_EDITOR_CONFIG_LABEL = "checkBoxEditor";
    public static final String LINK_CELL_LABEL = "LINK_CELL_LABEL";
    public static final String BUTTON_CELL_LABEL = "BUTTON_CELL_LABEL";
	private NatTable natTable;
	private GridConsoleLayer underlyingLayer;
	private List<GridConsoleRow> listOfGridConsoleRow;
	private ButtonCellPainter buttonPainter;
	
	 public static final PricingTypeBean PRICING_MANUAL = new PricingTypeBean(
	            "MN");
	    public static final PricingTypeBean PRICING_AUTO = new PricingTypeBean("AT");

	public Control createExampleControl(Composite parent) {
		
		IConfigRegistry configRegistry = new ConfigRegistry();
		underlyingLayer = new GridConsoleLayer(configRegistry, getList1());
		DataLayer bodyDataLayer = underlyingLayer.getBodyDataLayer();
		IDataProvider dataProvider = bodyDataLayer.getDataProvider();
		
		final ColumnOverrideLabelAccumulator columnLabelAccumulator = new ColumnOverrideLabelAccumulator(
				bodyDataLayer);
		columnLabelAccumulator.registerColumnOverrides(0, LINK_CELL_LABEL);
		columnLabelAccumulator.registerColumnOverrides(1, BUTTON_CELL_LABEL);
		bodyDataLayer.setConfigLabelAccumulator(columnLabelAccumulator);
		
		
		
		// NOTE: Register the accumulator on the body data layer.
		// This ensures that the labels are bound to the column index and are
		// unaffected by column order.
		natTable = new NatTable(parent, underlyingLayer, false);
		natTable.addConfiguration(new DefaultNatTableStyleConfiguration());
		setColumnSize(bodyDataLayer);
		natTable.setBackground(GUIHelper.COLOR_WHITE);
		natTable.addConfiguration(new FilterRowCustomConfiguration() {
			@Override
			public void configureRegistry(IConfigRegistry configRegistry) {
				super.configureRegistry(configRegistry);
				// Shade the row to be slightly darker than the background.
				final Style rowStyle = new Style();
				rowStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.getColor(197, 212, 231));
				configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, rowStyle, DisplayMode.NORMAL,
						GridRegion.FILTER_ROW);
			}
		});

		natTable.setConfigRegistry(configRegistry);
		natTable.addConfiguration(new EditorConfiguration());
		natTable.addConfiguration(editableGridConfiguration(
                columnLabelAccumulator, dataProvider));
		natTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		// Step 3: Register your custom cell style and , against the
        // label applied to the link cell.
        LinkClickConfiguration<GridConsoleRow> linkClickConfiguration = new LinkClickConfiguration<>();
        addLinkToColumn(configRegistry, natTable.getDisplay().getSystemColor(SWT.COLOR_BLUE), 
        		linkClickConfiguration);
        natTable.addConfiguration(linkClickConfiguration);

        // Step 4: Register your custom cell painter, cell style, against the
        // label applied to the button cell.
        addButtonToColumn(configRegistry);
        natTable.addConfiguration(new ButtonClickConfiguration<GridConsoleRow>(this.buttonPainter));
		natTable.configure();
		ThemeConfiguration modernTheme = new ModernNatTableThemeConfiguration();
		natTable.setTheme(modernTheme);
		return natTable;
	}
	
	private void addButtonToColumn(IConfigRegistry configRegistry) {
        this.buttonPainter = new ButtonCellPainter(
                new CellPainterDecorator(
                        new TextPainter(), CellEdgeEnum.RIGHT, new ImagePainter(GUIHelper.getImage("preferences"))));

        configRegistry.registerConfigAttribute(
                CellConfigAttributes.CELL_PAINTER,
                this.buttonPainter,
                DisplayMode.NORMAL,
                BUTTON_CELL_LABEL);

        // Add your listener to the button
        this.buttonPainter.addClickListener(new MyMouseAction());

        // Set the color of the cell. This is picked up by the button painter to
        // style the button
        Style style = new Style();
        style.setAttributeValue(
                CellStyleAttributes.BACKGROUND_COLOR,
                GUIHelper.COLOR_WHITE);

        configRegistry.registerConfigAttribute(
                CellConfigAttributes.CELL_STYLE,
                style,
                DisplayMode.NORMAL,
                BUTTON_CELL_LABEL);
        configRegistry.registerConfigAttribute(
                CellConfigAttributes.CELL_STYLE,
                style,
                DisplayMode.SELECT,
                BUTTON_CELL_LABEL);
    }
	private void addLinkToColumn(IConfigRegistry configRegistry, 
			Color linkColor, LinkClickConfiguration<GridConsoleRow> linkClickConfiguration) {
        // Add your listener to the button
        linkClickConfiguration.addClickListener(new MyMouseAction());

        Style linkStyle = new Style();
        linkStyle.setAttributeValue(CellStyleAttributes.FOREGROUND_COLOR,
                linkColor);
        linkStyle.setAttributeValue(CellStyleAttributes.TEXT_DECORATION, TextDecorationEnum.UNDERLINE);

        configRegistry.registerConfigAttribute(
                CellConfigAttributes.CELL_STYLE,
                linkStyle,
                DisplayMode.NORMAL,
                LINK_CELL_LABEL);
        configRegistry.registerConfigAttribute(
                CellConfigAttributes.CELL_STYLE,
                linkStyle,
                DisplayMode.SELECT,
                LINK_CELL_LABEL);
    }
	
	 class MyMouseAction implements IMouseAction {

	        @Override
	        public void run(NatTable natTable, MouseEvent event) {
	            NatEventData eventData = NatEventData.createInstanceFromEvent(event);
	            int rowIndex = natTable.getRowIndexByPosition(eventData.getRowPosition());
	            int columnIndex = natTable.getColumnIndexByPosition(eventData.getColumnPosition());

	            ListDataProvider<GridConsoleRow> dataProvider =
	                    underlyingLayer.getBodyDataProvider();

	            Object rowObject = dataProvider.getRowObject(rowIndex);
	            Object cellData = dataProvider.getDataValue(columnIndex, rowIndex);

	            System.out.println("Clicked on cell: " + cellData);
	            System.out.println("Clicked on row: " + rowObject + "\n");
	        }
	    }
	public static class FilterRowCustomConfiguration extends AbstractRegistryConfiguration {
		@Override
		public void configureRegistry(IConfigRegistry configRegistry) {
			// override the default filter row configuration for painter
			configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER,
					new FilterRowPainter(
							new FilterIconPainter(GUIHelper.getImage("filter"))),
					DisplayMode.NORMAL, GridRegion.FILTER_ROW);
		}
	}

	/**
	 * @author Bitwise Open Detailed Grid Console Message Window on cell click
	 *
	 */
	class EditorConfiguration extends AbstractRegistryConfiguration {
		@Override
		public void configureRegistry(IConfigRegistry configRegistry) {
			registerEditors(configRegistry, GridConsoleConstants.MESSAGE);
		}

		private void registerEditors(IConfigRegistry configRegistry, String columnHeader) {
			
		}
	}

	private void setColumnSize(DataLayer bodyDataLayer) {
		bodyDataLayer.setDefaultColumnWidthByPosition(0, 150);
		bodyDataLayer.setDefaultColumnWidthByPosition(1, 150);
		bodyDataLayer.setDefaultColumnWidthByPosition(2, 150);
		bodyDataLayer.setDefaultColumnWidthByPosition(3, 150);
	}

	public FilterRowHeaderComposite<GridConsoleRow> getFilterRowHeaderLayer() {
		return underlyingLayer.getFilterRowHeaderLayer();
	}

	public DefaultBodyLayerStack getBodyLayer() {
		return underlyingLayer.getBodyLayer();
	}

	public void setListOfGridConsoleRow(List<GridConsoleRow> listOfGridConsoleRow) {
		this.listOfGridConsoleRow = listOfGridConsoleRow;
	}

	public GridConsoleLayer getUnderlyingLayer() {
		return underlyingLayer;
	}

	public NatTable getNatTable() {
		return natTable;
	}
	
	public static AbstractRegistryConfiguration editableGridConfiguration(
            final ColumnOverrideLabelAccumulator columnLabelAccumulator,
            final IDataProvider dataProvider) {

        return new AbstractRegistryConfiguration() {

            @Override
            public void configureRegistry(IConfigRegistry configRegistry) {
                registerConfigLabelsOnColumns(columnLabelAccumulator);
                registerCheckBoxEditor(configRegistry, new CheckBoxPainter(),
                        new CheckBoxCellEditor());
                registerEditableRules(configRegistry);
                String[] str = {"US"};
                registerMultiLineFieldsConfig(configRegistry, str);
            }
        };
        
    }
	
	protected static void registerEditableRules(IConfigRegistry configRegistry) {
        
        configRegistry.registerConfigAttribute(
                EditConfigAttributes.CELL_EDITABLE_RULE,
                IEditableRule.ALWAYS_EDITABLE, DisplayMode.EDIT,
                CHECK_BOX_CONFIG_LABEL);
    }

	private static void registerConfigLabelsOnColumns(ColumnOverrideLabelAccumulator columnLabelAccumulator) {
        columnLabelAccumulator.registerColumnOverrides(
                2,CHECK_BOX_EDITOR_CONFIG_LABEL, CHECK_BOX_CONFIG_LABEL);
    }
	
	  private static void registerCheckBoxEditor(IConfigRegistry configRegistry,
	            ICellPainter checkBoxCellPainter, ICellEditor checkBoxCellEditor) {
	        configRegistry.registerConfigAttribute(
	                CellConfigAttributes.CELL_PAINTER, checkBoxCellPainter,
	                DisplayMode.NORMAL, CHECK_BOX_CONFIG_LABEL);
	        configRegistry.registerConfigAttribute(
	                CellConfigAttributes.DISPLAY_CONVERTER,
	                new DefaultBooleanDisplayConverter(), DisplayMode.NORMAL,
	                CHECK_BOX_CONFIG_LABEL);
	        configRegistry.registerConfigAttribute(
	                EditConfigAttributes.CELL_EDITOR, checkBoxCellEditor,
	                DisplayMode.NORMAL, CHECK_BOX_EDITOR_CONFIG_LABEL);
	    }
	
	public static List<RowDataFixture> getList() {
        List<RowDataFixture> listFixture = new ArrayList<RowDataFixture>();
        listFixture.addAll(Arrays.asList(
                new RowDataFixture("US"
                        + RowDataFixture.getRandomNumber(), "B Ford Motor", "a", new Date(),
                        PRICING_MANUAL, 4.7912, 20, 1500000, true, 6.75, 1.01, -7.03,
                        114000000, 2000000000, 5000000000D),
                new RowDataFixture("ABC"
                        + RowDataFixture.getRandomNumber(), "A Alphabet Co.", "AAA", RowDataFixture.getRandomDate(),
                        PRICING_AUTO, 1.23456, 10, 10000, false, 5.124, .506, 1.233,
                        2000000, 50000000, 4500000)));

        return listFixture;
    }
	
	public static void registerMultiLineFieldsConfig(IConfigRegistry configRegistry,String[] multiLineLabelNames)
    {
    	if (multiLineLabelNames != null && multiLineLabelNames.length > 0)
            for (String multiLineLabelName : multiLineLabelNames)
            {                
            	//configure the multi line text editor            	
    			configRegistry.registerConfigAttribute(
    					EditConfigAttributes.CELL_EDITOR, 
    					new MultiLineTextCellEditor(false), 
    					DisplayMode.NORMAL, 
    					multiLineLabelName);
    			
    			configRegistry.registerConfigAttribute(
    	                EditConfigAttributes.OPEN_IN_DIALOG, Boolean.TRUE,
    	                DisplayMode.EDIT, multiLineLabelName);
    			
    	        // configure custom dialog settings
    	        Display display = Display.getCurrent();
    	        Map<String, Object> editDialogSettings = new HashMap<String, Object>();
    	        editDialogSettings.put(CellEditDialog.DIALOG_SHELL_TITLE,
    	                "Edit cell value");
    	        
    	        editDialogSettings.put(CellEditDialog.DIALOG_SHELL_ICON, SWT.ICON_INFORMATION);
    	        editDialogSettings.put(CellEditDialog.DIALOG_SHELL_RESIZABLE,
    	                Boolean.TRUE);    	        
    	           	        
    	        Point size = new Point(500, 300);
    	        editDialogSettings.put(CellEditDialog.DIALOG_SHELL_SIZE, size);

    	        int screenWidth = display.getBounds().width;
    	        int screenHeight = display.getBounds().height;
    	        Point location = new Point(
    	                (screenWidth / (2 * display.getMonitors().length))
    	                        - (size.x / 2), (screenHeight / 2) - (size.y / 2));
    	        editDialogSettings.put(CellEditDialog.DIALOG_SHELL_LOCATION, location);
    	        
    	        editDialogSettings.put(CellEditDialog.DIALOG_MESSAGE,
    	                "Enter the value:");
    	        
    	        configRegistry.registerConfigAttribute(
    	                EditConfigAttributes.EDIT_DIALOG_SETTINGS, editDialogSettings,
    	                DisplayMode.EDIT, multiLineLabelName);
    			
    			Style cellStyle = new Style();
    			cellStyle.setAttributeValue(CellStyleAttributes.HORIZONTAL_ALIGNMENT, HorizontalAlignmentEnum.LEFT);
    			configRegistry.registerConfigAttribute(
    					CellConfigAttributes.CELL_STYLE, 
    					cellStyle,
    					DisplayMode.EDIT,
    					multiLineLabelName);    			
            }
    }
	
	public static List<GridConsoleRow> getList1() {
        List<GridConsoleRow> listFixture = new ArrayList<GridConsoleRow>();
        listFixture.addAll(Arrays.asList(
                new GridConsoleRow("US","dddd",false),
                new GridConsoleRow("US","dddd",true)));

        return listFixture;
    }

	
	class LinkClickConfiguration<T> extends AbstractUiBindingConfiguration implements IMouseAction {

        private final List<IMouseAction> clickListeners = new ArrayList<>();

        /**
         * Configure the UI bindings for the mouse click
         */
        @Override
        public void configureUiBindings(UiBindingRegistry uiBindingRegistry) {
            // Match a mouse event on the body, when the left button is clicked
            // and the custom cell label is present
            CellLabelMouseEventMatcher mouseEventMatcher =
                    new CellLabelMouseEventMatcher(
                            GridRegion.BODY,
                            MouseEventMatcher.LEFT_BUTTON,
                            LINK_CELL_LABEL);

            CellLabelMouseEventMatcher mouseHoverMatcher =
                    new CellLabelMouseEventMatcher(GridRegion.BODY, 0, LINK_CELL_LABEL);

            // Inform the button painter of the click.
            uiBindingRegistry.registerMouseDownBinding(mouseEventMatcher, this);

            // show hand cursor, which is usually used for links
            uiBindingRegistry.registerFirstMouseMoveBinding(mouseHoverMatcher, (natTable, event) -> {
                natTable.setCursor(natTable.getDisplay().getSystemCursor(SWT.CURSOR_HAND));
            });

        }

        @Override
        public void run(final NatTable natTable, MouseEvent event) {
            for (IMouseAction listener : this.clickListeners) {
                listener.run(natTable, event);
            }
        }

        public void addClickListener(IMouseAction mouseAction) {
            this.clickListeners.add(mouseAction);
        }

        public void removeClickListener(IMouseAction mouseAction) {
            this.clickListeners.remove(mouseAction);
        }
    }
	
	class ButtonClickConfiguration<T> extends AbstractUiBindingConfiguration {

        private final ButtonCellPainter buttonCellPainter;

        public ButtonClickConfiguration(ButtonCellPainter buttonCellPainter) {
            this.buttonCellPainter = buttonCellPainter;
        }

        /**
         * Configure the UI bindings for the mouse click
         */
        @Override
        public void configureUiBindings(UiBindingRegistry uiBindingRegistry) {
            // Match a mouse event on the body, when the left button is clicked
            // and the custom cell label is present
            CellLabelMouseEventMatcher mouseEventMatcher =
                    new CellLabelMouseEventMatcher(
                            GridRegion.BODY,
                            MouseEventMatcher.LEFT_BUTTON,
                            BUTTON_CELL_LABEL);

            // Inform the button painter of the click. Rendering_cells_as_a_link_and_button
            uiBindingRegistry.registerMouseDownBinding(mouseEventMatcher, this.buttonCellPainter);
        }

    }
}
