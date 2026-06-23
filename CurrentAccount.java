public class CurrentAccount extends Account {
    public CurrentAccount(String fn, String ln, String n, String e, String p, double d) {
        super(fn, ln, n, e, p, d);
    }

    @Override
    public double getMinimumDeposit() { return 200000; }

    @Override
    public String getSpecialRulesDescription() { return "Overdraft allowed, no interest."; }
}