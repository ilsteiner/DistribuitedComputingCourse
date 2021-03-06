package cscie55.hw5.bank;

import cscie55.hw5.bank.command.Command;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Isaac on 4/15/2015.
 */
public class BankServerImpl implements BankServer{
    private Queue<Command> commandQueue;
    private ArrayList<CommandExecutionThread> commandExecutionThreads;
    private Bank bank;

    public BankServerImpl(Bank bank,int numThreads,boolean executeCommandInsideMonitor) {
        this.bank = bank;
        commandQueue = new LinkedList<Command>();
        commandExecutionThreads = new ArrayList<CommandExecutionThread>();

        //Add threads to thread array, then start them
        for(int i=0;i<numThreads;i++){
            CommandExecutionThread threadToAdd = new CommandExecutionThread(bank,commandQueue,executeCommandInsideMonitor);
            commandExecutionThreads.add(threadToAdd);
            threadToAdd.start();
        }
    }

    /**
     * Executes the given Command.
     *
     * @param command the Command to be executed.
     */
    @Override
    public void execute(Command command) {
        synchronized (commandQueue){
            commandQueue.add(command);
            commandQueue.notifyAll();
        }
    }

    /**
     * Stop the BankServer. No further Commands will be executed.
     *
     * @throws InterruptedException
     */
    @Override
    public void stop() throws InterruptedException {
        synchronized (commandExecutionThreads) {
            synchronized (commandQueue) {
                for (CommandExecutionThread commandToStop : commandExecutionThreads) {
                    commandQueue.add(Command.stop());
                }

                commandQueue.notifyAll();
            }
            for(CommandExecutionThread threadToJoin : commandExecutionThreads){
                threadToJoin.join();
            }
        }
    }

    /**
     * Returns the total of all Account balances for all Accounts managed by the BankServer's Bank.
     *
     * @return The total of all Account balances for all Accounts managed by the BankServer's Bank.
     */
    @Override
    public long totalBalances() {
        return bank.totalBalances();
    }
}
