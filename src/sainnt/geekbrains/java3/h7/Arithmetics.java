package sainnt.geekbrains.java3.h7;

public class Arithmetics {
    @BeforeSuite
    private static void setup(){
        System.out.println("Setup...");
    }
    @Test(priority = 1)
    private static int sum(int s1,int s2){
        System.out.println("Calculating method 1...");
        return s1 + s2 ;
    }

    @Test(priority = 2)
    private static int minus(int s1,int s2){
        System.out.println("Calculating method 2...");
        return s1-s2;
    }

    @Test
    private static int multiply(int p1,int p2){
        System.out.println("Calculating method without priority");
        return p1*p2;
    }

    @AfterSuite
    private static void destroy(){
        System.out.println("Destroying...");
    }

}
