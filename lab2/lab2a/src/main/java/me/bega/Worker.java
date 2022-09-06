package me.bega;

public class Worker extends Thread {
    @Override
    public void run(){
        while (!Program.IsBearFound()){
            int currentRow = Program.allocateRow();
            if (currentRow == -1) break;
            for (int i = 0; i < Program.getRowLength(); i++) {
                if (Program.forest[currentRow][i]){
                    Program.setBearFound(currentRow, i);
                }
            }
        }
    }
}
