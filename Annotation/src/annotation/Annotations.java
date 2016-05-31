package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

/**
 * @author Daniel J. Rivers
 *         2013
 *
 * Created: Dec 23, 2013, 5:16:03 PM 
 */
public class Annotations {
	
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Div0 {}
	
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface PositiveOnly {}
	
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface SecurityState {
		String state();
	}
	
	
	private static void conditionDiv0( Object...params ) throws AnnotationException {
		if ( (Integer)params[ params.length - 1 ] == 0 ) {
			throw new AnnotationException( "ERROR: DIVIDE BY 0!" );
		}
	}
	
	private static void conditionPositiveOnly( Object...params ) throws AnnotationException {
		for ( Object i : params ) {
			if ( ( (Integer)i ) < 0 ) {
				throw new AnnotationException( "ERROR: NOT POSITIVE NUMBERS" );
			}
		}
	}
	
	private static void conditionSecurityState( String state ) {
		SecurityManager.state = state;
		System.out.println( "Executing as: " + state );
	}
	
	public static void process( Object...params ) throws AnnotationException {
		try {
			StackTraceElement e = Thread.currentThread().getStackTrace()[ 2 ];  //get info about the method that called this method -- number indicates depth of stacktrace, 1 = current method, 2 = calling method
			for ( Method m : Class.forName( e.getClassName() ).getMethods() ) {
				if ( m.getName().equals( e.getMethodName() ) ) {	//located the right method
					if ( m.getAnnotation( Div0.class ) != null ) {  //if it has the Div0 annotation
						conditionDiv0( params );
					}
					if ( m.getAnnotation( PositiveOnly.class) != null ) {
						conditionPositiveOnly( params );
					}
					SecurityState s = m.getAnnotation( SecurityState.class );
					if ( s != null ) {
						conditionSecurityState( s.state() );
					}
				}
			}
		} catch ( SecurityException | ClassNotFoundException e1 ) {
			e1.printStackTrace();
		}
	}
	
}