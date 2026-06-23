public class JointAccount extends Account {
    public JointAccount(String fn, String ln, String n, String e, String p, double d) {
        super(fn, ln, n, e, p, d);
    }

    @Override
    public double getMinimumDeposit() { return 100000; }

    @Override
    public String getSpecialRulesDescription() { return "Requires a second NIN."; }

    @Override
    public void validateExtraRules(int age, String jointNin) throws IllegalArgumentException {
        if (jointNin == null || jointNin.trim().isEmpty() || !jointNin.matches("^[A-Z0-9]{14}$")) {
            throw new IllegalArgumentException("Joint Accounts require a valid 14-digit uppercase Secondary NIN.");
        }
    }
}