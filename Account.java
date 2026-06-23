public abstract class Account {
    protected String firstName;
    protected String lastName;
    protected String nin;
    protected String email;
    protected String phone;
    protected double initialDeposit;

    public Account(String firstName, String lastName, String nin, String email, String phone, double initialDeposit) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nin = nin;
        this.email = email;
        this.phone = phone;
        this.initialDeposit = initialDeposit;
    }

    // Polymorphic Abstract Methods
    public abstract double getMinimumDeposit();
    public abstract String getSpecialRulesDescription();
    
    // Default validation hook that subclasses can override if they have extra rules (like Student or Joint)
    public void validateExtraRules(int age, String jointNin) throws IllegalArgumentException {
        // Default implementation does nothing
    }
}