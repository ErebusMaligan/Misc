package emudata;

import java.util.List;
import java.util.Map;

import xml.XMLExpansion;
import xml.XMLValues;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 27, 2015, 10:16:40 PM 
 */
public class SystemManager implements XMLValues {

	private List<SystemData> systems;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<XMLValues> getChildNodes() {
		return ( ( List<XMLValues> )( (Object)systems ) );
	}

	@Override
	public void loadParamsFromXMLValues( XMLExpansion arg0 ) {}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() { return null; }
}