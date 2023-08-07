import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

class parentwordle{
    Scanner sc=new Scanner(System.in);
    String black="\u001B[40m";
    String yellow = "\u001B[33m";
    String green = "\u001B[32m";
    String reset = "\u001B[0m";
    String rword;
    String[] words;
    String List() {
        try {
            words = new String(Files.readAllBytes(Paths.get("words.txt"))).split(" ");
            int i = (int) (Math.random() * words.length);
            String word = words[i];
            return word;
        }
        catch (Exception e) {
            System.out.println("Error 404: File \"words.txt\" not found");
            System.exit(404);
            return null;
        }
    }
}

class wordle extends parentwordle {
    char key[]={'Q','W','E','R','T','Y','U','I','O','P','A','S','D','F','G','H','J','K','L','Z','X','C','V','B','N','M'};
    int keyf[]=new int[26];
    String uword;
    int att=1;
    boolean won=false;
    String[][] gb=new String[6][7];
    void run(){
        rword=List();
        for(int i=0;i<26;i++)
        keyf[i]=0;
        rword=rword.toUpperCase();
        for(int i=0;i<=gb.length-1;i++){
            gb[i][0]="|";
            gb[i][6]="|";
            for(int j=1;j<=gb[0].length-2;j++){
                gb[i][j]="-";
            }
        }
        if(att>1)
        System.out.println("\033[H\033[2J");
        table();
        input();
    }
    
    public static void main(String args[]) {
        System.out.println("\033[H\033[2J");
        System.out.println("Welcome to Wordle!\n\nInstructions: In wordle, user has to guess a five-letter word in 6 attempts. At each attempt user has to input a word present in dictionary. In each attempt if a letter in the word is in the right position it will be printed in \u001B[32mgreen \u001B[0mcolor, \u001B[33myellow \u001B[0mif it is present in the word but is in wrong position.\n ");
        wordle a=new wordle();
        a.run();
    }

    void table(){
        for(int i=0;i<gb.length;i++){
            for(int j=0;j<gb[0].length;j++)
            System.out.print(gb[i][j]+ " ");
            System.out.println();
        }
    }

    void input(){
        String choice;
        boolean flag=false;
        att=1;
        won=false;
        while(!won && att<=6){
            keyboard();
            System.out.println();
            System.out.print("Please enter a word:\n>");
            while(!flag){
                uword=sc.nextLine();
                if(uword.length()==5){
                    for(String w: words){
                        if(w.equalsIgnoreCase(uword))
                        flag=true;
                    }
                    if(flag==false)
                    System.out.println("Enter a valid word.");
                }
                else
                System.out.println("Enter a word of length 5");
            }
            enter();
            att++;
            flag=false;
        }
        if(won==false){
        System.out.println("Oops! Better luck next time.\n");
        System.out.println("Correct word was: "+rword);
        }
        else
        System.out.println("Congratulations! You guessed the correct word.\n");
        System.out.print("Would you like to play again? Y/N : ");
        do{
            choice=sc.nextLine();
            System.out.println();
            if(choice.equalsIgnoreCase("y"))
            run();
            else if(choice.equalsIgnoreCase("n"))
            flag=false;
            else
            System.out.println("Please enter Y/N : ");
        }while(flag);
    }

    void enter(){
        int i,j;
        String letter="";
        int flag[]=new int[5];
        int flagy[]=new int[5];
        for(i=0;i<5;i++){
            flag[i]=0;
            flagy[i]=0;
        }
        uword=uword.toUpperCase();
        for(i=0;i<5;i++){
            letter=String.valueOf(uword.charAt(i));
            if(uword.charAt(i)==rword.charAt(i)){
                flag[i]=1;
                letter=green+letter+reset;
                keyg(uword.charAt(i));
            }
            gb[att-1][i+1]=letter;
        }
        for(i=0;i<5;i++){
            while(flag[i]!=0 && i<4)
            i++;
            letter=String.valueOf(uword.charAt(i));
            for(j=0;j<5;j++){
                while(flag[j]!=0 && j<4)
                j++;
                if(uword.charAt(i)==rword.charAt(j) && (i!=4 || j!=4) && (flag[i]!=1 && flag[j]!=1) && flagy[j]!=1){
                    letter=yellow+letter+reset;
                    flagy[j]=1;
                    keyy(uword.charAt(i));
                    gb[att-1][i+1]=letter;
                    break;
                }
            }
        }
        for(i=0;i<5;i++){
            if(flag[i]==0 && flagy[i]==0)
            keyb(uword.charAt(i));
        }
        System.out.println("\033[H\033[2J");
        table();
        if(uword.equals(rword))
        won=true;
    }
    void keyb(char a){
        for(int i=0;i<26;i++){
            if(a==key[i] && keyf[i]==0){
                keyf[i]=-1;
            }
        }
    }
    void keyg(char a){
        for(int i=0;i<26;i++){
            if(a==key[i]){
                keyf[i]=2;
            }
        }
    }
    void keyy(char a){
        for(int i=0;i<26;i++){
            if(a==key[i] && keyf[i]!=2){
                keyf[i]=1;
            }
        }
    }
    void keyboard(){
        for(int i=0;i<10;i++){
            if(keyf[i]==1){
                System.out.print(yellow+String.valueOf(key[i])+reset+" ");
            }
            else if(keyf[i]==2){
                System.out.print(green+String.valueOf(key[i])+reset+" ");
            }
            else if(keyf[i]==-1){
                System.out.print(black+String.valueOf(key[i])+reset+" ");
            }
            else
            System.out.print(key[i]+" ");
        }
        System.out.println();
        System.out.print(" ");
        for(int i=10;i<19;i++){
            if(keyf[i]==1){
                System.out.print(yellow+String.valueOf(key[i])+reset+" ");
            }
            else if(keyf[i]==2){
                System.out.print(green+String.valueOf(key[i])+reset+" ");
            }
            else if(keyf[i]==-1){
                System.out.print(black+String.valueOf(key[i])+reset+" ");
            }
            else
            System.out.print(key[i]+" ");
        }
        System.out.println();
        System.out.print("  ");
        for(int i=19;i<26;i++){
            if(keyf[i]==1){
                System.out.print(yellow+String.valueOf(key[i])+reset+" ");
            }
            else if(keyf[i]==2){
                System.out.print(green+String.valueOf(key[i])+reset+" ");
            }
            else if(keyf[i]==-1){
                System.out.print(black+String.valueOf(key[i])+reset+" ");
            }
            else
            System.out.print(key[i]+" ");
        }
    }
}