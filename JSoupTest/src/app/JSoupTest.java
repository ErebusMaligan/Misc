package app;

import gui.dialog.BusyDialog;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import scraper.image.GoogleImageScraper;
import scraper.image.URLImage;
import scraper.image.URLImageCellRenderer;
import scraper.text.GameFAQsSystemDescriptionScraper;
import scraper.url.GameFAQsAllSystemsScraper;
import scraper.url.GameFAQsSpecificSystemScraper;
import scraper.url.URLNode;
import statics.GU;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Jul 24, 2015, 9:48:47 PM 
 */
public class JSoupTest extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField input = new JTextField();
	
	JList<URLImage> s = new JList<>();
	
	DefaultListModel<URLImage> model = new DefaultListModel<>();
	
	public JSoupTest() {
		this.setSize( 800, 600 );
		this.setLayout( new BorderLayout() );
		this.setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		this.setVisible( true );
		JPanel north = new JPanel();
		north.add( new JLabel( "Search: " ) );
		GU.sizes( input, GU.FIELD );
		north.add( input );
		JButton search = new JButton( "Search" );
		search.addActionListener( e -> {
			try {
				loadImages();
			} catch ( Exception x ) {
				x.printStackTrace();
			}
		} );
		input.addKeyListener( new KeyAdapter() {
			@Override
			public void keyReleased( KeyEvent e ) {
				if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
					try {
						loadImages();
					} catch ( Exception x ) {
						x.printStackTrace();
					}
				}
			}
		} );
		north.add( search );
		this.add( north, BorderLayout.NORTH );
		JPanel center = new JPanel( new BorderLayout() );
		s.setCellRenderer( new URLImageCellRenderer() );
		s.setModel( model );
		center.add( new JScrollPane( s ), BorderLayout.CENTER );
		this.add( center, BorderLayout.CENTER );
		
		try {
			List<URLNode> nodes = new GameFAQsAllSystemsScraper().getURLs();
			Collections.sort( nodes );
			Collections.reverse( nodes );
			nodes.stream()
			.filter( n -> n.getText().contains( "NES" ) ) //filter for now to avoid spamming
			.forEach( n -> {
				System.out.println( n.toString() );
				try {
					List<URLNode> ns = new GameFAQsSpecificSystemScraper().getURLs( n.getURL().toString() );
					ns.forEach( n2 -> { 
						System.out.println( n2.toString() );
						try {
							List<String> ss = new GameFAQsSystemDescriptionScraper().getText( n2.getURL().toString() );
							ss.forEach( s -> System.out.println( s ) );
						} catch ( Exception e1 ) {
							e1.printStackTrace();
						}
					} );
				} catch ( Exception e1 ) {
					e1.printStackTrace();
				}
			} );
		} catch ( IOException e1 ) {
			e1.printStackTrace();
		}
	}
	
	private void loadImages() throws IOException {
		new BusyDialog( this, "Loading Remote Images..." ) {

			private static final long serialVersionUID = 1L;

			@Override
			public void executeTask() {
				GoogleImageScraper scraper = new GoogleImageScraper();
				try {
					model = new DefaultListModel<>();
					scraper.getImages( input.getText() ).forEach( i -> model.addElement( i ) );
					s.setModel( model );
				} catch ( IOException e ) {
					e.printStackTrace();
				}
				JSoupTest.this.revalidate();
			}
		};

	}
	
	
	
	public static void main( String[] args ) {
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e ) {
			System.err.println( "Critical JVM Failure!" );
			e.printStackTrace();
		}
		new JSoupTest();
	}

}
