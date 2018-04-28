package it.polimi.ingsw;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        Dice dice = new Dice(Color.ANSI_BLUE);
        System.out.println("Hello World!" + dice);
        dice.setColor(Color.ANSI_PURPLE);
        dice.dump();

        DiceBag bag = new DiceBag();
        bag.dump();
        System.out.println("--------");
        for (int i = 0; i < 90; i++) {
            Dice d = bag.draw();
            d.roll();
            System.out.println("d: " + d);
            //d.dump();
            bag.dump();
        }
    }
}
