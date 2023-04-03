import java.util.Scanner;

//Written by fishe835
public class Board {
    private int num_rows; //indicates number of rows the Board has
    private int num_columns; //indicates number of columns the Board has
    private int num_boats; //indicates number of Battleboat objects the Board has
    private Battleboat[] boats; //array of all the Battleboat objects associated with the Board object
    private Cell[][] board; //2-dimensional array of Cell objects required to represent the Board
    private boolean debugMode; //flag indicating if the Board should be printed in debugMode
    

    // Constructor for the Board class, it assigns the correct number of boats, initializes the board as a 2-dimensional array of Cells,
    //initializes boat into an array of Battleboats, and appropriately places the Battleboats and adds them to the board's boats
    public Board(int m , int n, boolean debugMode) {

        //determines the correct number of boats for the given rows and columns
        boolean invalid = true;
        while(invalid){
            if(m < 3 || n < 3 || m > 12 || n > 12){
                System.out.println("Error invalid dimensions");
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter new board rows dimension (between 3 and 12 inclusive): \n");
                m = sc.nextInt();
                System.out.println("Enter new board columns dimension (between 3 and 12 inclusive): \n");
                n = sc.nextInt();
            }
            else if (m == 3 || n == 3) {
                this.num_boats = 1;
                invalid = false;
            }
            else if ((m > 3 && m <= 5) || (n > 3 && n <= 5)) {
                this.num_boats = 2;
                invalid = false;
            }
            else if ((m > 5 && m <= 7) || (n > 5 && n <= 7)) {
                this.num_boats = 3;
                invalid = false;
            }
            else if ((m > 7 && m <= 9) || (n > 7 && n <= 9)) {
                this.num_boats = 4;
                invalid = false;
            }
            else if ((m > 10 && m <= 12) || (n > 10 && n <= 12)) {
                this.num_boats = 6;
                invalid = false;
            }
        }

        this.num_rows = m;
        this.num_columns = n;
        this.debugMode = debugMode;

        //initializing the boat and board arrays
        this.boats = new Battleboat[this.num_boats];
        this.board = new Cell[num_rows][num_columns];
        //Setting the status of every Cell on the board to ' ', to start with a board of no guesses or boats
        for (int row = 0; row < num_rows; row++) {
            for (int col = 0; col < num_columns; col++) {
                board[row][col] = new Cell(row, col, ' ');
            }
        }


        //Placing boats
        int boatsPlaced = 0; //Number of boats currently placed
        while (boatsPlaced < this.num_boats) {
            Battleboat boat1 = new Battleboat(); //potential boat to place
            boolean openSpace = true; //Indicates whether the spaces the potential boat takes up are empty on the board
            int r; //row position the upperleft most part of the boat will take
            int c; //column position the upperleft most part of the boat will take
            if (boat1.get_orientation() == true) {
                //if the boat is vertical, choose a row that is between 0 and m-size of the boat-1.
                r = (int) Math.floor(Math.random() * (m - boat1.get_size()));
                c = (int) Math.floor(Math.random() * n);
                //initializes Cell objects with the right row, column, and status for the boat
                //Also checks if the position the boat takes already has a boat
                //i is the current index of the boat array
                for (int i = 0; i < boat1.get_size(); i++) {
                    boat1.get_spaces()[i] = new Cell(r + i, c, 'B');
                    if (board[r + i][c].get_status() == 'B') {
                        openSpace = false;
                    }
                }
            } else {
                //if the boat is horizontal, choose a column that is between 0 and n-size of the boat-1.
                r = (int) Math.floor(Math.random() * m);
                c = (int) Math.floor(Math.random() * (n - boat1.get_size()));
                //initializes Cell objects with the correct row, column, and status for the boat,
                //Also checks if the position the boat takes already has a boat
                //i is the current index of the boat array
                for (int i = 0; i < boat1.get_size(); i++) {
                    boat1.get_spaces()[i] = new Cell(r, c + i, 'B');
                    if (board[r][c + i].get_status() == 'B') {
                        openSpace = false;
                    }
                }
            }

            //If the spaces the new boat takes are open, change the status of those positions on the board to show it contains a boat
            if (openSpace) {
                if (boat1.get_orientation() == true) {
                    for (int i = 0; i < boat1.get_size(); i++) {
                        board[r + i][c].set_status('B');
                    }
                } else {
                    for (int i = 0; i < boat1.get_size(); i++) {
                        board[r][c + i].set_status('B');
                    }
                }
                //placing the new boat into the array of boats
                boats[boatsPlaced] = boat1;
                boatsPlaced++;
            }
        }
    }

    //Obscures a character if the game is not being played in debug mode
    private char debug(boolean debugMode, char c){
        if(debugMode){
            return c;
        }
        else{
            switch(c){
                case 'H':
                    c = 'H';
                    break;
                case 'M':
                    c = 'M';
                    break;
                default:
                    c = ' ';
                    break;
            }
            return c;
        }
    }

    //Prints a Board object in a way that makes sense to the player
    public String toString(){

        String boardString = "\t";
        for (int j = 0; j < num_columns-1; j++){
            boardString += j + " |" + "\t";
        }

        boardString += num_columns-1;

        for(int i = 0; i < num_rows; i++){
            boardString+= "\n" + i + "\t";
            for (int j = 0; j < num_columns; j++){
                boardString += debug(debugMode, board[i][j].get_status()) + "\t";
            }
        }

        boardString += "\n";
        return boardString;
    }


    //Returns an int based on the guess with 0-> A Guess out of the Bounds of the board
    //1-> a miss, 2-> a hit, 3-> a repeated guess
    //Checks the result of the guess and changes the statuses of the cells if necessary
    public int guess(int r, int c){
        //Checks if the Guess is out of the bounds of the Board
        if ( (r < 0) || (r > (this.num_rows - 1)) || (c < 0) || (c > (this.num_columns - 1))){
            return 0;
            //"Penalty: Out of Bounds";
        }
        //Checks if the status of the guess position on the board is empty, resulting in a miss
        //Changes the status of the cell at that position to a miss
        else if (board[r][c].get_status() == ' ') {
            board[r][c].set_status('M');
            return 1;
            //"Miss";
        }
        //Checks if the status of the guess position on the board is an un-hit boat
        //Changes the status of the board and boat at that position to a hit
        else if(board[r][c].get_status() == 'B'){
            board[r][c].set_status('H');
            //Goes through all the battleboats in boats to find the hit boat
            for(int numBoat = 0; numBoat < this.num_boats; numBoat++){
                Cell cell; //The cell at each position of every boat, allowing the function to check its location
                for(int position = 0; position < boats[numBoat].get_size(); position++){
                    cell = boats[numBoat].get_spaces()[position];
                    if((cell.get_row() == r) && (cell.get_column() == c)){
                        boats[numBoat].get_spaces()[position].set_status('H');
                    }
                }
            }
            return 2;
            //"Hit";
        }
        else {
            return 3;
            //"Penalty: Redundant Guess";
        }
    }

    //Calculates the number of unsunken boats
    public int unsunkBoats(){
        int sunkenBoats = 0; //Number of sunken boats found
        for(int boatsChecked = 0; boatsChecked < this.num_boats; boatsChecked++) {
            int hitsOnBoat = 0; //Number of hits the boat has
            for (int spacesChecked = 0; spacesChecked < boats[boatsChecked].get_size(); spacesChecked++) {
                char stat = (this.boats[boatsChecked].get_spaces())[spacesChecked].get_status();
                if (stat == 'H') {
                    hitsOnBoat++;
                }
            }
            if (hitsOnBoat == boats[boatsChecked].get_size()) {
                sunkenBoats++;
            }
        }
        return (this.num_boats - sunkenBoats);
    }
}
