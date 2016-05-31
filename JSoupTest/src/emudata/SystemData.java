package emudata;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import statics.ImageUtils;
import xml.XMLExpansion;
import xml.XMLValues;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 27, 2015, 4:22:39 AM 
 */
public class SystemData implements XMLValues {
	
	private static String SYSTEM = "SYSTEM";
	
	private static String NAME = "NAME";
	
	private static String DESCRIPTION = "DESCRIPTION";
	
	private static String FANPATH = "FANPATH";
	
	private static String THUMBPATH = "THUMBPATH";
	
	private static String DEVELOPER = "DEVELOPER";
	
	private static String RELEASE = "RELEASE";
	
	private static String AKA = "AKA";
	
	private static String LAUNCHERPATH = "LAUNCHERPATH";
	
	private static String ROMPATH = "ROMPATH";
	
	private static String ARGS = "ARGS";

	private String name;
	
	private String description;
	
	private Image fanart;
	
	private Image thumbnail;
	
	private String fanPath;
	
	private String thumbPath;
	
	private String developer;
	
	private String release;
	
	private String aka;
	
	private String launcherPath;
	
	private String romPath;
	
	private String args;

	
	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

	public Image getFanart() {
		return fanart;
	}

	public void setFanart( Image fanart ) {
		this.fanart = fanart;
	}

	public Image getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail( Image thumbnail ) {
		this.thumbnail = thumbnail;
	}

	public String getFanPath() {
		return fanPath;
	}

	public void setFanPath( String fanPath ) {
		this.fanPath = fanPath;
		try {
			setFanart( ImageUtils.getScaledImage( ImageIO.read( new File( fanPath ) ), 200, 150 ) );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath( String thumbPath ) {
		this.thumbPath = thumbPath;
		try {
			setThumbnail( ImageUtils.getScaledImage( ImageIO.read( new File( thumbPath ) ), 200, 150 ) );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper( String developer ) {
		this.developer = developer;
	}

	public String getRelease() {
		return release;
	}

	public void setRelease( String release ) {
		this.release = release;
	}

	public String getAka() {
		return aka;
	}

	public void setAka( String aka ) {
		this.aka = aka;
	}

	public String getLauncherPath() {
		return launcherPath;
	}

	public void setLauncherPath( String launcherPath ) {
		this.launcherPath = launcherPath;
	}

	public String getRomPath() {
		return romPath;
	}

	public void setRomPath( String romPath ) {
		this.romPath = romPath;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs( String args ) {
		this.args = args;
	}

	@Override
	public List<XMLValues> getChildNodes() {
		return null;
	}

	@Override
	public void loadParamsFromXMLValues( XMLExpansion e ) {
		XMLExpansion x = e.getChild( SYSTEM );
		name = x.get( NAME );
		description = x.get( DESCRIPTION );
		setFanPath( x.get( FANPATH ) );
		setThumbPath( x.get( THUMBPATH ) );
		developer = x.get( DEVELOPER );
		release = x.get( RELEASE );
		aka = x.get( AKA );
		launcherPath = x.get( LAUNCHERPATH );
		romPath = x.get( ROMPATH );
		args = x.get( ARGS );
	}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() {
		Map<String, Map<String, String[]>> ret = new HashMap<>();
		Map<String, String[]> m = new HashMap<>();
		m.put( NAME, new String[] { name } );
		m.put( DESCRIPTION, new String[] { description } );
		m.put( FANPATH, new String[] { fanPath } );
		m.put( THUMBPATH, new String[] { thumbPath } );
		m.put( DEVELOPER, new String[] { developer } );
		m.put( RELEASE, new String[] { release } );
		m.put( AKA, new String[] { aka } );
		m.put( LAUNCHERPATH, new String[] { launcherPath } );
		m.put( ROMPATH, new String[] { romPath } );
		m.put( ARGS, new String[] { args } );
		ret.put( SYSTEM, m );
		return ret;
	}
}