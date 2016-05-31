import icon.creator.TriangleIconCreator;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Nov 5, 2015, 5:24:37 PM 
 */
public class TestApp {
	public static void main( String[] args ) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e ) {
			System.err.println( "Critical JVM Failure!" );
			e.printStackTrace();
		}
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setSize( 1200, 600 );

//		Rocker switch test code
		
//		SlitLightRockerSwitchIconCreator r = new SlitLightRockerSwitchIconCreator();
//		Color bg = new Color( 30, 30, 30 );
//		Color bg = Color.BLACK;
//		Color bg = new Color( 200, 200, 200 );
//		Color bg = new Color( 200, 0, 0 );
//		Arrays.asList( new Color[] { Color.RED, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.BLUE, Color.ORANGE, Color.CYAN, Color.PINK } ).forEach( c -> {
//			int x = (int)(Math.random() * 100);
//			if ( x < 50 ) { x = 50; }
//			Icon[] i = r.getIcons( bg, c, x, 2 * x );
//			ToggleButton b = new ToggleButton( i[ 0 ], i[ 1 ], true );
//			frame.add( b );
//		} );

//		TextIconCreator tic = new TextIconCreator();
//		JLabel temp = new JLabel();
//		System.out.println( temp.getFont() );
//		Icon[] i = tic.getIcons( "Testing", temp.getFont().deriveFont( 60f ), Color.MAGENTA, Color.BLACK );
//		JLabel l = new JLabel( i[ 0 ] );
//		frame.add( l );
//		l = new JLabel( i[ 1 ] );
//		frame.add( l );
		
		Color fg = new Color( 100, 100, 100 );
		Color bg = Color.BLACK;
		TriangleIconCreator tic = new TriangleIconCreator();
		Icon[] i = tic.getIcons( bg, fg, 50, 50 );
		JLabel l = new JLabel( i[ 0 ] );
		frame.add( l );
		l = new JLabel( i[ 1 ] );
		frame.add( l );
		
		
		frame.setLayout( new BoxLayout( frame.getContentPane(), BoxLayout.X_AXIS ) );
		frame.setVisible( true );
	}
}