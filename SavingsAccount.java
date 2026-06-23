public class SavingsAccount extends Account {
    public SavingsAccount(String fn, String ln, String n, String e, String p, double d) {
        super(fn, ln, n, e, p, d);
    }

    @Override
    public double getMinimumDeposit() { return 50000; }

    @Override
    public String getSpecialRulesDescription() { return "Earns interest, no overdraft."; }
}