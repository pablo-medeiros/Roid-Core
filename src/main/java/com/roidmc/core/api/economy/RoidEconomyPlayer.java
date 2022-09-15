package com.roidmc.core.api.economy;

public abstract class RoidEconomyPlayer {

    protected String name;
    protected String uuid;
    protected double amount;
    protected RoidEconomy economy;
    protected RoidEconomyTransactions transactions;

    public RoidEconomyPlayer(RoidEconomy economy, String name, String uuid, double amount) {
        this.economy = economy;
        this.name = name;
        this.uuid = uuid;
        this.amount = amount;
        this.transactions = new RoidEconomyTransactions(this);
    }

    protected abstract void onChange();

    public boolean setAmount(double amount) {
        if(economy.negativeOnly||amount>0) {
            this.amount = amount;
            onChange();
            return true;
        }
        return false;
    }

    public boolean addAmount(double amount) {
        if(economy.negativeOnly||(this.amount+amount)>0) {
            this.amount += amount;
            onChange();
            return true;
        }
        return false;
    }

    public boolean removeAmount(double amount) {
        if(economy.negativeOnly||this.amount>amount)
            this.amount -= amount;
        else return false;
        onChange();
        return true;
    }

    public boolean hasAmount(double amount){
        return this.amount>=amount;
    }

    public void toReceive(RoidEconomyPlayer target, double amount){
        this.amount+=amount;
        this.transactions.transaction(target,amount,false);
        this.onChange();
    }

    public boolean send(RoidEconomyPlayer target, double amount){
        if(economy.negativeOnly||this.amount>amount){
            target.toReceive(this,amount);
            this.amount-=amount;
            this.transactions.transaction(target,amount,true);
            onChange();
            return true;
        }
        return false;
    }

    public RoidEconomyTransactions getTransactions() {
        return transactions;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getUUID() {
        return uuid;
    }
}
