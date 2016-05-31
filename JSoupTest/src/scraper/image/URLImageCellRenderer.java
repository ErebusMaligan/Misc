package scraper.image;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import statics.ImageUtils;

/**
 * @author Daniel J. Rivers
 *         2014
 *
 * Created: Apr 30, 2014, 11:54:49 PM 
 */
public class URLImageCellRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = 1L;
	
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
		JLabel c = (JLabel)super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
		c.setText( "Image " + ( index + 1 ) );
		URLImage i = (URLImage)value;
		c.setIcon( new ImageIcon( ImageUtils.getScaledImage( i.getImage(), 200, 150 ) ) );
		return c;
	}
}