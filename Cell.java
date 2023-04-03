//Written by fishe835
public class Cell {

    private int row; //Expresses the value of the row of the Cell
    private int col; //Expresses the value of the column of the Cell
    private char status; // ' ': Empty, 'B': Boat present, but not hit, 'H': Boat hit; 'M': Miss

    // Getter method for the status attribute, returns a char representing the status
    public char get_status(){
        return this.status;
    }

    // Getter method for the row attribute, returns a int representing the row
    public int get_row(){
        return this.row;
    }

    // Getter method for the column attribute, returns a int representing the column
    public int get_column(){
        return this.col;
    }
    // Setter method for the status attribute, sets status to a given char
    public void set_status(char c){
        this.status = c;
    }

    // Constructor for the Cell class
    public Cell(int row, int col, char status){
        this.row = row;
        this.col = col;
        this.status = status;
    }
}
