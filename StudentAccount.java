public class StudentAccount extends Account {
    public StudentAccount(String fn, String ln, String n, String e, String p, double d) {
        super(fn, ln, n, e, p, d);
    }

    @Override
    public double getMinimumDeposit() { return 10000; }

    @Override
    public String getSpecialRulesDescription() { return "Applicant age must be 18-25."; }

    @Override
    public void validateExtraRules(int age, String jointNin) throws IllegalArgumentException {
        if (age > 25) {
            throw new IllegalArgumentException("Student Accounts restrict applicant age maximum limits to 25 years old.");
        }
    }
}