package cscie55.hw6.bank;

import cscie55.hw6.bank.command.Command;

import java.io.*;

/**
 * Created by Isaac on 5/04/2015.
 */
public class CommandExecutionThread extends Thread {
    private BankImpl bank;
    private InputStream inputStream;
    private OutputStream outputStream;

    /**
     * Creates a new CommandExecutionThread
     * @param bank the bank with which this thread is associated
     * @param inputStream the stream via which this thread will get input
     * @param outputStream the stream via which this thread will communicate output
     */
    public CommandExecutionThread(BankImpl bank, InputStream inputStream, OutputStream outputStream) {
        this.bank = bank;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }


    @Override
    public void run() {
        Command commandToRun;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        PrintWriter out = new PrintWriter(outputStream,true);

        System.out.println("Starting thread " + this.getId());

        while(true){
            try {
                String nextLine = in.readLine();
                if (nextLine != null) {
                    commandToRun = Command.parse(nextLine);
                    try {
                        synchronized (bank) {
                            String outputString;
                            System.out.println("Executing command: " + commandToRun.asString());
                            outputString = commandToRun.execute(bank);
                            if (outputString != null) {
                                out.println(outputString);
                            }
                            else {
                                out.println("");
                            }
                        }
                    } catch (InsufficientFundsException e) {
                        out.println(e.getMessage());
                    } catch (DuplicateAccountException e) {
                        out.println(e.getMessage());
                    }
                }
                else{
                    break;
                }
            }catch(IOException e){
                System.out.println("Ending thread " + this.getId());
                break;
            }
        }


    }
}
