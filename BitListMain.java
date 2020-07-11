public class BitListMain {

    public static void main( String[] args ) {

        BitList a = new BitList( );

        a.addFirst( 1 );
        a.addFirst( 0 );
        a.addFirst( 1 );
        a.addFirst( 0 );
        a.addFirst( 1 );
        a.addFirst( 1 );
        a.addFirst( 1 );

        System.out.println( "a is " + a );

        BitList b = new BitList( "1010111" );

        System.out.println( "b is " + b );

    }
}
