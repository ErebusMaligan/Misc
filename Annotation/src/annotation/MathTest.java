package annotation;

import annotation.Annotations.Div0;
import annotation.Annotations.PositiveOnly;
import annotation.Annotations.SecurityState;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Dec 23, 2013, 5:29:21 PM 
 */
public class MathTest {
	
	@Div0
	@PositiveOnly
	@SecurityState(state=SecurityManager.INESCURE)
	public static int divide( int num, int denom ) throws AnnotationException {
		Annotations.process( num, denom );
		System.out.println( "Division Result: " );
		return num / denom;
	}
	
	@PositiveOnly
	@SecurityState(state=SecurityManager.SECURE)
	public static int multiply( int val1, int val2 ) throws AnnotationException {
		Annotations.process( val1, val2 );
		System.out.println( "Multiplication Result: " );
		return val1 * val2;
	}
	
	public static void main( String[] args ) {
		try {
			System.out.println( divide( 4, 2 ) );
		} catch ( AnnotationException e ) {
			e.printStackTrace();
		}
		
		try {
			System.out.println( divide( -1, 1 ) );
		} catch ( AnnotationException e ) {
			e.printStackTrace();
		}
		
		try {
			System.out.println( divide( 1, 0 ) );
		} catch ( AnnotationException e ) {
			e.printStackTrace();
		}
		
		try {
			System.out.println( multiply( 1, 4 ) );
		} catch ( AnnotationException e ) {
			e.printStackTrace();
		}
	}
}