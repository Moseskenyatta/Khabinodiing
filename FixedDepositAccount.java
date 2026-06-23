public class FixedDepositAccount extends Account {
    public FixedDepositAccount(String fn, String ln, String n, String e, String p, double d) {
        super(fn, ln, n, e, p, d);
    }

    @Override
    public double getMinimumDeposit() { return 1000000; }

    @Override
    public String getSpecialRulesDescription() { return "Locked term, highest interest."; }
}