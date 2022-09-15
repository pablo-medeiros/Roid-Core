package com.roidmc.core.api.economy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoidEconomyTransactions extends ArrayList<RoidEconomyTransactions.Transaction> implements List<RoidEconomyTransactions.Transaction>{

    private RoidEconomyPlayer player;

    public RoidEconomyTransactions(RoidEconomyPlayer player) {
        this.player = player;
    }

    public RoidEconomyTransactions(Collection<? extends Transaction> c, RoidEconomyPlayer player) {
        super(c);
        this.player = player;
    }

    public void transaction(RoidEconomyPlayer target, double amount, boolean sent){
        this.add(new Transaction(target.getName(), target.getUUID(), amount, player.amount,!sent,sent));
    }

    public static class Transaction {

        public final String targetName;
        public final String targetUUID;
        public final double amount;
        public final double currentBalance;
        public final boolean received;
        public final boolean sent;
        public final long created_at;

        public Transaction(String targetName, String targetUUID, double amount, double currentBalance, boolean received, boolean sent) {
            this(targetName,targetUUID,amount, currentBalance, received,sent,System.currentTimeMillis());
        }

        public Transaction(String targetName, String targetUUID, double amount, double currentBalance, boolean received, boolean sent, long created_at) {
            this.targetName = targetName;
            this.targetUUID = targetUUID;
            this.amount = amount;
            this.currentBalance = currentBalance;
            this.received = received;
            this.sent = sent;
            this.created_at = created_at;
        }
    }
}
