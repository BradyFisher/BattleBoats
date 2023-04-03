//Written by fishe835
public class Battleboat {

    private  int size; // indicates number of Cell objects a Battleboat spans, defaulted to 3.
    private  boolean orientation; // orientation of the Battleboat, false <-> horizontal, true <-> vertical
    private  Cell[] spaces; //array of Cell objects associated with the Battleboat

    // Constructor for the Battle class, with size defaulted to 3, a random orientation and an array of Cells
    public Battleboat(){
        this.size = 3; //defualts size to 3
        this.orientation = ((int) Math.floor(Math.random() * 2)) == 0; //randomly decides orientation,
        this.spaces = new Cell[this.size]; //declares the Cell objects associated with the battleboat
    }

    // Getter method for the orientation attribute, returns a boolean representing the orientation
    public boolean get_orientation(){
        return this.orientation;
    }

    // Getter method for the size attribute, returns an int representing the size
    public int get_size(){
        return this.size;
    }

    // Getter method for the spaces attribute, returns an array of Cell objects representing the spaces
    public Cell[] get_spaces(){
        return this.spaces;
    }

}
