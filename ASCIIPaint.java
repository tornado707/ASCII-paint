import java.util.Scanner;

/**
 * Command-driven paint program for text-based images.
 * 
 * @author Abhishek Tamang
 */
public class ASCIIPaint {
    
    /**
     * Converts the multi-line String into a 2D array of characters.
     * Each line in the String must be the same length.
     */
    public static char[][] stringToImage(String str) {
        char[][] result;
        String[] rows;
        
        rows = str.split("\n");
        result = new char[rows.length][];
        for (int r = 0; r < rows.length; r++) {
            result[r] = rows[r].toCharArray();
        }
        
        return result;
    }
    
    /** Reads the next character the user enters. */
    public static char nextChar(Scanner in) {
        return in.next().charAt(0);
    }
    
    /** Displays the given character-based image with a leading and trailing blank line. */
    public static void displayImage(char[][] image) {
        System.out.print("  ");
        for(int i = 0;i<image.length;i++){
            for(int j =0;j<i;j++){
                
                System.out.print(image[i][j]);
                
            }
        }
        System.out.print("  ");
    }
    
    /** Returns true if (row, col) is inside the image, false otherwise. */
    public static boolean inBounds(int row, int col, char[][] image) {
        if(row >= 1 || row<=image.length){
            if(col >=1 || row <=image.length){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Displays the 9 cells in the image centred at (row, col). Displays a space
     * for any cell that is outside the image's bounds-this is left to program.
     */
    public static void zoom(char[][] image, int row, int col) {
        int count = 0;// variable used to divide zoom output to 3 rows as to match the expected result.
        
        for(int i = row-1;i<row+2;i++){
            for(int j = col-1;j<col+2;j++){
                System.out.print(" | "+ image[i][j]+" | ");
                count++;
            }
            if(count==3){
                System.out.println("\n");
                count = 0;
            }
        }
        
    }
    
    
    /**
     * Starts a flood fill operation by selecting the replacement colour
     * at the given row and column.
     */
    public static void floodFill(char[][] image, int row, int col, char fill) {
        //must be within the bounds of the image and not already equal to fill
        if (inBounds(row, col, image) && image[row][col] != fill) {
            floodFill(image, row, col, image[row][col],fill);
        }
    }
    
    /** Performs flood fill, replacing replace with fill, starting from (row, col). */
    public static void floodFill(char[][] image, int row, int col, char replace, char fill) {
        
        
        if(image[row][col] == replace){
            image[row][col] = fill;
       
        floodFill(image,row-1,col,replace, fill);
                    
                    floodFill(image,row+1,col,replace,fill);
                    
                    floodFill(image,row,col+1,replace,fill);
                    floodFill(image,row,col-1,replace,fill);
                     }
        
    }
    
    public static void main(String[] args) {
        //Commands
        final char CMD_PRINT = 'p', CMD_ZOOM = 'z', CMD_FILL = 'f', CMD_HELP = '?',
            CMD_LOAD = 'l', CMD_SAVE = 's',CMD_INBOUND = 'i', CMD_QUIT = 'q';
        Scanner sc = new Scanner(System.in);
        boolean inboundResult;
        char[][] image; // the character-based 'image'
        char command; //user's entered command
        int row;    //|
        int col;    //|- command parameters
        int inputRow=0;//variable for row input value from user to check whether it lies in the range of the array
        int inputColumn=0;//variable for column input value from user to check whether it lies in the range of the array
        char brush; //|
        char fill;
        char replace;
        //The initial image source. Edit this to create some holes (or change its size).
        String strImage = "##### ########### ####\n" +
            "########### ###### ###\n" +
            "##### ######### ######\n" +
            "#### ############# ###\n" +
            "########## ####### ###\n" +
            "###### ########### ###\n" +
            "# ########## #########\n" +
            "#### ############### #\n" +
            "###### ########## ####";            
        
         
        image = stringToImage(strImage);
        System.out.println("ASCII Paint");
        System.out.println("===========");
        System.out.println();
        System.out.println("Enter commands (? for help). There are no further prompts after this point.");
        
        do {
            command = nextChar(sc); //read the next single-character command
            
            switch (command) {
                case CMD_PRINT: //TODO Make required changes in the appropriate cases
                    displayImage(image);//calling method displayImage to display the array content.
                    break;
                case CMD_ZOOM: 
                    System.out.println("Enter the values of row and column to zoom in: ");
                    row = sc.nextInt();
                    
                    col = sc.nextInt();
                    zoom( image, row,  col);
                    break;
                case CMD_FILL: 
                    
                    
                    System.out.println("Enter the values of row and column to be replaced : ");
                    row = sc.nextInt();
                    col = sc.nextInt();
                    System.out.println("Enter the character to fill the character to be replaced with: ");
                    fill = sc.next().charAt(0);
                    System.out.println("Enter the character you want to replace:");
                    replace = sc.next().charAt(0);
                    floodFill(image,row,col,fill);
                    
                    break;
                case CMD_LOAD:
                    break;
                case CMD_SAVE: System.out.println("Feature not implemented");
                break;
                case CMD_INBOUND:
                    System.out.println("Enter the values of row and column that you want to check:");
                    inputRow = sc.nextInt();
                    inputColumn = sc.nextInt();
                    inboundResult = inBounds(inputRow, inputColumn, image);
                    System.out.println("Does input "+"("+inputRow+","+inputColumn+")"+"lies in the array range: "+inboundResult);
                    break;
                case CMD_QUIT: 
                    break;
                case CMD_HELP:
                    
                default: System.out.println("\nCommands:");
                System.out.println(CMD_PRINT + "             \tDisplay the image");
                System.out.println(CMD_ZOOM +  " row col     \tShow cells surrounding (row, col)");
                System.out.println(CMD_FILL +  " row col fill\tFill from (row, col) with fill (a single character)");
                System.out.println(CMD_HELP +  "             \tShow this list of commands");
                System.out.println(CMD_LOAD +  " filename    \tLoad the text image from filename");
                System.out.println(CMD_SAVE +  " filename    \tSave the current image to filename");
                System.out.println(CMD_INBOUND + "           \tChecks whether input (row,column) is within the array range");
                System.out.println(CMD_QUIT +  "             \tLeave the program");
                System.out.println("\nCommands may be chained together, separated by whitespace");
                System.out.println();
            }
        } while (command != CMD_QUIT);
    }
    
}
