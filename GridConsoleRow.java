package gridconsole;

import java.util.LinkedHashMap;
import java.util.Map;

public class GridConsoleRow {
	private String id;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	private String name;
	private boolean last;

	GridConsoleRow(String id, String name, boolean last){
		this.id = id;
		this.name = name;
		this.last = last;
	}
	
	public static String[] getPropertyNames() {
        return new String[] {"id","name", "last"};
    }

	public static Map<String, String> getPropertyToLabelMap() {
        Map<String, String> propertyToLabelMap = new LinkedHashMap<String, String>();
        propertyToLabelMap.put("id", "ID");
        propertyToLabelMap.put("name", "Name");
        propertyToLabelMap.put("last", "Last");
        return propertyToLabelMap;
    }

	
}
