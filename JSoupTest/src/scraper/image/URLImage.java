package scraper.image;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 26, 2015, 11:27:49 PM 
 */
public class URLImage {
	
	private URL url = null;
	
	private Image image = null;
	
	public URLImage( String urlString ) {
		try {
			url = new URL( urlString );
			loadImage();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	private void loadImage() throws IOException {
		image = ImageIO.read( url );
	}

	/**
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}
}